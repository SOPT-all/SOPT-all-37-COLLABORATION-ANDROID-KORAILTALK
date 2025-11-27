package org.sopt.korailtalk.presentation.reservation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import org.sopt.korailtalk.R
import org.sopt.korailtalk.core.common.util.extension.noRippleClickable
import org.sopt.korailtalk.core.designsystem.component.checkbox.KorailTalkBasicCheckBox
import org.sopt.korailtalk.core.designsystem.component.dropdown.KorailTalkDropdown
import org.sopt.korailtalk.core.designsystem.component.topappbar.BackTopAppBar
import org.sopt.korailtalk.core.designsystem.theme.KorailTalkTheme
import org.sopt.korailtalk.domain.model.DomainTrainItem
import org.sopt.korailtalk.domain.model.SeatInfo
import org.sopt.korailtalk.domain.type.SeatStatusType
import org.sopt.korailtalk.domain.type.SeatType
import org.sopt.korailtalk.domain.type.TrainType
import org.sopt.korailtalk.presentation.reservation.component.ReservationBottomSheet
import org.sopt.korailtalk.presentation.reservation.component.ReservationCard
import org.sopt.korailtalk.presentation.reservation.state.ReservationUiState
import org.sopt.korailtalk.presentation.reservation.viewmodel.ReservationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationRoute(
    paddingValues: PaddingValues,
    origin: String,
    destination: String,
    navigateToCheckout: (String, String, Int, Int?) -> Unit,
    navigateUp: () -> Unit,
    viewModel: ReservationViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val bottomSheetState by viewModel.bottomSheetState.collectAsStateWithLifecycle()
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(origin, destination) {
        viewModel.searchTrains(
            origin = origin,
            destination = destination,
            trainType = TrainType.KTX
            //trainType = null  // null로 설정하면 KTX, SRT, ITX-새마을 병합 조회
        )
    }

// 바텀시트 표시
    bottomSheetState.selectedTrain?.let { train ->
        if (bottomSheetState.isVisible) {
            ReservationBottomSheet(
                trainItem = train,
                sheetState = sheetState,
                onDismiss = {
                    scope.launch {
                        sheetState.hide()
                    }.invokeOnCompletion {
                        viewModel.hideBottomSheet()
                    }
                },
                onConfirm = { seatType ->
                    scope.launch {
                        sheetState.hide()
                    }.invokeOnCompletion {
                        viewModel.hideBottomSheet()
                        navigateToCheckout(
                            seatType.name,
                            train.trainNumber,
                            train.normalSeat.price,
                            train.premiumSeat?.price
                        )
                    }
                }
            )
        }
    }

    ReservationScreen(
        uiState = uiState,
        modifier = Modifier.padding(paddingValues),
        onBackClick = navigateUp,
        onTrainItemClick = { train ->
            viewModel.showBottomSheet(train)
        },
        onRefresh = {
            viewModel.refresh()
        },
        onFilterChange = { trainType, seatType, isBookAvailable ->
            viewModel.applyClientSideFilter(trainType, seatType, isBookAvailable)
        },
        onLoadMore = {
            viewModel.loadMoreTrains()
        }
    )
}

@Composable
private fun ReservationScreen(
    uiState: ReservationUiState,
    onBackClick: () -> Unit,
    onTrainItemClick: (DomainTrainItem) -> Unit,
    onRefresh: () -> Unit,
    onFilterChange: (String?, String, Boolean) -> Unit,
    onLoadMore: () -> Unit,
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
                            onRefresh()
                        }
                )
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_hamburger),
                    contentDescription = "메뉴",
                    modifier = Modifier.size(44.dp)
                    // 메뉴 버튼은 아이콘 UI만 구현, 기능 구현하지 않음
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
                    date = state.date,
                    dayOfWeek = state.dayOfWeek,
                    passengerCount = state.passengerCount,
                    totalTrains = state.filteredTrains.size,
                    trains = state.filteredTrains,
                    hasMoreData = state.nextCursor != null,
                    selectedTrainType = selectedTrainType,
                    onTrainTypeSelected = { type ->
                        selectedTrainType = if (type == "전체") null else type
                        onFilterChange(selectedTrainType, selectedSeatType, isBookAvailableOnly)
                    },
                    selectedSeatType = selectedSeatType,
                    onSeatTypeSelected = { seat ->
                        selectedSeatType = seat
                        onFilterChange(selectedTrainType, selectedSeatType, isBookAvailableOnly)
                    },
                    selectedRouteType = selectedRouteType,
                    onRouteTypeSelected = { route ->
                        selectedRouteType = route
                        // 환승은 구현하지 않음
                    },
                    isBookAvailableOnly = isBookAvailableOnly,
                    onBookAvailableChanged = { checked ->
                        isBookAvailableOnly = checked
                        onFilterChange(selectedTrainType, selectedSeatType, isBookAvailableOnly)
                    },
                    onTrainItemClick = onTrainItemClick,
                    onLoadMore = onLoadMore
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
    date: String,
    dayOfWeek: String,
    passengerCount: Int,
    totalTrains: Int,
    trains: List<DomainTrainItem>,
    hasMoreData: Boolean,
    selectedTrainType: String?,
    onTrainTypeSelected: (String) -> Unit,
    selectedSeatType: String,
    onSeatTypeSelected: (String) -> Unit,
    selectedRouteType: String,
    onRouteTypeSelected: (String) -> Unit,
    isBookAvailableOnly: Boolean,
    onBookAvailableChanged: (Boolean) -> Unit,
    onTrainItemClick: (DomainTrainItem) -> Unit,
    onLoadMore: () -> Unit
) {
    val listState = rememberLazyListState()

    // 무한 스크롤 감지
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisibleIndex ->
                if (lastVisibleIndex != null &&
                    lastVisibleIndex >= trains.size - 3 &&
                    hasMoreData) {
                    onLoadMore()
                }
            }
    }

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
                .padding(horizontal = 16.dp, vertical = 16.dp),
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
                text = "$date · 편도 · 어른 ${passengerCount}명",
                style = KorailTalkTheme.typography.cap.cap1M12,
                color = KorailTalkTheme.colors.gray400
            )
        }

        // 날짜 선택 + 예약가능 체크박스
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(KorailTalkTheme.colors.white)
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // 좌우 화살표는 클릭 불가 (구현하지 않음)
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_reservation_arrow),
                    contentDescription = "이전 날짜",
                    modifier = Modifier
                        .size(24.dp)
                        .then(Modifier.rotate(180f))
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(11.dp)
                ) {
                    Text(
                        text = "가는 날",
                        style = KorailTalkTheme.typography.body.body3R15,
                        color = KorailTalkTheme.colors.gray400
                    )
                    Text(
                        text = "$date ($dayOfWeek)",
                        style = KorailTalkTheme.typography.body.body4M14,
                        color = KorailTalkTheme.colors.black
                    )
                }
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
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val trainTypes = buildList {
                add("전체")
                addAll(TrainType.entries.map { it.displayName })
            }

            trainTypes.forEach { type ->
                val isSelected = if (type == "전체") {
                    selectedTrainType == null
                } else {
                    selectedTrainType == type
                }

                FilterChip(
                    selected = isSelected,
                    onClick = {
                        onTrainTypeSelected(type)
                    },
                    label = {
                        Text(
                            text = type,
                            style = KorailTalkTheme.typography.body.body2M15,
                            modifier = Modifier.padding(vertical = 8.dp)
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
                    ),
                )
            }
        }

        // 드롭다운 필터 + 결과 개수
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(vertical = 16.dp),
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
                // 환승 드롭다운은 클릭 불가 (구현하지 않음)
                KorailTalkDropdown(
                    items = listOf("직통", "환승"),
                    selectedItem = selectedRouteType,
                    onItemSelected = { /* 환승 구현 안함 */ }
                )
            }

            Text(
                text = "결과 ($totalTrains)",
                style = KorailTalkTheme.typography.cap.cap1M12,
                color = KorailTalkTheme.colors.gray500
            )
        }

        // 열차 목록
        if (trains.isEmpty()) {
            // 조회 결과 없음
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        "예약 가능한 기차가 없어요",
                        style = KorailTalkTheme.typography.body.body2M15,
                        color = KorailTalkTheme.colors.gray400
                    )
                }
            }
        } else {
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(trains, key = { it.trainNumber + it.departureTime }) { train ->
                    val isDisabled = train.normalSeat.status == SeatStatusType.SOLD_OUT &&
                            (train.premiumSeat == null || train.premiumSeat.status == SeatStatusType.SOLD_OUT)

                    ReservationCard(
                        trainItem = train,
                        modifier = Modifier.noRippleClickable(
                            enabled = !isDisabled && !isBookAvailableOnly || !isDisabled
                        ) {
                            if (!isDisabled) {
                                onTrainItemClick(train)
                            }
                        }
                    )
                }

                // 로딩 인디케이터 (무한 스크롤)
                if (hasMoreData) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                color = KorailTalkTheme.colors.primary700,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
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
                ),
                DomainTrainItem(
                    type = TrainType.MUGUNGHWA,
                    trainNumber = "456",
                    departureTime = "06:00",
                    arrivalTime = "10:30",
                    durationMinutes = 270,
                    normalSeat = SeatInfo(SeatType.NORMAL, SeatStatusType.SOLD_OUT, 35000),
                    premiumSeat = null
                )
            ),
            origin = "서울",
            destination = "부산",
            totalTrains = 24,
            filteredTrains = listOf(
                DomainTrainItem(
                    type = TrainType.KTX,
                    trainNumber = "001",
                    departureTime = "05:13",
                    arrivalTime = "07:50",
                    durationMinutes = 157,
                    normalSeat = SeatInfo(SeatType.NORMAL, SeatStatusType.AVAILABLE, 59000),
                    premiumSeat = SeatInfo(SeatType.PREMIUM, SeatStatusType.ALMOST_SOLD_OUT, 83000)
                )
            )
        ),
        onBackClick = {},
        onTrainItemClick = { },
        onRefresh = {},
        onFilterChange = { _, _, _ -> },
        onLoadMore = {}
    )
}

@Preview
@Composable
private fun ReservationScreenEmptyPreview() {
    ReservationScreen(
        uiState = ReservationUiState.Success(
            trains = emptyList(),
            origin = "서울",
            destination = "부산",
            totalTrains = 0,
            filteredTrains = emptyList()
        ),
        onBackClick = {},
        onTrainItemClick = { },
        onRefresh = {},
        onFilterChange = { _, _, _ -> },
        onLoadMore = {}
    )
}
