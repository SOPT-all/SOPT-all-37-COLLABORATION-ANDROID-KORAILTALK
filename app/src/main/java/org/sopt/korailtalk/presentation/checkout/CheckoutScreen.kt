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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.sopt.korailtalk.R
import org.sopt.korailtalk.core.common.state.UiState
import org.sopt.korailtalk.core.common.util.extension.noRippleClickable
import org.sopt.korailtalk.core.common.util.preview.DefaultPreview
import org.sopt.korailtalk.core.designsystem.component.topappbar.BackTopAppBar
import org.sopt.korailtalk.domain.model.DomainCouponData
import org.sopt.korailtalk.domain.model.DomainTrainInfo
import org.sopt.korailtalk.domain.type.SeatType
import org.sopt.korailtalk.domain.type.TrainType
import org.sopt.korailtalk.presentation.checkout.state.CheckoutUiState
import org.sopt.korailtalk.presentation.checkout.view.CheckoutBottomView
import org.sopt.korailtalk.presentation.checkout.view.CheckoutTopView

@Composable
fun CheckoutRoute(
    paddingValues: PaddingValues,
    navigateToHome: () -> Unit,
    navigateUp: () -> Unit,
    seatType: SeatType = SeatType.NORMAL,
    trainId: Long = 0,
    viewModel: CheckoutViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.getTrainInfo(seatType, trainId)
    }

    val checkoutUiState by viewModel.checkoutUiState.collectAsStateWithLifecycle()

    when(checkoutUiState.loadState) {
        is UiState.Success -> {
            val trainInfo = (checkoutUiState.trainInfoLoadState as UiState.Success).data

            CheckoutScreen(
                trainInfo = trainInfo,
                modifier = Modifier.padding(paddingValues),
                onBackClick = navigateUp,
                onCloseClick = navigateToHome
            )
        }
        is UiState.Failure -> {
            //TODO 에러처리
        }
        else -> {}
    }
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