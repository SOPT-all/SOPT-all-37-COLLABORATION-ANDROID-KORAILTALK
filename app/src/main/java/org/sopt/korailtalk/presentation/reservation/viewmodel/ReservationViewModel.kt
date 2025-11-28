package org.sopt.korailtalk.presentation.reservation.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.sopt.korailtalk.core.common.state.UiState
import org.sopt.korailtalk.core.navigation.Route
import org.sopt.korailtalk.domain.model.DomainTrainItem
import org.sopt.korailtalk.domain.repository.KorailTalkRepository
import org.sopt.korailtalk.domain.type.TrainFilterType
import org.sopt.korailtalk.domain.type.SeatStatusType
import org.sopt.korailtalk.domain.type.SeatType
import org.sopt.korailtalk.presentation.reservation.state.BottomSheetState
import org.sopt.korailtalk.presentation.reservation.state.ReservationUiState
import javax.inject.Inject

@HiltViewModel
class ReservationViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: KorailTalkRepository
) : ViewModel() {

    companion object {
        private const val TAG = "KORAIL_TALK_VM"
    }

    val data = savedStateHandle.toRoute<Route.Reservation>()

    private val _uiState = MutableStateFlow<ReservationUiState>(ReservationUiState(
        origin = data.origin,
        destination = data.destination
    ))
    val uiState: StateFlow<ReservationUiState> = _uiState.asStateFlow()

    private val _bottomSheetState = MutableStateFlow(BottomSheetState())
    val bottomSheetState: StateFlow<BottomSheetState> = _bottomSheetState.asStateFlow()

    private var currentFilters = FilterState(
        trainType = TrainFilterType.ALL,
        seatType = null,
        isBookAvailable = null
    )

    private var isLoadingMore = false

    init {
        searchTrains(
            origin = data.origin,
            destination = data.destination,
            trainTypeFilter = TrainFilterType.ALL
        )
    }

    /**
     * 열차 검색
     */
    fun searchTrains(
        origin: String,
        destination: String,
        trainTypeFilter: TrainFilterType,
        seatType: String? = null,
        isBookAvailable: Boolean? = null,
        cursor: String? = "2025-12-01T14:00" // QA용 (14시 이후 열차 조회)
    ) {
        viewModelScope.launch {
            Log.d(TAG, "🚀 [searchTrains] 요청 시작: origin=$origin, destination=$destination, trainType=${trainTypeFilter?.displayName}")

            _uiState.value.copy(
                trains = UiState.Loading
            )
            currentFilters = FilterState(
                trainType = trainTypeFilter,
                seatType = seatType,
                isBookAvailable = isBookAvailable
            )  // ✅ enum 저장

            repository.getTrainList(
                origin,
                destination,
                trainTypeFilter.type?.serverValue,  // ✅ serverValue로 변환
                seatType,
                isBookAvailable,
                cursor
            )
                .onSuccess { result ->
                    Log.d(TAG, "✅ [searchTrains] 조회 성공: totalTrains=${result.totalTrains}, nextCursor=${result.nextCursor}")
                    Log.d(TAG, "✅ [searchTrains] trainList=${result.trains.map { it.trainNumber to it.type }}")

                    _uiState.update {
                        it.copy(
                            trains = UiState.Success(result.trains.toImmutableList()),
                            totalTrains = result.totalTrains
                        )
                    }
                }
                .onFailure { error ->
                    Log.e(TAG, "❌ [searchTrains] 조회 실패: ${error.message}", error)
//                    _uiState.value = ReservationUiState.Error(
//                        message = error.message ?: "알 수 없는 오류가 발생했습니다"
//                    )
                }
        }
    }

    /**
     * 클라이언트 측 필터 적용 (백그라운드 스레드에서 처리)
     */
    fun applyClientSideFilter(
        trainTypeFilter: TrainFilterType,
        seatTypeFilter: String? = null,
        isBookAvailableOnly: Boolean = false
    ) {
        val currentState = _uiState.value

//        Log.d(TAG, "🎛 [applyClientSideFilter] 시작 - trainType=$trainTypeFilter, 전체 데이터: ${currentState.trains.size}개")
        Log.d(TAG, "🎛 [applyClientSideFilter] 적용 - seatType=$seatTypeFilter, onlyAvailable=$isBookAvailableOnly")

        // ✅ 필터 상태 저장 (무한 스크롤 시 사용)
        currentFilters = currentFilters.copy(
            trainTypeFilter = trainTypeFilter.type?.serverValue,
            seatTypeFilter = seatTypeFilter,
            isBookAvailableOnly = isBookAvailableOnly
        )

        // ✅ 백그라운드 스레드에서 필터링 수행
        viewModelScope.launch {
            searchTrains(
                origin = currentState.origin,
                destination = currentState.destination,
                trainTypeFilter = trainTypeFilter,
                seatType = seatTypeFilter,
                isBookAvailable = isBookAvailableOnly
            )
        }
    }

    /**
     * 열차 필터링 로직 (성능 최적화)
     */
    private fun filterTrains(
        trains: List<DomainTrainItem>,
        trainTypeFilter: String?,
        seatTypeFilter: String?,
        isBookAvailableOnly: Boolean
    ): List<DomainTrainItem> {
        // 모든 필터가 비활성화면 원본 리스트 그대로 반환
        if (trainTypeFilter.isNullOrEmpty() || trainTypeFilter == "전체") {
            if (seatTypeFilter.isNullOrEmpty() || seatTypeFilter == "전체") {
                if (!isBookAvailableOnly) {
                    return trains
                }
            }
        }

        return trains.filter { train ->
            // 1. 열차 종류 필터 (가장 빠른 체크)
            if (trainTypeFilter != null && trainTypeFilter != "전체") {
                if (train.type.displayName != trainTypeFilter) return@filter false
            }

            // 2. 좌석 종류 필터
            when (seatTypeFilter) {
                "일반실" -> {
                    if (train.normalSeat.status == SeatStatusType.SOLD_OUT) return@filter false
                }
                "특실" -> {
                    if (train.premiumSeat == null || train.premiumSeat.status == SeatStatusType.SOLD_OUT) {
                        return@filter false
                    }
                }
            }

            // 3. 예약 가능 필터 (마지막 체크)
            if (isBookAvailableOnly) {
                val hasAvailableSeat = train.normalSeat.status != SeatStatusType.SOLD_OUT ||
                        (train.premiumSeat != null && train.premiumSeat.status != SeatStatusType.SOLD_OUT)
                if (!hasAvailableSeat) return@filter false
            }

            true
        }
    }

    /**
     * 무한 스크롤 - 추가 데이터 로드 (필터 적용 개선)
     */
    fun loadMoreTrains() {
        val currentState = _uiState.value
        if (currentState.nextCursor == null) {
            Log.d(TAG, "📭 [loadMoreTrains] 더 이상 불러올 데이터 없음")
            return
        }

        if (isLoadingMore) {
            Log.d(TAG, "⏳ [loadMoreTrains] 이미 로딩 중")
            return
        }

        isLoadingMore = true

        viewModelScope.launch {
            Log.d(TAG, "📥 [loadMoreTrains] 요청: nextCursor=${currentState.nextCursor}")
            repository.getTrainList(
                origin = currentState.origin,
                destination = currentState.destination,
                trainType = currentFilters.trainType.type?.serverValue,
                seatType = currentFilters.seatType,
                isBookAvailable = currentFilters.isBookAvailable,
                cursor = currentState.nextCursor
            ).onSuccess { result ->
                Log.d(TAG, "✅ [loadMoreTrains] 추가 성공: 새로 ${result.trains.size}개, nextCursor=${result.nextCursor}")
                
                // ✅ 새로 받은 데이터를 현재 필터 기준으로 필터링 (백그라운드 스레드)
                val newFilteredTrains = withContext(Dispatchers.Default) {
                    filterTrains(
                        result.trains,
                        currentFilters.trainTypeFilter,
                        currentFilters.seatTypeFilter,
                        currentFilters.isBookAvailableOnly
                    )
                }
                
                Log.d(TAG, "🎯 [loadMoreTrains] 필터 적용 후: ${newFilteredTrains.size}개")

                _uiState.update { currentState ->
                    currentState.copy(
                        trains = currentState.trains.updateIfSuccess {
                            (it.toImmutableList() + newFilteredTrains.toImmutableList()) as ImmutableList<DomainTrainItem>
                        },
                        nextCursor = result.nextCursor
                    )
                }
            }.onFailure { e ->
                Log.e(TAG, "❌ [loadMoreTrains] 실패: ${e.message}", e)
            }
            isLoadingMore = false
        }
    }

    fun showBottomSheet(train: DomainTrainItem) {
        Log.d(TAG, "📄 [showBottomSheet] 열차 선택됨: ${train.trainNumber} (${train.type})")
        _bottomSheetState.value = BottomSheetState(
            isVisible = true,
            selectedTrain = train,
            selectedSeatType = null
        )
    }

    fun hideBottomSheet() {
        Log.d(TAG, "🧹 [hideBottomSheet] 바텀시트 닫기")
        _bottomSheetState.value = BottomSheetState()
    }

    fun selectSeatType(seatType: SeatType) {
        Log.d(TAG, "💺 [selectSeatType] 좌석 선택됨: ${seatType.name}")
        _bottomSheetState.value = _bottomSheetState.value.copy(selectedSeatType = seatType)
    }

    fun refresh() {
        searchTrains(
            origin = _uiState.value.origin,
            destination = _uiState.value.destination,
            trainTypeFilter = currentFilters.trainType,  // ✅ enum 그대로 전달
            seatType = currentFilters.seatType,
            isBookAvailable = currentFilters.isBookAvailable,
        )
    }

    private fun UiState<ImmutableList<DomainTrainItem>>.updateIfSuccess(
        transform: (ImmutableList<DomainTrainItem>) -> ImmutableList<DomainTrainItem>
    ): UiState<ImmutableList<DomainTrainItem>> {
        return when (this) {
            is UiState.Success -> UiState.Success(transform(this.data))
            else -> this
        }
    }

    //  FilterState 수정
    private data class FilterState(
        val trainType: TrainFilterType,  // API 요청용 (enum)
        val seatType: String? = null,
        val isBookAvailable: Boolean? = null,
        // UI 필터 상태 추가
        val trainTypeFilter: String? = null,  // 클라이언트 필터용
        val seatTypeFilter: String? = null,
        val isBookAvailableOnly: Boolean = false
    )
}