package com.example.fitpulse.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColors = darkColorScheme(
    primary = NeonGreen,
    onPrimary = Color(0xFF003314),
    secondary = NeonCyan,
    onSecondary = Color(0xFF003338),
    tertiary = NeonPurple,
    background = DarkBg,
    onBackground = TextPrimary,
    surface = DarkSurface,
    onSurface = TextPrimary,
    surfaceVariant = DarkSurface2,
    onSurfaceVariant = TextSecondary,
    outline = DarkBorder,
    error = AccentRed
)

@Composable
fun FitPulseTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = DarkColors, typography = AppTypography, shapes = AppShapes, content = content)
}
