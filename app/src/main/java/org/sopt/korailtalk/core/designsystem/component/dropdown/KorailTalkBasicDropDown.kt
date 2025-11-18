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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import org.sopt.korailtalk.core.designsystem.theme.KorailTalkTheme


@Composable
fun KorailTalkDropdown(
    items: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    // 화살표
    val rotateAngle by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        label = "ArrowAnimation"
    )

    Box(
        Modifier
            .width(68.dp)
            .zIndex(1f)
    ) {
        Column(
            Modifier
                .width(68.dp)
                .background(
                    color = KorailTalkTheme.colors.white,
                    shape = RoundedCornerShape(size = 4.dp)
                )
                .border(
                    width = 1.dp,
                    color = KorailTalkTheme.colors.gray200,
                    shape = RoundedCornerShape(size = 4.dp)
                )
                .padding(bottom = if (expanded) 4.dp else 0.dp)
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .height(36.dp)
                    .clickable { expanded = !expanded }
                    .padding(start = 8.dp, top = 5.dp, end = 2.dp, bottom = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = selectedItem,
                    color = KorailTalkTheme.colors.gray500,
                    style = KorailTalkTheme.typography.body.body2M15,

                    )
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown Arrow",
                    modifier = modifier
                        .size(24.dp)
                        .rotate(rotateAngle),
                    tint = KorailTalkTheme.colors.gray500
                )
            }

            // 펼쳐졌을 때 나오는 리스트 영역
            if (expanded) {
                Spacer(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(KorailTalkTheme.colors.gray100)
                )

                val otherItems = items.filter { it != selectedItem }

                otherItems.forEachIndexed { index, item ->
                    DropdownItem(
                        item = item,
                        onItemClick = {
                            onItemSelected(item)
                            expanded = false
                        },
                        isLastItem = index == otherItems.lastIndex
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}

@Composable
fun DropdownItem(
    item: String,
    onItemClick: () -> Unit,
    isLastItem: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onItemClick() }
    ) {
        Row(
            modifier = modifier
                .height(36.dp)
                .padding(start = 8.dp, top = 5.dp, end = 2.dp, bottom = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = item,
                color = KorailTalkTheme.colors.gray500,
                style = KorailTalkTheme.typography.body.body2M15
            )
        }

        if (!isLastItem) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(KorailTalkTheme.colors.gray100)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable

private fun KorailTalkDropdownPreview() {
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
                items = listOf("전체", "일반실", "특실"),
                selectedItem = seatType,
                onItemSelected = { seatType = it }
            )
            // 운행 종류 드롭다운
            KorailTalkDropdown(
                items = listOf("직통", "환승"),
                selectedItem = routeType,
                onItemSelected = { routeType = it }
            )

        }
        //정상 작동하는지 확인 하기 위한 코드
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "선택값: $seatType / $routeType")


    }

}
