package com.example.wearworldtime.ui.theme

import androidx.compose.runtime.Composable
import androidx.wear.compose.material.Colors
import androidx.wear.compose.material.MaterialTheme

private val WearColorPalette = Colors(
    primary = PrimaryTeal,
    primaryVariant = SecondaryAqua,
    secondary = GlowAmber,
    secondaryVariant = AccentGold,
    background = Black,
    surface = CardBackground,
    onPrimary = Black,
    onSecondary = Black,
    onBackground = TextPrimary,
    onSurface = TextPrimary
)

@Composable
fun WearWorldTimeTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = WearColorPalette,
        typography = Typography,
        content = content
    )
}
