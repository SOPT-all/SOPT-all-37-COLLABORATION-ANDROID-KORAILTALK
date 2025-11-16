package org.sopt.korailtalk.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import org.sopt.korailtalk.core.designsystem.theme.KorailTalkTheme
import org.sopt.korailtalk.presentation.main.MainScreen
import org.sopt.korailtalk.presentation.main.rememberMainNavigator

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KorailTalkTheme {
                val navigator = rememberMainNavigator()
                MainScreen(
                    navigator = navigator
                )
            }
        }
    }
}