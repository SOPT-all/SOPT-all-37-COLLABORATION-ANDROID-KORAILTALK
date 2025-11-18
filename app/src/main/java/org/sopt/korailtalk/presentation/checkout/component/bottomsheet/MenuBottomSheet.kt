package org.sopt.korailtalk.presentation.checkout.component.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.sopt.korailtalk.R
import org.sopt.korailtalk.core.common.util.extension.bottomBorder
import org.sopt.korailtalk.core.common.util.extension.noRippleClickable
import org.sopt.korailtalk.core.common.util.extension.pressedClickable
import org.sopt.korailtalk.core.common.util.extension.sideBorder
import org.sopt.korailtalk.core.common.util.extension.topBorder
import org.sopt.korailtalk.core.designsystem.component.bottomsheet.KorailTalkBasicBottomSheet
import org.sopt.korailtalk.core.designsystem.theme.KorailTalkTheme
import org.sopt.korailtalk.presentation.checkout.data.CouponData

enum class MenuBottomSheetType {
    Coupon, Person
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuBottomSheet(
    type: MenuBottomSheetType,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(),
    selectedCouponItem: CouponData? = null,
    selectedPersonItem: String? = null,
    couponList: List<CouponData> = emptyList(),
    personList: List<String> = emptyList(),
    onCouponClick: (CouponData) -> Unit = {},
    onPersonClick: (String) -> Unit = {},
) {
    val scope = rememberCoroutineScope()

    val menuList = when (type) {
        MenuBottomSheetType.Coupon -> couponList
        MenuBottomSheetType.Person -> personList
    }

    val titleText = when (type) {
        MenuBottomSheetType.Coupon -> "적용할 쿠폰 선택"
        MenuBottomSheetType.Person -> "적용할 승객 선택"
    }

    val titleViewBottomRadius = if(menuList.isEmpty()) 8.dp else 0.dp

    val dismissCallback: () -> Unit = { scope.launch { sheetState.hide() }
        .invokeOnCompletion{ onDismiss() } }

    KorailTalkBasicBottomSheet(
        sheetState = sheetState,
        onDismiss = dismissCallback,
        content = {
            LazyColumn(
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
            ) {
                item {
                    Spacer(Modifier.height(24.dp))

                    // title view
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .border(
                                width = 1.dp,
                                color = KorailTalkTheme.colors.gray200,
                                shape = RoundedCornerShape(
                                    topStart = 8.dp,
                                    topEnd = 8.dp,
                                    bottomStart = titleViewBottomRadius,
                                    bottomEnd = titleViewBottomRadius
                                )
                            )
                            .padding(horizontal = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = titleText,
                            style = KorailTalkTheme.typography.body.body1R16,
                            color = KorailTalkTheme.colors.gray400
                        )

                        Spacer(Modifier.weight(1f))

                        Icon(
                            painter = painterResource(id = R.drawable.ic_up),
                            contentDescription = null,
                            tint = KorailTalkTheme.colors.gray200,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                items(menuList.size) { index ->
                    MenuBottomSheetItem(
                        itemIndex = index,
                        itemCount = menuList.size,
                        itemType = type,
                        couponData = if(couponList.isNotEmpty()) couponList[index] else null,
                        personData = if(personList.isNotEmpty()) personList[index] else null,
                        selectedCouponItem = selectedCouponItem,
                        selectedPersonItem = selectedPersonItem,
                        onCouponClick = {
                            onCouponClick(it)
                            dismissCallback()
                        },
                        onPersonClick = {
                            onPersonClick(it)
                            dismissCallback()
                        }
                    )

                    if(index != menuList.size - 1) {
                        HorizontalDivider(thickness = 1.dp, color = KorailTalkTheme.colors.gray200)
                    }
                }

                item {
                    // 아무 아이템 없을시 Empty View
                    if(menuList.isEmpty()) {
                        val emptyTextTitle = when(type) {
                            MenuBottomSheetType.Coupon -> "적용 가능한 쿠폰이 없습니다."
                            MenuBottomSheetType.Person -> "적용 가능한 승객이 없습니다."
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = emptyTextTitle,
                                style = KorailTalkTheme.typography.body.body1R16,
                                color = KorailTalkTheme.colors.black,
                            )
                        }
                    }
                }
            }
        }
    )
}

@Composable
private fun MenuBottomSheetItem(
    itemIndex: Int,
    itemCount: Int,
    itemType: MenuBottomSheetType,
    couponData: CouponData? = null,
    personData: String? = null,
    selectedCouponItem: CouponData? = null,
    selectedPersonItem: String? = null,
    onCouponClick: (CouponData) -> Unit = {},
    onPersonClick: (String) -> Unit = {},
) {
    var isPressed by remember { mutableStateOf(false) }

    val itemViewBottomRadius = if(itemIndex + 1 == itemCount) 8.dp else 0.dp

    val itemTitle = when (itemType) {
        MenuBottomSheetType.Coupon -> couponData?.couponName ?: ""
        MenuBottomSheetType.Person -> personData ?: ""
    }

    val menuItem = when (itemType) {
        MenuBottomSheetType.Coupon -> couponData
        MenuBottomSheetType.Person -> personData
    }

    val selectedItem = when (itemType) {
        MenuBottomSheetType.Coupon -> selectedCouponItem
        MenuBottomSheetType.Person -> selectedPersonItem
    }

    val isEnabled = menuItem != selectedItem

    val borderModifier =
        if(itemIndex == itemCount - 1) Modifier.bottomBorder(2.dp, KorailTalkTheme.colors.gray200, 8.dp)
        else Modifier.sideBorder(2.dp, KorailTalkTheme.colors.gray200)


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .clip(
                shape = RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 0.dp,
                    bottomStart = itemViewBottomRadius,
                    bottomEnd = itemViewBottomRadius
                )
            )
            .background(
                color = if (isEnabled) {
                    if(isPressed) {
                        KorailTalkTheme.colors.primary200
                    } else {
                        KorailTalkTheme.colors.white
                    }
                } else {
                    KorailTalkTheme.colors.gray100
                }
            )
            .then(borderModifier)
            .padding(horizontal = 12.dp)
            .pressedClickable(
                changePressed = {
                    isPressed = it
                },
                onClick = {
                    if (isEnabled) {
                        when(itemType) {
                            MenuBottomSheetType.Coupon -> onCouponClick(menuItem as CouponData)
                            MenuBottomSheetType.Person -> onPersonClick(menuItem as String)
                        }
                    }
                }
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = itemTitle,
            style = KorailTalkTheme.typography.body.body1R16,
            color = if(isEnabled) KorailTalkTheme.colors.black
                    else KorailTalkTheme.colors.gray300
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun MenuBottomSheetPreview() {
    val sheetState = rememberModalBottomSheetState()
    var showSheet by remember { mutableStateOf(false) }

    val couponList = listOf(
        CouponData(
            couponName = "운임의 10% 할인",
            salePercentage = 10
        ),
        CouponData(
            couponName = "운임의 30% 할인",
            salePercentage = 30
        ),
    )

    val personList = listOf(
        "어른 - 1호차 12A / 48,800원",
        "어른 - 1호차 12B / 48,800원"
    )

    var selectedCouponItem: CouponData? = null
    var selectedPersonItem: String? = null


    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "BottomSheet 열기",
            modifier = Modifier
                .align(Alignment.Center)
                .noRippleClickable {
                    showSheet = true
                }
        )

        if (showSheet) {
            MenuBottomSheet(
                sheetState = sheetState,
                type = MenuBottomSheetType.Coupon,
                onDismiss = { showSheet = false },
                couponList = emptyList(),
                personList = personList,
                selectedCouponItem = selectedCouponItem,
                selectedPersonItem = selectedPersonItem,
                onCouponClick = {
                    selectedCouponItem = it
                },
                onPersonClick = {
                    selectedPersonItem = it
                }
            )
        }
    }
}