package com.sebascamayo.dynamicfeature

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.play.core.splitcompat.SplitCompat
import com.sebascamayo.analytics.data.di.analyticsModule
import com.sebascamayo.analytics.presentation.AnalyticsDashboardScreenRoot
import com.sebascamayo.analytics.presentation.di.analyticsPresentationModule
import com.sebascamayo.core.presentation.designsystem.MyOwnRuniqueTheme
import org.koin.core.context.loadKoinModules

class AnalyticsActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadKoinModules(analyticsModule)
        SplitCompat.installActivity(this)

        setContent {
            MyOwnRuniqueTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "analytics_dashboard"
                ) {
                    composable("analytics_dashboard") {
                        AnalyticsDashboardScreenRoot(
                            onBackClick = { finish() }
                        )
                    }
                }
            }
        }
    }
}