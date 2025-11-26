package org.sopt.korailtalk.presentation.reservation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.sopt.korailtalk.R
import org.sopt.korailtalk.core.common.util.extension.noRippleClickable
import org.sopt.korailtalk.core.common.util.preview.DefaultPreview
import org.sopt.korailtalk.core.designsystem.component.checkbox.KorailTalkBasicCheckBox
import org.sopt.korailtalk.core.designsystem.component.dropdown.KorailTalkDropdown
import org.sopt.korailtalk.core.designsystem.component.topappbar.BackTopAppBar
import org.sopt.korailtalk.core.designsystem.theme.KorailTalkTheme
import org.sopt.korailtalk.domain.model.DomainTrainItem
import org.sopt.korailtalk.domain.model.SeatInfo
import org.sopt.korailtalk.domain.type.SeatStatusType
import org.sopt.korailtalk.domain.type.SeatType
import org.sopt.korailtalk.domain.type.TrainType
import org.sopt.korailtalk.presentation.reservation.component.ReservationCard
import org.sopt.korailtalk.presentation.reservation.state.ReservationUiState
import org.sopt.korailtalk.presentation.reservation.viewmodel.ReservationViewModel

@Composable
fun ReservationRoute(
    paddingValues: PaddingValues,
    navigateToCheckout: () -> Unit,
    navigateUp: () -> Unit,
    viewModel: ReservationViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // 화면 진입 시 데이터 로딩 (임시로 서울-부산)
    LaunchedEffect(Unit) {
        viewModel.searchTrains(
            origin = "Seoul",
            destination = "Busan"
        )
    }

    ReservationScreen(
        uiState = uiState,
        modifier = Modifier.padding(paddingValues),
        onBackClick = navigateUp,
        onTrainItemClick = navigateToCheckout,
        onSearchTrains = { origin, destination, trainType, seatType, isBookAvailable ->
            viewModel.searchTrains(origin, destination, trainType, seatType, isBookAvailable)
        }
    )
}

@Composable
private fun ReservationScreen(
    uiState: ReservationUiState,
    onBackClick: () -> Unit,
    onTrainItemClick: () -> Unit,
    onSearchTrains: (String, String, String?, String?, Boolean?) -> Unit,
    modifier: Modifier = Modifier
) {
    // 필터 상태
    var selectedTrainType by remember { mutableStateOf<String?>(null) }
    var selectedSeatType by remember { mutableStateOf("전체") }
    var selectedRouteType by remember { mutableStateOf("직통") }
    var isBookAvailableOnly by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // TopAppBar
        BackTopAppBar(
            title = "열차 조회",
            onBackClick = onBackClick,
            actions = {
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_reload),
                    contentDescription = "새로고침",
                    modifier = Modifier
                        .size(44.dp)
                        .noRippleClickable {
                            // 현재 필터로 재검색
                            when (uiState) {
                                is ReservationUiState.Success -> {
                                    onSearchTrains(
                                        uiState.origin,
                                        uiState.destination,
                                        selectedTrainType,
                                        if (selectedSeatType == "전체") null else selectedSeatType,
                                        isBookAvailableOnly
                                    )
                                }
                                else -> {}
                            }
                        }
                )
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_hamburger),
                    contentDescription = "메뉴",
                    modifier = Modifier.size(44.dp)
                )
            }
        )

        // UI 상태에 따른 화면 표시
        when (val state = uiState) {
            is ReservationUiState.Initial -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "출발지와 도착지를 선택해주세요",
                        style = KorailTalkTheme.typography.body.body2M15,
                        color = KorailTalkTheme.colors.gray400
                    )
                }
            }

            is ReservationUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = KorailTalkTheme.colors.primary700)
                }
            }

            is ReservationUiState.Success -> {
                ReservationContent(
                    origin = state.origin,
                    destination = state.destination,
                    totalTrains = state.totalTrains,
                    trains = state.trains,
                    selectedTrainType = selectedTrainType,
                    onTrainTypeSelected = { type ->
                        selectedTrainType = if (selectedTrainType == type) null else type
                        onSearchTrains(
                            state.origin,
                            state.destination,
                            selectedTrainType,
                            if (selectedSeatType == "전체") null else selectedSeatType,
                            isBookAvailableOnly
                        )
                    },
                    selectedSeatType = selectedSeatType,
                    onSeatTypeSelected = { seat ->
                        selectedSeatType = seat
                        onSearchTrains(
                            state.origin,
                            state.destination,
                            selectedTrainType,
                            if (selectedSeatType == "전체") null else selectedSeatType,
                            isBookAvailableOnly
                        )
                    },
                    selectedRouteType = selectedRouteType,
                    onRouteTypeSelected = { route ->
                        selectedRouteType = route
                    },
                    isBookAvailableOnly = isBookAvailableOnly,
                    onBookAvailableChanged = { checked ->
                        isBookAvailableOnly = checked
                        onSearchTrains(
                            state.origin,
                            state.destination,
                            selectedTrainType,
                            if (selectedSeatType == "전체") null else selectedSeatType,
                            isBookAvailableOnly
                        )
                    },
                    onTrainItemClick = onTrainItemClick
                )
            }

            is ReservationUiState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            "오류가 발생했습니다",
                            style = KorailTalkTheme.typography.body.body2M15,
                            color = KorailTalkTheme.colors.pointRed
                        )
                        Text(
                            state.message,
                            style = KorailTalkTheme.typography.body.body2M15,
                            color = KorailTalkTheme.colors.gray400
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ReservationContent(
    origin: String,
    destination: String,
    totalTrains: Int,
    trains: List<DomainTrainItem>,
    selectedTrainType: String?,
    onTrainTypeSelected: (String) -> Unit,
    selectedSeatType: String,
    onSeatTypeSelected: (String) -> Unit,
    selectedRouteType: String,
    onRouteTypeSelected: (String) -> Unit,
    isBookAvailableOnly: Boolean,
    onBookAvailableChanged: (Boolean) -> Unit,
    onTrainItemClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(KorailTalkTheme.colors.gray50)
    ) {
        // 출발지/도착지 정보
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(KorailTalkTheme.colors.white)
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = origin,
                    style = KorailTalkTheme.typography.headline.head2M20,
                    color = KorailTalkTheme.colors.black
                )
                Text(
                    text = "→",
                    style = KorailTalkTheme.typography.headline.head2M20,
                    color = KorailTalkTheme.colors.gray300
                )
                Text(
                    text = destination,
                    style = KorailTalkTheme.typography.headline.head2M20,
                    color = KorailTalkTheme.colors.black
                )
            }
            Text(
                text = "11월 10일 · 편도 · 어른 1명", // TODO: 실제 데이터로 변경
                style = KorailTalkTheme.typography.cap.cap1M12,
                color = KorailTalkTheme.colors.gray400
            )
        }

        // 날짜 선택 + 예약가능 체크박스
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(KorailTalkTheme.colors.white)
                .padding(horizontal = 20.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_reservation_arrow),
                    contentDescription = "이전 날짜",
                    modifier = Modifier
                        .size(24.dp)
                        .then(Modifier.rotate(180f))
                )
                Text(
                    text = "가는 날 11월 10일 (화)", // TODO: 실제 날짜로 변경
                    style = KorailTalkTheme.typography.body.body2M15,
                    color = KorailTalkTheme.colors.black
                )
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_reservation_arrow),
                    contentDescription = "다음 날짜",
                    modifier = Modifier
                        .size(24.dp)
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                KorailTalkBasicCheckBox(
                    checked = isBookAvailableOnly,
                    onCheckedChange = onBookAvailableChanged
                )
                Text(
                    text = "예약가능",
                    style = KorailTalkTheme.typography.body.body2M15,
                    color = KorailTalkTheme.colors.gray500
                )
            }
        }

        // 열차 종류 필터
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(KorailTalkTheme.colors.white)
                .padding(horizontal = 20.dp)
                .padding(bottom = 16.dp)
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val trainTypes = listOf("전체", "KTX", "SRT", "무궁화", "ITX-마음/새마을")
            trainTypes.forEach { type ->
                val isSelected = if (type == "전체") selectedTrainType == null else selectedTrainType == type

                FilterChip(
                    selected = isSelected,
                    onClick = {
                        onTrainTypeSelected(if (type == "전체") "" else type)
                    },
                    label = {
                        Text(
                            text = type,
                            style = KorailTalkTheme.typography.body.body2M15
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = KorailTalkTheme.colors.black,
                        selectedLabelColor = KorailTalkTheme.colors.white,
                        containerColor = KorailTalkTheme.colors.white,
                        labelColor = KorailTalkTheme.colors.gray500
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        enabled = true,
                        selected = isSelected,
                        borderColor = KorailTalkTheme.colors.gray200,
                        selectedBorderColor = KorailTalkTheme.colors.black
                    )
                )
            }
        }

        // 드롭다운 필터 + 결과 개수
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(KorailTalkTheme.colors.white)
                .padding(horizontal = 20.dp)
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                KorailTalkDropdown(
                    items = listOf("전체", "일반실", "특실"),
                    selectedItem = selectedSeatType,
                    onItemSelected = onSeatTypeSelected
                )
                KorailTalkDropdown(
                    items = listOf("직통", "환승"),
                    selectedItem = selectedRouteType,
                    onItemSelected = onRouteTypeSelected
                )
            }

            Text(
                text = "결과 ($totalTrains)",
                style = KorailTalkTheme.typography.cap.cap1M12,
                color = KorailTalkTheme.colors.gray500
            )
        }

        // 열차 목록
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(trains) { train ->
                ReservationCard(
                    trainItem = train,
                    modifier = Modifier.noRippleClickable {
                        onTrainItemClick()
                    }
                )
            }
        }
    }
}

@DefaultPreview
@Composable
private fun ReservationScreenPreview() {
    ReservationScreen(
        uiState = ReservationUiState.Success(
            trains = listOf(
                DomainTrainItem(
                    type = TrainType.KTX,
                    trainNumber = "001",
                    departureTime = "05:13",
                    arrivalTime = "07:50",
                    durationMinutes = 157,
                    normalSeat = SeatInfo(SeatType.NORMAL, SeatStatusType.AVAILABLE, 59000),
                    premiumSeat = SeatInfo(SeatType.PREMIUM, SeatStatusType.ALMOST_SOLD_OUT, 83000)
                ),
                DomainTrainItem(
                    type = TrainType.SRT,
                    trainNumber = "182",
                    departureTime = "05:30",
                    arrivalTime = "08:20",
                    durationMinutes = 170,
                    normalSeat = SeatInfo(SeatType.NORMAL, SeatStatusType.AVAILABLE, 59000),
                    premiumSeat = SeatInfo(SeatType.PREMIUM, SeatStatusType.AVAILABLE, 83000)
                )
            ),
            origin = "서울",
            destination = "부산",
            totalTrains = 24
        ),
        onBackClick = {},
        onTrainItemClick = {},
        onSearchTrains = { _, _, _, _, _ -> }
    )
}