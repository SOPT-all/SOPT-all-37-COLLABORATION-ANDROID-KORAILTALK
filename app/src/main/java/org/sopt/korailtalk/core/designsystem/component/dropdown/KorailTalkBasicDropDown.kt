package org.sopt.korailtalk.core.designsystem.component.dropdown

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import org.sopt.korailtalk.core.common.util.extension.noRippleClickable
import org.sopt.korailtalk.core.designsystem.theme.KorailTalkTheme


@Composable
fun KorailTalkDropdown(
    items: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val measurer = rememberTextMeasurer()

    // 선택된 텍스트 기준 너비 계산 + 아이콘/패딩 여유분
    val textWidth = LocalDensity.current.run {
        measurer.measure(
            text = selectedItem,
            style = KorailTalkTheme.typography.body.body2M15
        ).size.width.toDp() + 36.dp
    }

    // 화살표 회전 애니메이션
    val rotateAngle by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        label = "ArrowAnimation"
    )

    Box(
        modifier = modifier
            .width(textWidth)
            .zIndex(1f) // 다른 컴포넌트 위에 뜨도록
    ) {
        // 상단 선택 영역 (앵커)
        Row(
            Modifier
                .fillMaxWidth()
                .height(36.dp)
                .background(
                    color = KorailTalkTheme.colors.white,
                    shape = RoundedCornerShape(size = 4.dp)
                )
                .border(
                    width = 1.dp,
                    color = KorailTalkTheme.colors.gray200,
                    shape = RoundedCornerShape(size = 4.dp)
                )
                .clickable {
                    expanded = !expanded
                    onItemSelected(items.first())
                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (expanded) items[0] else selectedItem,
                color = KorailTalkTheme.colors.gray500,
                style = KorailTalkTheme.typography.body.body2M15,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(1f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Dropdown Arrow",
                modifier = Modifier
                    .size(24.dp)
                    .rotate(rotateAngle)
                    .padding(end = 4.dp),
                tint = KorailTalkTheme.colors.gray500
            )
        }

        // 팝업 드롭다운 메뉴
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            offset = DpOffset(x = 0.dp, y = (-2).dp),
            shadowElevation = 0.dp,
            modifier = Modifier
                .width(textWidth)
                .background(
                    color = KorailTalkTheme.colors.white,
                    shape = RoundedCornerShape(4.dp)
                )
                .border(
                    width = 1.dp,
                    color = KorailTalkTheme.colors.gray200,
                    shape = RoundedCornerShape(4.dp)
                )
        ) {
            val itemsToShow = items.drop(1)

            itemsToShow.forEachIndexed { index, item ->
                DropdownItem(
                    item = item,
                    onItemClick = {
                        onItemSelected(item)
                        expanded = false
                    }
                )

                if (index < itemsToShow.lastIndex) {
                    HorizontalDivider(
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        thickness = 1.dp,
                        color = KorailTalkTheme.colors.gray100
                    )
                }
            }
        }
    }
}

@Composable
fun DropdownItem(
    item: String,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(KorailTalkTheme.colors.white)
            .noRippleClickable(onClick = onItemClick)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(30.dp)
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = item,
                color = KorailTalkTheme.colors.gray500,
                style = KorailTalkTheme.typography.body.body2M15,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun KorailTalkDropdownPreview() {
    val seatList = listOf("전체", "일반실", "특실")
    val routeList = listOf("직통어쩌고", "환승저쩌고")
    var seatType by remember { mutableStateOf("전체") }
    var routeType by remember { mutableStateOf("직통") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp, start = 20.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 좌석 등급 드롭다운
            KorailTalkDropdown(
                items = seatList,
                selectedItem = seatType,
                onItemSelected = { seatType = it }
            )
            // 운행 종류 드롭다운
            KorailTalkDropdown(
                items = routeList,
                selectedItem = routeType,
                onItemSelected = { routeType = it }
            )
        }
        //정상 작동하는지 확인 하기 위한 코드
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "선택값: $seatType / $routeType")
    }

}
