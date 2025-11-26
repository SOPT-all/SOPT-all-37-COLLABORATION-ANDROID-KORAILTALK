package org.sopt.korailtalk.presentation.reservation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.sopt.korailtalk.core.common.util.extension.noRippleClickable
import org.sopt.korailtalk.core.designsystem.component.bottomsheet.KorailTalkBasicBottomSheet
import org.sopt.korailtalk.core.designsystem.theme.KorailTalkTheme
import org.sopt.korailtalk.domain.model.DomainTrainItem
import org.sopt.korailtalk.domain.model.SeatInfo
import org.sopt.korailtalk.domain.type.SeatStatusType
import org.sopt.korailtalk.domain.type.SeatType
import org.sopt.korailtalk.domain.type.TrainType

/**
 * 열차 예약 바텀시트
 * @param trainItem 선택된 열차 정보
 * @param onDismiss 닫기 콜백
 * @param onConfirm 예매하기 콜백 (좌석 타입 전달)
 * @param onSeatSelection 좌석선택 버튼 클릭 콜백
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationBottomSheet(
    modifier: Modifier = Modifier,
    trainItem: DomainTrainItem,
    onDismiss: () -> Unit,
    onConfirm: (SeatType) -> Unit,
    onSeatSelection: () -> Unit = {},
    sheetState: SheetState = rememberModalBottomSheetState()
) {
    var selectedSeatType by remember { mutableStateOf<SeatType?>(null) }

    KorailTalkBasicBottomSheet(
        sheetState = sheetState,
        onDismiss = onDismiss,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // 시간 표시
            Text(
                text = "${trainItem.departureTime} - ${trainItem.arrivalTime}",
                style = KorailTalkTheme.typography.headline.head2M20,
                color = KorailTalkTheme.colors.primary400
            )

            // 열차 종류 + 번호
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TrainTypeLabel(
                    trainType = trainItem.type,
                    isEnabled = true
                )
                Text(
                    text = trainItem.trainNumber,
                    style = KorailTalkTheme.typography.body.body4M14,
                    color = KorailTalkTheme.colors.black
                )
            }

            Spacer(modifier = Modifier.height(0.dp))

            // 일반실 선택
            SeatSelectionItem(
                seatType = SeatType.NORMAL,
                price = trainItem.normalSeat.price,
                isSelected = selectedSeatType == SeatType.NORMAL,
                isEnabled = trainItem.normalSeat.status != SeatStatusType.SOLD_OUT,
                onClick = {
                    selectedSeatType = SeatType.NORMAL
                }
            )

            // 특실 선택 (있는 경우에만)
            trainItem.premiumSeat?.let { premium ->
                SeatSelectionItem(
                    seatType = SeatType.PREMIUM,
                    price = premium.price,
                    isSelected = selectedSeatType == SeatType.PREMIUM,
                    isEnabled = premium.status != SeatStatusType.SOLD_OUT,
                    onClick = {
                        selectedSeatType = SeatType.PREMIUM
                    }
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            // 하단 버튼
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // 좌석선택 버튼
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .background(
                            color = KorailTalkTheme.colors.white,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = KorailTalkTheme.colors.gray200,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .noRippleClickable(onClick = onSeatSelection),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "좌석선택",
                        style = KorailTalkTheme.typography.headline.head4M18,
                        color = KorailTalkTheme.colors.black
                    )
                }

                // 예매하기 버튼
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .background(
                            color = if (selectedSeatType != null) {
                                KorailTalkTheme.colors.primary700
                            } else {
                                KorailTalkTheme.colors.gray200
                            },
                            shape = RoundedCornerShape(8.dp)
                        )
                        .noRippleClickable(
                            enabled = selectedSeatType != null,
                            onClick = {
                                selectedSeatType?.let { seatType ->
                                    onConfirm(seatType)
                                }
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "예매하기",
                        style = KorailTalkTheme.typography.headline.head4M18,
                        color = if (selectedSeatType != null) {
                            KorailTalkTheme.colors.white
                        } else {
                            KorailTalkTheme.colors.gray300
                        }
                    )
                }
            }
        } // Column 닫기
    } // KorailTalkBasicBottomSheet 닫기
} // ReservationBottomSheet 함수 닫기

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun ReservationBottomSheetPreview() {
    val sheetState = rememberModalBottomSheetState()
    var showSheet by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()

    if (showSheet) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ReservationBottomSheet(
                trainItem = DomainTrainItem(
                    type = TrainType.KTX,
                    trainNumber = "001",
                    departureTime = "05:13",
                    arrivalTime = "07:50",
                    durationMinutes = 157,
                    normalSeat = SeatInfo(
                        type = SeatType.NORMAL,
                        status = SeatStatusType.AVAILABLE,
                        price = 48800
                    ),
                    premiumSeat = SeatInfo(
                        type = SeatType.PREMIUM,
                        status = SeatStatusType.AVAILABLE,
                        price = 83700
                    )
                ),
                sheetState = sheetState,
                onDismiss = {
                    scope.launch { sheetState.hide() }
                        .invokeOnCompletion { showSheet = false }
                },
                onConfirm = { seatType ->
                    // 예매하기 처리
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, name = "특실 매진 케이스")
@Composable
private fun ReservationBottomSheetPremiumSoldOutPreview() {
    val sheetState = rememberModalBottomSheetState()
    var showSheet by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()

    if (showSheet) {
        ReservationBottomSheet(
            trainItem = DomainTrainItem(
                type = TrainType.KTX,
                trainNumber = "001",
                departureTime = "05:13",
                arrivalTime = "07:50",
                durationMinutes = 157,
                normalSeat = SeatInfo(
                    type = SeatType.NORMAL,
                    status = SeatStatusType.AVAILABLE,
                    price = 48800
                ),
                premiumSeat = SeatInfo(
                    type = SeatType.PREMIUM,
                    status = SeatStatusType.SOLD_OUT,
                    price = 83700
                )
            ),
            sheetState = sheetState,
            onDismiss = {
                scope.launch { sheetState.hide() }
                    .invokeOnCompletion { showSheet = false }
            },
            onConfirm = { seatType ->
                // 예매하기 처리
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, name = "일반실만 있는 케이스")
@Composable
private fun ReservationBottomSheetNormalOnlyPreview() {
    val sheetState = rememberModalBottomSheetState()
    var showSheet by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()

    if (showSheet) {
        ReservationBottomSheet(
            trainItem = DomainTrainItem(
                type = TrainType.KTX,
                trainNumber = "001",
                departureTime = "05:13",
                arrivalTime = "07:50",
                durationMinutes = 157,
                normalSeat = SeatInfo(
                    type = SeatType.NORMAL,
                    status = SeatStatusType.AVAILABLE,
                    price = 48800
                ),
                premiumSeat = null
            ),
            sheetState = sheetState,
            onDismiss = {
                scope.launch { sheetState.hide() }
                    .invokeOnCompletion { showSheet = false }
            },
            onConfirm = { seatType ->
                // 예매하기 처리
            }
        )
    }
}