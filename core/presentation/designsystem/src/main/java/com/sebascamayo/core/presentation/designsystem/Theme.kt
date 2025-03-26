package com.sebascamayo.core.presentation.designsystem

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

val DarkColorScheme = darkColorScheme(
    primary = RuniqueGreen,
    background = RuniqueBlack,
    surface = RuniqueDarkGray,
    secondary = RuniqueWhite,
    tertiary = RuniqueWhite,
    primaryContainer = RuniqueGreen30,
    onPrimary = RuniqueBlack,
    onBackground = RuniqueWhite,
    onSurface = RuniqueWhite,
    onSurfaceVariant = RuniqueGray,
    error = RuniqueDarkRed,
    errorContainer = RuniqueDarkRed5
)

private val LightColorScheme = lightColorScheme(
    primary = RuniqueGreen,
    secondary = RuniqueGray,
    tertiary = RuniqueDarkGray
)

@Composable
fun MyOwnRuniqueTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    /*val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }*/

    /*val view = LocalView. current
    if (!view. isInEditMode) {
        SideEffect {
            val window = (view.context as Activity) .window
            WindowCompat.getInsetsController(window, view) . isAppearanceLightStatusBars = false

        }

    }*/

    val colorScheme = DarkColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        //typography = Typography,
        content = content
    )
}