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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
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
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
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

    val textWidth = LocalDensity.current.run {
        measurer.measure(
            text = selectedItem,
            style = KorailTalkTheme.typography.body.body2M15
        ).size.width.toDp() + 36.dp
    }


    // 화살표
    val rotateAngle by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        label = "ArrowAnimation"
    )

    Box(
        modifier
            .zIndex(1f)
    ) {
        Column(
            Modifier
                .width(textWidth)
                .background(
                    color = KorailTalkTheme.colors.white,
                    shape = RoundedCornerShape(size = 4.dp)
                )
                .border(
                    width = 1.dp,
                    color = KorailTalkTheme.colors.gray200,
                    shape = RoundedCornerShape(size = 4.dp)
                )
        ) { //상단 선택된 아이템
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(36.dp)
                    .clickable {
                        expanded = !expanded
                        onItemSelected(items.first())
                    }
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (expanded) items[0] else selectedItem,
                    color = KorailTalkTheme.colors.gray500,
                    style = KorailTalkTheme.typography.body.body2M15,

                    )
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown Arrow",
                    modifier = Modifier
                        .size(24.dp)
                        .rotate(rotateAngle)
                        .padding(1.dp),
                    tint = KorailTalkTheme.colors.gray500
                )
            }

            // 펼쳐졌을 때 나오는 리스트 영역
            if (expanded) {
                val itemsToShow = items.drop(1)

                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    item {
                        HorizontalDivider(
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp),
                            thickness = 1.dp,
                            color = KorailTalkTheme.colors.gray100
                        )
                    }
                    itemsIndexed(itemsToShow) { index, item ->
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
            .noRippleClickable(
                onClick = onItemClick
            )
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(36.dp)
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = item,
                color = KorailTalkTheme.colors.gray500,
                style = KorailTalkTheme.typography.body.body2M15,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis // 영역 벗어났을 때 처리
            )
        }
    }
}

@Composable
fun rememberTextMeasurer(): TextMeasurer {
    val fontFamilyResolver = LocalFontFamilyResolver.current
    val density = LocalDensity.current
    val layoutDirection = LocalLayoutDirection.current

    return remember(fontFamilyResolver, density, layoutDirection) {
        TextMeasurer(fontFamilyResolver, density, layoutDirection)
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
