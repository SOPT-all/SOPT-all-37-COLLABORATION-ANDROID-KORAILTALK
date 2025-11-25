package org.sopt.korailtalk.presentation.checkout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.sopt.korailtalk.R
import org.sopt.korailtalk.core.common.util.extension.noRippleClickable
import org.sopt.korailtalk.core.common.util.preview.DefaultPreview
import org.sopt.korailtalk.core.designsystem.component.topappbar.BackTopAppBar
import org.sopt.korailtalk.core.designsystem.theme.KorailTalkTheme
import org.sopt.korailtalk.domain.model.DomainCouponData
import org.sopt.korailtalk.domain.model.DomainTrainInfo
import org.sopt.korailtalk.domain.type.SeatType
import org.sopt.korailtalk.domain.type.TrainType
import org.sopt.korailtalk.presentation.checkout.view.CheckoutBottomView
import org.sopt.korailtalk.presentation.checkout.view.CheckoutTopView
import java.text.NumberFormat
import java.util.Locale

@Composable
fun CheckoutRoute(
    paddingValues: PaddingValues,
    navigateToHome: () -> Unit,
    navigateUp: () -> Unit
) {
    val trainInfo = DomainTrainInfo(
        startAt = "",
        arriveAt = "",
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

    CheckoutScreen(
        trainInfo = trainInfo,
        modifier = Modifier.padding(paddingValues),
        onBackClick = navigateUp,
        onCloseClick = navigateToHome
    )
}

@Composable
private fun CheckoutScreen(
    trainInfo: DomainTrainInfo,
    onBackClick: () -> Unit,
    onCloseClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(KorailTalkTheme.colors.white)
            .fillMaxSize()
    ) {
        Row( //TODO: TopAppBar로 수정 (@kimjw2003)
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            BackTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                onBackClick = {
                    onBackClick()
                },
                title = "결제",
                actions = {
                    Image(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_cancel),
                        contentDescription = null,
                        modifier = Modifier
                            .size(44.dp)
                            .noRippleClickable {
                                onCloseClick()
                            }
                    )
                },
            )
        }

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            item { // @kimjw2003
                CheckoutTopView(
                    trainInfo = trainInfo,
                    discountFee = 0,
                    finalPriceCallback = { finalPrice ->
                        //TODO 할인쿠폰 적용한 결과
                    }
                ) // 상단 ~ 할인쿠폰 적용
            }
            item { // @nahy-512
                CheckoutBottomView() // 국가유공자 할인 ~ 하단
            }
        }

        FinalPriceInfo( // 총 결제 금액
            price = 48800
        )

        BottomFixedButtons( // 예약취소 및 다음 버튼
            onCancelClick = {},
            onNextClick = {}
        )
    }
}

@Composable
private fun FinalPriceInfo(
    price: Int,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .background(KorailTalkTheme.colors.primary700)
            .fillMaxWidth()
            .height(84.dp)
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "총 결제 금액",
            style = KorailTalkTheme.typography.body.body2M15,
            color = KorailTalkTheme.colors.white
        )
        Text(
            text = "${NumberFormat.getInstance(Locale.KOREA).format(price)}원",
            style = KorailTalkTheme.typography.headline.head2M20,
            color = KorailTalkTheme.colors.white
        )
    }
}

@Composable
private fun BottomFixedButtons(
    onCancelClick: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(KorailTalkTheme.colors.primary200)
            .fillMaxWidth()
            .height(56.dp)
    ) {
        Text(
            text = "예약취소",
            style = KorailTalkTheme.typography.body.body1R16,
            color = KorailTalkTheme.colors.primary700,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .noRippleClickable(onClick = onCancelClick)
                .weight(1f)
                .fillMaxHeight()
                .wrapContentHeight(Alignment.CenterVertically)
        )
        Text(
            text = "다음",
            style = KorailTalkTheme.typography.body.body1R16,
            color = KorailTalkTheme.colors.primary700,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .noRippleClickable(onClick = onNextClick)
                .weight(1f)
                .fillMaxHeight()
                .wrapContentHeight(Alignment.CenterVertically)
        )
    }
}

@DefaultPreview
@Composable
private fun CheckoutScreenPreview() {
    val trainInfo = DomainTrainInfo(
        startAt = "",
        arriveAt = "",
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

    CheckoutScreen(
        trainInfo = trainInfo,
        onBackClick = {},
        onCloseClick = {}
    )
}