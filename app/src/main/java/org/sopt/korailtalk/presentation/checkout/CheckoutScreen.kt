package org.sopt.korailtalk.presentation.checkout

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import org.sopt.korailtalk.R
import org.sopt.korailtalk.core.common.util.extension.noRippleClickable
import org.sopt.korailtalk.core.common.util.preview.DefaultPreview
import org.sopt.korailtalk.core.designsystem.component.topappbar.BackTopAppBar
import org.sopt.korailtalk.domain.model.DomainCouponData
import org.sopt.korailtalk.domain.model.DomainTrainInfo
import org.sopt.korailtalk.domain.type.SeatType
import org.sopt.korailtalk.domain.type.TrainType
import org.sopt.korailtalk.presentation.checkout.view.CheckoutBottomView
import org.sopt.korailtalk.presentation.checkout.view.CheckoutTopView

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
        modifier = modifier.fillMaxSize()
    ) {
        Row( //TODO: TopAppBar로 수정 (@kimjw2003)
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            BackTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                onBackClick = {},
                title = "결제",
                actions = {
                    Image(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_cancel),
                        contentDescription = null,
                        modifier = Modifier.size(44.dp)
                    )
                }
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

        // TODO: 하단 Fixed 영역 작업 (@nahy-512)
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