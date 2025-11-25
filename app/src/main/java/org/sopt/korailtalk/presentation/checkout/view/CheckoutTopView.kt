package org.sopt.korailtalk.presentation.checkout.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.korailtalk.R
import org.sopt.korailtalk.core.common.util.format.priceFormat
import org.sopt.korailtalk.core.designsystem.theme.KorailTalkTheme
import org.sopt.korailtalk.domain.model.DomainCouponData
import org.sopt.korailtalk.domain.model.DomainTrainInfo
import org.sopt.korailtalk.domain.type.SeatType
import org.sopt.korailtalk.domain.type.TrainType
import org.sopt.korailtalk.presentation.checkout.component.bottomsheet.MenuBottomSheet
import org.sopt.korailtalk.presentation.checkout.component.bottomsheet.MenuBottomSheetType
import org.sopt.korailtalk.presentation.checkout.component.row.CheckoutDropDownRow
import org.sopt.korailtalk.presentation.checkout.component.row.CheckoutSectionRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutTopView(
    trainInfo: DomainTrainInfo,
    discountFee: Int,
    modifier: Modifier = Modifier,
    viewEnteredTime: Long = System.currentTimeMillis(),
    finalPriceCallback: (Int) -> Unit = {},
) {
    val targetPayTime = viewEnteredTime + 600000

    var selectedCoupon: DomainCouponData? = null
    var selectedPerson: String? = null

    var showMenuBottomSheetForCoupon by remember { mutableStateOf(false) }
    var showMenuBottomSheetForPerson by remember { mutableStateOf(false) }

    var finalPrice by remember { mutableStateOf(trainInfo.price) }


    LaunchedEffect(selectedCoupon, selectedPerson) {
        if(selectedCoupon != null && selectedPerson != null) {
            finalPrice = trainInfo.price - trainInfo.price * (selectedCoupon!!.discountRate * 0.01).toInt()
            finalPriceCallback(finalPrice)
        }
    }



    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(KorailTalkTheme.colors.white)
            .padding(top = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 날짜
            Text(
                text = "2025년 12월 1일 (금)",
                style = KorailTalkTheme.typography.headline.head5R18,
                color = KorailTalkTheme.colors.black
            )

            Spacer(Modifier.height(12.dp))

            //열차 정보
            Text(
                text = "${trainInfo.type.name} ${trainInfo.trainNumber} · 1호차 12A",
                style = KorailTalkTheme.typography.body.body1R16,
                color = KorailTalkTheme.colors.black
            )

            Spacer(Modifier.height(20.dp))

            HorizontalDivider(thickness = 1.dp, color = KorailTalkTheme.colors.gray150)

            Spacer(Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                // 출발 장소 및 시간
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "서울",
                        style = KorailTalkTheme.typography.headline.head1M30,
                        color = KorailTalkTheme.colors.primary700
                    )

                    Spacer(Modifier.height(8.dp))

                    Text(
                        text = trainInfo.startAt,
                        style = KorailTalkTheme.typography.body.body1R16,
                        color = KorailTalkTheme.colors.black
                    )
                }

                Spacer(Modifier.width(44.dp))

                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_side),
                    contentDescription = null,
                    tint = Color.Unspecified
                )

                Spacer(Modifier.width(44.dp))

                // 도착 장소 및 시간
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "부산",
                        style = KorailTalkTheme.typography.headline.head1M30,
                        color = KorailTalkTheme.colors.primary700
                    )

                    Spacer(Modifier.height(8.dp))

                    Text(
                        text = trainInfo.arriveAt,
                        style = KorailTalkTheme.typography.body.body1R16,
                        color = KorailTalkTheme.colors.black
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            // 운임
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "운임",
                    style = KorailTalkTheme.typography.body.body1R16,
                    color = KorailTalkTheme.colors.gray400
                )

                Text(
                    text = "${trainInfo.price.priceFormat()} 원",
                    style = KorailTalkTheme.typography.body.body1R16,
                    color = KorailTalkTheme.colors.gray400
                )
            }

            Spacer(Modifier.height(12.dp))

            // 요금
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "요금",
                    style = KorailTalkTheme.typography.body.body1R16,
                    color = KorailTalkTheme.colors.gray400
                )

                Text(
                    text = "${(trainInfo.price - 48000).priceFormat()} 원",
                    style = KorailTalkTheme.typography.body.body1R16,
                    color = KorailTalkTheme.colors.gray400
                )
            }

            Spacer(Modifier.height(12.dp))

            // 운임 할인
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "운임할인",
                    style = KorailTalkTheme.typography.body.body1R16,
                    color = KorailTalkTheme.colors.gray400
                )

                Text(
                    text = "0 원",
                    style = KorailTalkTheme.typography.body.body1R16,
                    color = KorailTalkTheme.colors.gray400
                )
            }

            Spacer(Modifier.height(12.dp))

            // 요금 할인
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "요금할인",
                    style = KorailTalkTheme.typography.body.body1R16,
                    color = KorailTalkTheme.colors.gray400
                )

                Text(
                    text = "${discountFee.priceFormat()} 원",
                    style = KorailTalkTheme.typography.body.body1R16,
                    color = KorailTalkTheme.colors.gray400
                )
            }

            Spacer(Modifier.height(12.dp))

            // 결제 금액
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "결제금액",
                    style = KorailTalkTheme.typography.headline.head2M20,
                    color = KorailTalkTheme.colors.black
                )

                Text(
                    text = "${(trainInfo.price - discountFee).priceFormat()} 원",
                    style = KorailTalkTheme.typography.headline.head2M20,
                    color = KorailTalkTheme.colors.black
                )
            }

            Spacer(Modifier.height(12.dp))

            Text(
                text = "*특(우등)실은 운임과 요금으로 구성되며 운임만 할인됩니다.\n" +
                        "*${targetPayTime.toHHMM()}까지 결제하지 않으면 취소됩니다.",
                style = KorailTalkTheme.typography.body.body5R13,
                color = KorailTalkTheme.colors.pointRed,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))
        }

        Spacer(Modifier.height(20.dp))

        // 할인쿠폰 적용
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            CheckoutSectionRow(
                title = "할인쿠폰 적용"
            )

            Spacer(Modifier.height(16.dp))

            CheckoutDropDownRow(
                title = "할인 쿠폰",
                onClick = {
                    showMenuBottomSheetForCoupon = true
                },
                placeholder = "적용할 쿠폰 선택",
                selected = selectedCoupon?.name ?: "",
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(Modifier.height(16.dp))

            CheckoutDropDownRow(
                title = "적용 대상",
                onClick = {
                    showMenuBottomSheetForCoupon = true
                },
                placeholder = "적용할 승객 선택",
                selected = selectedPerson ?: "",
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(Modifier.height(16.dp))
        }
    }


    if(showMenuBottomSheetForCoupon) {
        MenuBottomSheet(
            type = MenuBottomSheetType.Coupon,
            couponList = trainInfo.coupons,
            selectedCouponItem = selectedCoupon,
            onCouponClick = {
                selectedCoupon = it
            },
            onDismiss = {
                showMenuBottomSheetForCoupon = false
            },
        )
    }

    if(showMenuBottomSheetForPerson) {
        MenuBottomSheet(
            type = MenuBottomSheetType.Person,
            personList = listOf("어른 - 1호차 12A / ${trainInfo.price}"),
            selectedPersonItem = selectedPerson,
            onPersonClick = {
                selectedPerson = it
            },
            onDismiss = {
                showMenuBottomSheetForPerson = false
            },
        )
    }
}

@SuppressLint("DefaultLocale")
fun Long.toHHMM(): String {
    val hours = (this / (1000 * 60 * 60)) % 24
    val minutes = (this / (1000 * 60)) % 60
    return String.format("%02d:%02d", hours, minutes)
}


@Preview
@Composable
private fun CheckoutTopViewPreview() {
    val trainInfo = DomainTrainInfo(
        startAt = "06:48",
        arriveAt = "10:09",
        type = TrainType.KTX,
        trainNumber = 404,
        price = 48000,
        reservationId = 1,
        seatType = SeatType.NORMAL,
        coupons = listOf(
            DomainCouponData(
                name = "coupon1",
                discountRate = 10
            ),
            DomainCouponData(
                name = "coupon2",
                discountRate = 20
            ),
        )
    )

    CheckoutTopView(
        trainInfo = trainInfo,
        discountFee = 0,
        viewEnteredTime = System.currentTimeMillis() + 32_400_000
    )
}