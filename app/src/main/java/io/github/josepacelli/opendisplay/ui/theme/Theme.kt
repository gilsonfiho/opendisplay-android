package io.github.josepacelli.opendisplay.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.josepacelli.opendisplay.R

private val DarkColors = darkColorScheme(
    primary = Color(0xFFE8ECF4),
    onPrimary = Color(0xFF0E1526),
    primaryContainer = Color(0xFF26314F),
    onPrimaryContainer = Color(0xFFE8ECF4),
    secondary = Color(0xFFC3CBDA),
    onSecondary = Color(0xFF0E1526),
    secondaryContainer = Color(0xFF222C47),
    onSecondaryContainer = Color(0xFFE8ECF4),
    tertiary = Color(0xFF4C8DFF),
    onTertiary = Color(0xFF0E1526),
    background = Color(0xFF0E1526),
    onBackground = Color(0xFFE8ECF4),
    surface = Color(0xFF0E1526),
    onSurface = Color(0xFFE8ECF4),
    surfaceVariant = Color(0xFF1B2440),
    onSurfaceVariant = Color(0xFF9AA4B8),
    surfaceContainerLowest = Color(0xFF0A101E),
    surfaceContainerLow = Color(0xFF141C30),
    surfaceContainer = Color(0xFF172036),
    surfaceContainerHigh = Color(0xFF1B2440),
    surfaceContainerHighest = Color(0xFF222C47),
    outline = Color(0xFF5A6580),
    outlineVariant = Color(0xFF2A3452),
    error = Color(0xFFFF6B5E),
    onError = Color(0xFF0E1526),
    errorContainer = Color(0xFF5C1F1A),
    onErrorContainer = Color(0xFFFFDAD5),
    inverseSurface = Color(0xFFE8ECF4),
    inverseOnSurface = Color(0xFF0E1526),
)

private val LightColors = lightColorScheme(
    primary = Color(0xFF0E1526),
    onPrimary = Color(0xFFF2F5FA),
    primaryContainer = Color(0xFFD8DFEC),
    onPrimaryContainer = Color(0xFF0E1526),
    secondary = Color(0xFF3F4A63),
    onSecondary = Color(0xFFF2F5FA),
    secondaryContainer = Color(0xFFDCE2EE),
    onSecondaryContainer = Color(0xFF0E1526),
    tertiary = Color(0xFF1E5BD8),
    onTertiary = Color(0xFFFFFFFF),
    background = Color(0xFFE3E8F2),
    onBackground = Color(0xFF0E1526),
    surface = Color(0xFFF2F5FA),
    onSurface = Color(0xFF0E1526),
    surfaceVariant = Color(0xFFE3E8F2),
    onSurfaceVariant = Color(0xFF4F5970),
    surfaceContainerLowest = Color(0xFFFFFFFF),
    surfaceContainerLow = Color(0xFFF2F5FA),
    surfaceContainer = Color(0xFFEBEFF6),
    surfaceContainerHigh = Color(0xFFE3E8F2),
    surfaceContainerHighest = Color(0xFFD8DFEC),
    outline = Color(0xFF7D879B),
    outlineVariant = Color(0xFFC9D1E0),
    error = Color(0xFFB3261E),
    onError = Color(0xFFFFFFFF),
    errorContainer = Color(0xFFF9DEDC),
    onErrorContainer = Color(0xFF410E0B),
    inverseSurface = Color(0xFF0E1526),
    inverseOnSurface = Color(0xFFE8ECF4),
)

/**
 * Indicator-lamp colours, the one place the theme spends saturated colour: each one names a
 * connection state, never decoration. Listening uses the scheme's `tertiary` blue; these cover
 * the "off" state, which Material has no role for. Problems use the scheme's `error`.
 *
 * @property off starting up or stopped — nothing to report.
 */
@Immutable
data class LampColors(val off: Color)

private val DarkLamps = LampColors(off = Color(0xFF5A6580))
private val LightLamps = LampColors(off = Color(0xFF9AA4B8))

/** The current theme's [LampColors], provided by [OpenDisplayTheme]. */
val LocalLampColors = staticCompositionLocalOf { DarkLamps }

/**
 * Colours for chrome that floats over the Mac's video ([io.github.josepacelli.opendisplay.ui.ConnectedMenu],
 * the perf HUD): always the dark navy, whatever the system theme, since what sits behind it is
 * someone else's desktop.
 */
object OverlayColors {
    val strip = Color(0xE00E1526)
    val stripBorder = Color(0xFF2A3452)
    val button = Color(0xFF1B2440)
    val buttonOn = Color(0xFFE8ECF4)
    val ink = Color(0xFFE8ECF4)
    val inkOn = Color(0xFF0E1526)
    val danger = Color(0xFFE5484D)
}

private val Hanken = FontFamily(
    Font(R.font.hanken_grotesk_300, FontWeight.Light),
    Font(R.font.hanken_grotesk_400, FontWeight.Normal),
    Font(R.font.hanken_grotesk_500, FontWeight.Medium),
    Font(R.font.hanken_grotesk_600, FontWeight.SemiBold),
)

private val BaseType = Typography()

/** Hanken Grotesk carries display, headline and title roles; body and label stay on the
 * system face so long text and controls read like the rest of Android. */
private val AppTypography = Typography(
    displayLarge = TextStyle(fontFamily = Hanken, fontWeight = FontWeight.Light, fontSize = 88.sp, lineHeight = 92.sp, letterSpacing = (-1.5).sp),
    displayMedium = TextStyle(fontFamily = Hanken, fontWeight = FontWeight.Light, fontSize = 56.sp, lineHeight = 60.sp, letterSpacing = (-0.8).sp),
    displaySmall = BaseType.displaySmall.copy(fontFamily = Hanken, fontWeight = FontWeight.Light),
    headlineLarge = BaseType.headlineLarge.copy(fontFamily = Hanken, fontWeight = FontWeight.Normal),
    headlineMedium = BaseType.headlineMedium.copy(fontFamily = Hanken, fontWeight = FontWeight.Normal),
    headlineSmall = BaseType.headlineSmall.copy(fontFamily = Hanken, fontWeight = FontWeight.Medium),
    titleLarge = BaseType.titleLarge.copy(fontFamily = Hanken, fontWeight = FontWeight.Medium),
    titleMedium = BaseType.titleMedium.copy(fontFamily = Hanken, fontWeight = FontWeight.SemiBold),
    titleSmall = BaseType.titleSmall.copy(fontFamily = Hanken, fontWeight = FontWeight.SemiBold),
)

private val AppShapes = Shapes(
    extraSmall = RoundedCornerShape(6.dp),
    small = RoundedCornerShape(10.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(20.dp),
    extraLarge = RoundedCornerShape(28.dp),
)

/**
 * Navy desk-hardware theme: a dark and a light scheme built on the same navy/blue family as the
 * app icon, Hanken Grotesk for display and titles, and [LampColors] for connection state.
 */
@Composable
fun OpenDisplayTheme(content: @Composable () -> Unit) {
    val dark = isSystemInDarkTheme()
    CompositionLocalProvider(LocalLampColors provides if (dark) DarkLamps else LightLamps) {
        MaterialTheme(
            colorScheme = if (dark) DarkColors else LightColors,
            typography = AppTypography,
            shapes = AppShapes,
            content = content,
        )
    }
}
