package com.sebascamayo.run.presentation.active_run.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.content.getSystemService
import androidx.core.net.toUri
import com.sebascamayo.presentation.ui.formatted
import com.sebascamayo.run.domain.RunningTracker
import com.sebascamayo.run.presentation.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject

// Se ejecuta un Foreground Serviceque actualiza notificacion a tiempo real
// Un foreground es persistente a diferencia de un service normal que es terminado por la app
class ActiveRunService: Service() {

    // lazy{} garantiza que solo se inicializa cuando se usa

    //Obtenemos el manager para gestionar notificaciones
    private val notificationManager by lazy {
        getSystemService<NotificationManager>()!!
    }

    // Estructura base de notificacion
    private val baseNotification by lazy {
        NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(com.sebascamayo.core.presentation.designsystem.R.drawable.logo)
            .setContentTitle(getString(R.string.active_run))
    }

    // Se inyecta el RunningTracker para obtener el tiempo
    private val runningTracker by inject<RunningTracker>()

    private var serviceScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    // Este no es un Bound Service por lo que es null
    // Un bound service hace que un Activity pueda comunicarse con el servicio directamente
    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    // Cada vez que se inicia el servicio
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        when(intent?.action) {
            ACTION_START -> {
                val activityClass = intent.getStringExtra(EXTRA_ACTIVITY_CLASS)
                    ?: throw IllegalArgumentException("No activity class provided")
                start(Class.forName(activityClass))
            }
            ACTION_STOP -> {
                stop()
            }
        }
        return START_STICKY
    }

    private fun start(activityClass: Class<*>) {
        if(!isServiceActive) {
            isServiceActive = true
            // Las notificaciones se integran en un canal para que existan
            createNotificationChannel()

            // Intent permite la comunicacion entre componentes de la app
            // Los componentes como Activity, Serive o Broadcast

            // Este intent explicito activa una actividad con su clase
            // Un intent implicito solo se le da una accion para interpretar
            val activityIntent = Intent(applicationContext, activityClass).apply {
                data = "runique://active_run".toUri() // Se añade data al intent
                addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP) // cambia comportamiento del intent
            }
            // Se crea una pila de actividades para una mejor gestion
            val pendingIntent = TaskStackBuilder.create(applicationContext).run {
                addNextIntentWithParentStack(activityIntent) // Apilamos el activity
                getPendingIntent(0, PendingIntent.FLAG_IMMUTABLE)// Obtenemos el pendingIntent
            }

            val notification = baseNotification
                .setContentText("00:00:00")
                .setContentIntent(pendingIntent)
                .build()

            // Ejecutamos el servicio en un primer plano con 1
            // Asi no es eliminado por el sistema
            startForeground(1, notification)
            updateNotification()
        }
    }

    // Actualizar la informacion escuchando el elapsedTime
    private fun updateNotification() {
        runningTracker.elapsedTime.onEach { elapsedTime ->
            val notification = baseNotification
                .setContentText(elapsedTime.formatted())
                .build()

            // Se actualiza el texto
            notificationManager.notify(1, notification)
        }.launchIn(serviceScope)
    }

    fun stop() {
        stopSelf()
        isServiceActive = false
        serviceScope.cancel()

        serviceScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    }

    // Creamos el canal que será el hilo por el cual persista    la notificacion
    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= 26)
        {
            val channel = NotificationChannel(
                CHANNEL_ID,
                getString(R.string.active_run),
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        var isServiceActive = false
        private const val CHANNEL_ID = "active_run"

        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"

        private const val EXTRA_ACTIVITY_CLASS = "EXTRA_ACTIVITY_CLASS"

        fun createStartIntent(context: Context, activityClass: Class<*>): Intent {
            return Intent(context, ActiveRunService::class.java). apply {
                action = ACTION_START
                // Puedes transportar y recibir informacion mediante un Extra
                putExtra(EXTRA_ACTIVITY_CLASS, activityClass.name)
            }
        }

        fun createStopIntent(context: Context): Intent {
            return Intent(context, ActiveRunService::class.java).apply {
                action = ACTION_STOP
            }
        }
    }
}