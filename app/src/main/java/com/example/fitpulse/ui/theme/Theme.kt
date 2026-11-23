package com.example.fitpulse.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp

// ─── Palette ───────────────────────────────────────────────────────────────────
val BgDeep     = Color(0xFF070B14)
val BgMid      = Color(0xFF0C1220)
val BgCard     = Color(0x1FFFFFFF)
val BgCardGlow = Color(0x0D3DFF8F)

val NeonGreen   = Color(0xFF3DFF8F)
val NeonCyan    = Color(0xFF4DD9FF)
val NeonPurple  = Color(0xFF9B7BFF)
val NeonAmber   = Color(0xFFFFC555)
val NeonRed     = Color(0xFFFF5C7A)

val TextHi      = Color(0xFFEAF0FF)
val TextMid     = Color(0xFF8A94B0)
val TextLo      = Color(0xFF4A5468)

val BorderGlass = Color(0x1FFFFFFF)
val GlowGreen   = Color(0x403DFF8F)
val GlowCyan    = Color(0x404DD9FF)

val DarkBg        = BgDeep
val DarkSurface   = BgMid
val DarkSurface2  = Color(0xFF141C30)
val DarkBorder    = BorderGlass
val TextPrimary   = TextHi
val TextSecondary = TextMid
val AccentOrange  = NeonAmber
val AccentRed     = NeonRed

// ─── Color Scheme ──────────────────────────────────────────────────────────────
private val DarkColors = darkColorScheme(
    primary        = NeonGreen,
    onPrimary      = Color(0xFF041A0E),
    secondary      = NeonCyan,
    onSecondary    = Color(0xFF041820),
    tertiary       = NeonPurple,
    background     = BgDeep,
    onBackground   = TextHi,
    surface        = BgMid,
    onSurface      = TextHi,
    surfaceVariant = Color(0xFF141C30),
    onSurfaceVariant = TextMid,
    outline        = BorderGlass,
    error          = NeonRed,
    onError        = Color.White
)

// ─── Typography ────────────────────────────────────────────────────────────────
private val AppTypography = Typography(
    displayLarge   = TextStyle(fontWeight = FontWeight.Black,      fontSize = 32.sp, letterSpacing = (-0.5).sp),
    headlineLarge  = TextStyle(fontWeight = FontWeight.ExtraBold,  fontSize = 24.sp),
    headlineMedium = TextStyle(fontWeight = FontWeight.Bold,       fontSize = 20.sp),
    titleLarge     = TextStyle(fontWeight = FontWeight.SemiBold,   fontSize = 17.sp),
    titleMedium    = TextStyle(fontWeight = FontWeight.Medium,     fontSize = 15.sp),
    bodyLarge      = TextStyle(fontWeight = FontWeight.Normal,    fontSize = 15.sp, color = TextHi),
    bodyMedium     = TextStyle(fontWeight = FontWeight.Normal,    fontSize = 13.sp, color = TextMid),
    labelLarge     = TextStyle(fontWeight = FontWeight.Bold,      fontSize = 13.sp, letterSpacing = 0.3.sp, color = TextHi),
    labelMedium    = TextStyle(fontWeight = FontWeight.Medium,    fontSize = 11.sp, letterSpacing = 0.5.sp, color = TextMid),
    labelSmall     = TextStyle(fontWeight = FontWeight.Normal,    fontSize = 10.sp, color = TextLo)
)

// ─── Shapes ────────────────────────────────────────────────────────────────────
private val AppShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small      = RoundedCornerShape(12.dp),
    medium     = RoundedCornerShape(16.dp),
    large      = RoundedCornerShape(20.dp),
    extraLarge = RoundedCornerShape(28.dp)
)

// ─── Theme ─────────────────────────────────────────────────────────────────────
@Composable
fun FitPulseTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColors,
        typography  = AppTypography,
        shapes      = AppShapes,
        content     = content
    )
}
