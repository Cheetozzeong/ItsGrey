package com.tntt.core.designsystem.theme

import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

/**
 * Light default theme color scheme
 */
@VisibleForTesting
val LightDefaultColorScheme = lightColorScheme(
    primary = GrayF9,
    inversePrimary = GrayE9,
    onPrimary = Black,
    primaryContainer = GrayF9,
    onPrimaryContainer = Black,
    secondary = GrayF2,
    onSecondary = Gray8A,
    secondaryContainer = GrayF2,
    onSecondaryContainer = Gray8A,
    tertiary = GrayDE,
//    onTertiary = White,
//    tertiaryContainer = Blue90,
//    onTertiaryContainer = Blue10,
    error = Red2F,
    onError = White,
    errorContainer = Red2F,
    onErrorContainer = White,
    background = White,
    onBackground = Black,
    surface = White,
    onSurface = Black,
    surfaceVariant = White,
    onSurfaceVariant = Black,
    inverseSurface = Black,
    inverseOnSurface = White,
    outline = BlueEA,
    outlineVariant = Orange25,
)

/**
 * Dark default theme color scheme
 */
@VisibleForTesting
val DarkDefaultColorScheme = darkColorScheme(
    primary = GrayF9,
    inversePrimary = GrayE9,
    onPrimary = Black,
    primaryContainer = GrayF9,
    onPrimaryContainer = Black,
    secondary = GrayF2,
    onSecondary = Gray8A,
    secondaryContainer = GrayF2,
    onSecondaryContainer = Gray8A,
    tertiary = GrayDE,
//    onTertiary = White,
//    tertiaryContainer = Blue90,
//    onTertiaryContainer = Blue10,
    error = Red2F,
    onError = White,
    errorContainer = Red2F,
    onErrorContainer = White,
    background = Black,
    onBackground = White,
    surface = Black,
    onSurface = White,
    surfaceVariant = Black,
    onSurfaceVariant = White,
    inverseSurface = White,
    inverseOnSurface = Black,
    outline = BlueEA,
    outlineVariant = Orange25,
)


@Composable
fun IgTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    androidTheme: Boolean = false,
    disableDynamicTheming: Boolean = true,
    content: @Composable () -> Unit,
) {
    // Color scheme
    val colorScheme = when {
        androidTheme -> if (darkTheme) DarkDefaultColorScheme else LightDefaultColorScheme
        !disableDynamicTheming && supportsDynamicTheming() -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        else -> if (darkTheme) DarkDefaultColorScheme else LightDefaultColorScheme
    }
    // Composition locals
    CompositionLocalProvider {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = IgTypography,
            content = content,
        )
    }
}

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
fun supportsDynamicTheming() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S