package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme =
  darkColorScheme(
    primary = BrandGreenAccent,
    onPrimary = Color.Black,
    primaryContainer = BrandGreenDark,
    onPrimaryContainer = Color.White,
    secondary = BrandBlue,
    onSecondary = Color.White,
    tertiary = BrandAmber,
    background = DarkBackground,
    onBackground = Color(0xFFF1F5F9),
    surface = DarkSurface,
    onSurface = Color(0xFFF1F5F9),
    surfaceVariant = Color(0xFF334155),
    onSurfaceVariant = Color(0xFFCBD5E1),
    outline = DarkBorder,
  )

private val LightColorScheme =
  lightColorScheme(
    primary = BrandGreen,
    onPrimary = Color.White,
    primaryContainer = BrandGreenLight,
    onPrimaryContainer = BrandGreenDark,
    secondary = BrandBlue,
    onSecondary = Color.White,
    tertiary = BrandAmber,
    background = BackgroundLight,
    onBackground = NeutralDark,
    surface = SurfaceWhite,
    onSurface = NeutralDark,
    surfaceVariant = BrandGreenSurface,
    onSurfaceVariant = NeutralMedium,
    outline = BorderLight,
  )

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false, // Keep consistent brand identity
  content: @Composable () -> Unit,
) {
  val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}

