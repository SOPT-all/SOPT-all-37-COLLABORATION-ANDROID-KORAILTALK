package org.sopt.korailtalk.core.common.util.extension

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.input.pointer.pointerInput

@Composable
inline fun Modifier.noRippleClickable(
    enabled: Boolean = true,
    crossinline onClick: () -> Unit
): Modifier = composed {
    this.clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() },
        onClick = { onClick() },
        enabled = enabled
    )
}

inline fun Modifier.pressedClickable(
    crossinline changePressed: (Boolean) -> Unit = {},
    crossinline onClick: () -> Unit,
    throttleDelay: Long = 300L
): Modifier = composed {
    // Compose 뷰에 pressed 상태 콜백과 클릭 throttle 기능을 추가해,
    // 터치 시작/종료 시 changePressed를 호출하고 일정 간격 이상일 때만 onClick을 실행하도록 한다

    var lastClickTime by remember { mutableLongStateOf(0L) }

    pointerInput(Unit) {
        detectTapGestures(
            onPress = {
                changePressed(true)
                tryAwaitRelease()
                changePressed(false)
            },
            onTap = {
                val currentTime = System.currentTimeMillis()
                if (currentTime - lastClickTime >= throttleDelay) {
                    lastClickTime = currentTime
                    onClick()
                }
            }
        )
    }
}