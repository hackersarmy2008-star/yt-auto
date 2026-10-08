package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val ShortsPilotColorScheme = darkColorScheme(
    primary = ShortsCrimson,
    onPrimary = Color.White,
    primaryContainer = ShortsCrimsonDark,
    onPrimaryContainer = Color.White,
    secondary = ElectricCyan,
    onSecondary = ObsidianBackground,
    secondaryContainer = ElectricCyanDim,
    onSecondaryContainer = Color.White,
    tertiary = StrategyViolet,
    onTertiary = Color.White,
    background = ObsidianBackground,
    onBackground = TextPrimary,
    surface = ObsidianSurface,
    onSurface = TextPrimary,
    surfaceVariant = ObsidianSurfaceHigh,
    onSurfaceVariant = TextSecondary,
    outline = BorderSubtle,
    error = CoralAlert,
    onError = Color.White
)

@Composable
fun MyApplicationTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = ShortsPilotColorScheme,
        typography = Typography,
        content = content
    )
}
