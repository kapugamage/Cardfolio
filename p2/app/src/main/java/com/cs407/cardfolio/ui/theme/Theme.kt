package com.cs407.cardfolio.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// Existing Material color schemes
private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

// 1.6.2 Extend the Theme
data class CustomColors(
    val gradientTop: Color,
    val gradientBottom: Color
)

val LightCustomColors = CustomColors(
    gradientTop = LightGradientTop,
    gradientBottom = LightGradientBottom
)

val DarkCustomColors = CustomColors(
    gradientTop = DarkGradientTop,
    gradientBottom = DarkGradientBottom
)

private val LocalCustomColors = staticCompositionLocalOf {
    CustomColors(
        gradientTop = Color.Unspecified,
        gradientBottom = Color.Unspecified
    )
}

// AppTheme accessor
object AppTheme {
    val customColors: CustomColors
        @Composable
        @ReadOnlyComposable
        get() = LocalCustomColors.current
}

@Composable
fun CardfolioTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colors = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    // 1.6.3 Provide Custom Colors
    val currentCustomColors = if (darkTheme) DarkCustomColors else LightCustomColors

    CompositionLocalProvider(LocalCustomColors provides currentCustomColors) {
        MaterialTheme(
            colorScheme = colors,
            typography = Typography,
            content = content
        )
    }
}
