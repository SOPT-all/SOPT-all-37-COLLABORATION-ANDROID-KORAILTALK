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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.sopt.korailtalk.R
import org.sopt.korailtalk.core.common.util.extension.noRippleClickable
import org.sopt.korailtalk.core.common.util.preview.DefaultPreview
import org.sopt.korailtalk.presentation.checkout.component.dialog.ConfirmDialog
import org.sopt.korailtalk.core.designsystem.component.topappbar.BackTopAppBar
import org.sopt.korailtalk.core.designsystem.theme.KorailTalkTheme
import org.sopt.korailtalk.domain.model.DomainCouponData
import org.sopt.korailtalk.domain.model.DomainNationalVerify
import org.sopt.korailtalk.domain.model.DomainTrainInfo
import org.sopt.korailtalk.domain.type.SeatType
import org.sopt.korailtalk.domain.type.TrainType
import org.sopt.korailtalk.presentation.checkout.component.dialog.ReservationCancelDialog
import org.sopt.korailtalk.presentation.checkout.state.CheckoutUiState
import org.sopt.korailtalk.presentation.checkout.view.CheckoutBottomView
import org.sopt.korailtalk.presentation.checkout.view.CheckoutTopView
import java.text.NumberFormat
import java.util.Locale

@Composable
fun CheckoutRoute(
    paddingValues: PaddingValues,
    navigateToHome: () -> Unit,
    navigateUp: () -> Unit,
    seatType: SeatType = SeatType.NORMAL,
    trainId: Long = 1,
    normalSeatPrice: Int = 48000, // FIXME sample
    premiumSeatPrice: Int? = 20000, // FIXME sample
    viewModel: CheckoutViewModel = hiltViewModel()
) {
    val checkoutUiState by viewModel.checkoutUiState.collectAsStateWithLifecycle()
    // 다이얼로그용 상태
    var confirmDialogMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        viewModel.getTrainInfo(seatType, trainId)
    }

    // 사이드 이펙트 수집 (에러 발생 등)
    LaunchedEffect(Unit) {
        viewModel.checkoutSideEffect.collect { sideEffect ->
            when (sideEffect) {
                is CheckoutSideEffect.ShowDialog -> {
                    confirmDialogMessage = sideEffect.message
                }
                is CheckoutSideEffect.NavigateToHome -> {
                    navigateToHome()
                }
                else -> {}
            }
        }
    }

    when(checkoutUiState) {
        is CheckoutUiState.Success -> {
            val trainInfo = (checkoutUiState as CheckoutUiState.Success).domainTrainInfo

            CheckoutScreen(
                trainInfo = trainInfo,
                modifier = Modifier.padding(paddingValues),
                onBackClick = navigateUp,
                onCloseClick = navigateToHome,
                normalSeatPrice = normalSeatPrice,
                premiumSeatPrice = premiumSeatPrice,
                onNationalConfirmClick = viewModel::postVerifyNation,
                onCancelClick = viewModel::deleteReservation
            )
        }
        is CheckoutUiState.Failure -> {
            //TODO 에러처리
        }
        else -> {}
    }

    // 실제 다이얼로그 UI는 Composable 트리 안에
    confirmDialogMessage?.let { message ->
        ConfirmDialog(
            isVisible = true,
            message = message,
            onDismiss = { confirmDialogMessage = null }
        )
    }
}

@Composable
private fun CheckoutScreen(
    trainInfo: DomainTrainInfo,
    onBackClick: () -> Unit,
    onCloseClick: () -> Unit,
    onCancelClick: (Long) -> Unit,
    onNationalConfirmClick: (DomainNationalVerify) -> Unit,
    normalSeatPrice: Int = 0,
    premiumSeatPrice: Int? = null,
    modifier: Modifier = Modifier,
) {
    var selectedCoupon by remember { mutableStateOf<DomainCouponData?>(null) }
    var finalPrice by remember { mutableIntStateOf(trainInfo.price) }
    var couponSalePrice by remember { mutableIntStateOf(0) }

    var isCancelDialogVisible by remember { mutableStateOf(false) }
    var isCancelConfirmDialogVisible by remember { mutableStateOf(false) }

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
                    couponSalePrice = couponSalePrice,
                    normalSeatPrice = normalSeatPrice,
                    finalPrice = finalPrice,
                    selectedCoupon = selectedCoupon,
                    onSelectedCouponChange = ({
                        selectedCoupon = it
                    }),
                    premiumSeatPrice = premiumSeatPrice,
                    finalPriceCallback = { callbackPrice ->
                        finalPrice = callbackPrice
                    }
                ) // 상단 ~ 할인쿠폰 적용
            }
            item { // @nahy-512
                CheckoutBottomView(
                    price = trainInfo.price,
                    onNationalConfirmClick = onNationalConfirmClick,
                    finalPriceCallback = { callbackPrice ->
                        finalPrice = callbackPrice
                        couponSalePrice = trainInfo.price // 전체 운임 할인
                    }
                ) // 국가유공자 할인 ~ 하단
            }
        }

        FinalPriceInfo( // 총 결제 금액
            price = finalPrice
        )

        BottomFixedButtons( // 예약취소 및 다음 버튼
            onCancelClick = { isCancelDialogVisible = true },
            onNextClick = {}
        )
    }

    // 예약 취소 (아니오/예) 다이얼로그
    ReservationCancelDialog(
        isVisible = isCancelDialogVisible,
        onDismiss = { isCancelDialogVisible = false },
        onConfirm = {
            isCancelDialogVisible = false
            isCancelConfirmDialogVisible = true
            //TODO: 예약 취소 진행
        }
    )

    // 예약 취소 확인 다이얼로그
    ConfirmDialog(
        isVisible = isCancelConfirmDialogVisible,
        message = "예약이 취소되었습니다.",
        onDismiss = {
            isCancelConfirmDialogVisible = false
            onCancelClick(trainInfo.reservationId)
        }
    )
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
        origin = "서울",
        destination = "부산",
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
        onCloseClick = {},
        onNationalConfirmClick = {},
        onCancelClick = {}
    )
}