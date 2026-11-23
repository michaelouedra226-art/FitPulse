package com.example.fitpulse.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitpulse.ui.theme.*

// ─── Section Header ────────────────────────────────────────────────────────────
@Composable
fun SectionHeader(title: String, modifier: Modifier = Modifier, accent: Boolean = false) {
    Row(
        modifier = modifier
            .padding(top = 20.dp, bottom = 12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (accent) {
            Box(
                Modifier
                    .size(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(NeonGreen)
            )
        }
        Text(
            title.uppercase(),
            style = MaterialTheme.typography.labelMedium,
            color = if (accent) NeonGreen else TextMid
        )
        Spacer(Modifier.weight(1f))
    }
}

// ─── Glass Card ────────────────────────────────────────────────────────────────
@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    glow: Color = Color.Transparent,
    content: @Composable ColumnScope.() -> Unit
) {
    val bg = if (glow != Color.Transparent)
        Brush.verticalGradient(listOf(glow.copy(alpha = 0.12f), BgCard))
    else
        null

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(BgCard)
            .then(if (bg != null) Modifier.background(bg) else Modifier)
            .border(1.dp, BorderGlass, RoundedCornerShape(20.dp))
            .padding(18.dp)
    ) {
        Column(content = content, modifier = Modifier.fillMaxWidth())
    }
}

// ─── Stat Card ─────────────────────────────────────────────────────────────────
@Composable
fun StatCard(
    label: String,
    value: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    accent: Color = NeonGreen
) {
    GlassCard(modifier = modifier, glow = accent) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(accent.copy(alpha = 0.12f))
                    .border(1.dp, accent.copy(alpha = 0.2f), RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, null, tint = accent, modifier = Modifier.size(20.dp))
            }
            Spacer(Modifier.width(12.dp))
            Column {
                Text(
                    value,
                    style = MaterialTheme.typography.headlineMedium,
                    color = TextHi,
                    fontWeight = FontWeight.Black
                )
                Text(label, style = MaterialTheme.typography.labelMedium, color = TextMid)
            }
        }
    }
}

// ─── Primary Button ────────────────────────────────────────────────────────────
@Composable
fun NeonButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color = NeonGreen,
    icon: ImageVector? = null
) {
    Box(
        modifier = modifier
            .height(48.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(
                Brush.horizontalGradient(listOf(
                    color.copy(alpha = 0.95f),
                    color.copy(alpha = 0.7f)
                ))
            )
            .border(1.dp, color.copy(alpha = 0.4f), RoundedCornerShape(14.dp)),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            if (icon != null) {
                Icon(icon, null, tint = Color(0xFF041A0E), modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(8.dp))
            }
            Text(
                text,
                color = Color(0xFF041A0E),
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}

// ─── Ghost Button ──────────────────────────────────────────────────────────────
@Composable
fun GhostButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color = TextHi
) {
    Box(
        modifier = modifier
            .height(44.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(BgCard)
            .border(1.dp, BorderGlass, RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(text, color = color, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
    }
}

// ─── Chip / Tag ────────────────────────────────────────────────────────────────
@Composable
fun Tag(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = NeonCyan
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(color.copy(alpha = 0.1f))
            .border(1.dp, color.copy(alpha = 0.25f), RoundedCornerShape(8.dp))
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        Text(text, color = color, fontSize = 11.sp, fontWeight = FontWeight.Medium)
    }
}

// ─── Progress Ring ─────────────────────────────────────────────────────────────
@Composable
fun ProgressRing(
    progress: Float,
    size: Int = 80,
    strokeWidth: Int = 6,
    color: Color = NeonGreen,
    modifier: Modifier = Modifier,
    label: String = ""
) {
    Box(modifier = modifier.size(size.dp), contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.size(size.dp)) {
            val r = (size - strokeWidth) / 2f
            // background ring
            drawArc(
                color = Color.White.copy(alpha = 0.08f),
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(strokeWidth = strokeWidth.dp.toPx(), cap = StrokeCap.Round)
            )
            // progress ring
            drawArc(
                color = color,
                startAngle = -90f,
                sweepAngle = 360f * progress.coerceIn(0f, 1f),
                useCenter = false,
                style = Stroke(strokeWidth = strokeWidth.dp.toPx(), cap = StrokeCap.Round)
            )
        }
        if (label.isNotEmpty()) {
            Text(
                label,
                style = MaterialTheme.typography.labelMedium,
                color = TextHi,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// ─── Empty State ───────────────────────────────────────────────────────────────
@Composable
fun EmptyState(title: String, subtitle: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("○", fontSize = 48.sp, color = TextLo)
        Spacer(Modifier.height(12.dp))
        Text(title, style = MaterialTheme.typography.titleLarge, color = TextHi, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(4.dp))
        Text(subtitle, style = MaterialTheme.typography.bodyMedium, color = TextMid, textAlign = TextAlign.Center)
    }
}

// ─── Gradient Hero Card ────────────────────────────────────────────────────────
@Composable
fun HeroCard(
    title: String,
    subtitle: String = "",
    modifier: Modifier = Modifier,
    action: @Composable (() -> Unit)? = null
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(
                Brush.linearGradient(
                    listOf(
                        NeonGreen.copy(alpha = 0.18f),
                        NeonCyan.copy(alpha = 0.08f),
                        BgMid
                    )
                )
            )
            .border(1.dp, BorderGlass, RoundedCornerShape(24.dp))
            .padding(20.dp)
    ) {
        Column {
            Text(title, style = MaterialTheme.typography.headlineMedium, color = TextHi, fontWeight = FontWeight.ExtraBold)
            if (subtitle.isNotEmpty()) {
                Spacer(Modifier.height(4.dp))
                Text(subtitle, style = MaterialTheme.typography.bodyMedium, color = TextMid)
            }
            if (action != null) {
                Spacer(Modifier.height(16.dp))
                Row(action = action)
            }
        }
    }
}
