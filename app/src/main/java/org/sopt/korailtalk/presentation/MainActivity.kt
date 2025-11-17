package org.sopt.korailtalk.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.korailtalk.core.designsystem.theme.KORAILTALKTheme
import org.sopt.korailtalk.presentation.main.MainScreen
import org.sopt.korailtalk.presentation.main.rememberMainNavigator

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KORAILTALKTheme {
                val navigator = rememberMainNavigator()
                MainScreen(
                    navigator = navigator
                )
            }
        }
    }
}
