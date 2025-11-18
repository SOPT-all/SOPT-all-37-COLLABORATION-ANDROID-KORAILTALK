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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

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

/**
 * topBorder = Border 사용처 중에 top만 필요한 경우 사용
 * bottomBorder = Border 사용처 중에 bottom만 필요한 경우 사용
 * sideBorder = Border 사용처 중에 side만 필요한 경우 사용
 *
 * 각 border는 실제 수치의 2배를 넣어줘야 함
 * 이유) strokeWidth가 drawLine 및 drawArc에서 중앙을 기준으로 양쪽으로 그려지기 때문
 * */

fun Modifier.topBorder(strokeWidth: Dp, color: Color, cornerRadiusDp: Dp) = composed(
    factory = {
        val density = LocalDensity.current
        val strokeWidthPx = density.run { strokeWidth.toPx() }
        val cornerRadiusPx = density.run { cornerRadiusDp.toPx() }

        Modifier.drawBehind {
            val width = size.width
            val height = size.height

            drawLine(
                color = color,
                start = Offset(x = 0f, y = height+2),
                end = Offset(x = 0f, y = cornerRadiusPx),
                strokeWidth = strokeWidthPx
            )

            drawArc(
                color = color,
                startAngle = 180f,
                sweepAngle = 90f,
                useCenter = false,
                topLeft = Offset.Zero,
                size = Size(cornerRadiusPx * 2, cornerRadiusPx * 2),
                style = Stroke(width = strokeWidthPx)
            )

            drawLine(
                color = color,
                start = Offset(x = cornerRadiusPx, y = 0f),
                end = Offset(x = width - cornerRadiusPx, y = 0f),
                strokeWidth = strokeWidthPx
            )

            drawArc(
                color = color,
                startAngle = 270f,
                sweepAngle = 90f,
                useCenter = false,
                topLeft = Offset(x = width - cornerRadiusPx * 2, y = 0f),
                size = Size(cornerRadiusPx * 2, cornerRadiusPx * 2),
                style = Stroke(width = strokeWidthPx)
            )

            drawLine(
                color = color,
                start = Offset(x = width, y = height+2),
                end = Offset(x = width, y = cornerRadiusPx),
                strokeWidth = strokeWidthPx
            )
        }
    }
)

fun Modifier.bottomBorder(strokeWidth: Dp, color: Color, cornerRadiusDp: Dp) = composed(
    factory = {
        val density = LocalDensity.current
        val strokeWidthPx = density.run { strokeWidth.toPx() }
        val cornerRadiusPx = density.run { cornerRadiusDp.toPx() }

        Modifier.drawBehind {
            val width = size.width
            val height = size.height

            drawLine(
                color = color,
                start = Offset(x = 0f, y = -2f),
                end = Offset(x = 0f, y = height-cornerRadiusPx),
                strokeWidth = strokeWidthPx
            )

            drawArc(
                color = color,
                startAngle = 90f,
                sweepAngle = 90f,
                useCenter = false,
                topLeft = Offset(x = 0f, y = height - cornerRadiusPx * 2),
                size = Size(cornerRadiusPx * 2, cornerRadiusPx * 2),
                style = Stroke(width = strokeWidthPx)
            )

            drawLine(
                color = color,
                start = Offset(x = cornerRadiusPx, y = height),
                end = Offset(x = width - cornerRadiusPx, y = height),
                strokeWidth = strokeWidthPx
            )

            drawArc(
                color = color,
                startAngle = 0f,
                sweepAngle = 90f,
                useCenter = false,
                topLeft = Offset(x = width - cornerRadiusPx * 2, y = height - cornerRadiusPx * 2),
                size = Size(cornerRadiusPx * 2, cornerRadiusPx * 2),
                style = Stroke(width = strokeWidthPx)
            )

            drawLine(
                color = color,
                start = Offset(x = width, y = -2f),
                end = Offset(x = width, y = height - cornerRadiusPx),
                strokeWidth = strokeWidthPx
            )
        }
    }
)

fun Modifier.sideBorder(strokeWidth: Dp, color: Color) = composed(
    factory = {
        val density = LocalDensity.current
        val strokeWidthPx = density.run { strokeWidth.toPx() }

        Modifier.drawBehind {
            val width = size.width
            val height = size.height

            drawLine(
                color = color,
                start = Offset(x = 0f, y = -2f),
                end = Offset(x = 0f, y = height+2),
                strokeWidth = strokeWidthPx
            )

            drawLine(
                color = color,
                start = Offset(x = width, y = -2f),
                end = Offset(x = width, y = height+2),
                strokeWidth = strokeWidthPx
            )
        }
    }
)