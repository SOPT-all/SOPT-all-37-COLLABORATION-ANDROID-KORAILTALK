package org.sopt.korailtalk.core.designsystem.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat


object KorailTalkTheme {
    val colors: KorailTalkColors
        @Composable
        @ReadOnlyComposable
        get() = LocalKorailTalkColorsProvider.current

    val typography: KorailTalkTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalKorailTalkTypographyProvider.current
}

@Composable
fun ProvideKorailTalkColorsAndTypography(
    colors: KorailTalkColors,
    typography: KorailTalkTypography,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalKorailTalkColorsProvider provides colors,
        LocalKorailTalkTypographyProvider provides typography,
        content = content
    )
}

@Composable
fun KORAILTALKTheme(
    content: @Composable () -> Unit
) {
    ProvideKorailTalkColorsAndTypography(
        colors = defaultKorailTalkColors,
        typography = defaultKorailTalkTypography
    ) {
        val view = LocalView.current
        if (!view.isInEditMode) {
            // optional
            SideEffect {
                (view.context as Activity).window.run {
                    WindowCompat.getInsetsController(this, view).isAppearanceLightStatusBars = true
                }
            }
        }
        MaterialTheme(
            content = content
        )
    }
}
