package org.sopt.korailtalk.presentation.reservation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sopt.korailtalk.domain.model.DomainTrainItem
import org.sopt.korailtalk.domain.repository.KorailTalkRepository
import org.sopt.korailtalk.domain.type.SeatStatusType
import org.sopt.korailtalk.domain.type.SeatType
import org.sopt.korailtalk.domain.type.TrainType
import org.sopt.korailtalk.presentation.reservation.state.BottomSheetState
import org.sopt.korailtalk.presentation.reservation.state.ReservationUiState
import javax.inject.Inject

@HiltViewModel
class ReservationViewModel @Inject constructor(
    private val repository: KorailTalkRepository
) : ViewModel() {

    companion object {
        private const val TAG = "KORAIL_TALK_VM"
    }

    private val _uiState = MutableStateFlow<ReservationUiState>(ReservationUiState.Initial)
    val uiState: StateFlow<ReservationUiState> = _uiState.asStateFlow()

    private val _bottomSheetState = MutableStateFlow(BottomSheetState())
    val bottomSheetState: StateFlow<BottomSheetState> = _bottomSheetState.asStateFlow()

    private var currentFilters = FilterState()
    private var isLoadingMore = false

    /**
     * 열차 검색
     */
    fun searchTrains(
        origin: String,
        destination: String,
        trainType: TrainType? = null,  // ✅ enum으로 받기
        seatType: String? = null,
        isBookAvailable: Boolean? = null,
        cursor: String? = null
    ) {
        viewModelScope.launch {
            Log.d(TAG, "🚀 [searchTrains] 요청 시작: origin=$origin, destination=$destination, trainType=${trainType?.displayName}")

            _uiState.value = ReservationUiState.Loading
            currentFilters = FilterState(trainType, seatType, isBookAvailable)  // ✅ enum 저장

            repository.getTrainList(
                origin,
                destination,
                trainType?.serverValue,  // ✅ serverValue로 변환
                seatType,
                isBookAvailable,
                cursor
            )
                .onSuccess { result ->
                    Log.d(TAG, "✅ [searchTrains] 조회 성공: totalTrains=${result.totalTrains}, nextCursor=${result.nextCursor}")
                    Log.d(TAG, "✅ [searchTrains] trainList=${result.trains.map { it.trainNumber to it.type }}")

                    _uiState.value = ReservationUiState.Success(
                        trains = result.trains,
                        origin = result.origin,
                        destination = result.destination,
                        totalTrains = result.totalTrains,
                        filteredTrains = result.trains,
                        nextCursor = result.nextCursor
                    )
                }
                .onFailure { error ->
                    Log.e(TAG, "❌ [searchTrains] 조회 실패: ${error.message}", error)
                    _uiState.value = ReservationUiState.Error(
                        message = error.message ?: "알 수 없는 오류가 발생했습니다"
                    )
                }
        }
    }

    /**
     * 클라이언트 측 필터 적용
     */
    fun applyClientSideFilter(
        trainTypeFilter: String? = null,  // displayName으로 받음
        seatTypeFilter: String? = null,
        isBookAvailableOnly: Boolean = false
    ) {
        val currentState = _uiState.value
        if (currentState !is ReservationUiState.Success) return

        Log.d(TAG, "🎛 [applyClientSideFilter] 적용 - trainType=$trainTypeFilter, seatType=$seatTypeFilter, onlyAvailable=$isBookAvailableOnly")

        val filteredTrains = currentState.trains.filter { train ->
            val matchesTrainType = when {
                trainTypeFilter.isNullOrEmpty() || trainTypeFilter == "전체" -> true
                else -> train.type.displayName == trainTypeFilter  // ✅ displayName과 비교
            }

            val matchesSeatType = when (seatTypeFilter) {
                "일반실" -> train.normalSeat.status != SeatStatusType.SOLD_OUT
                "특실" -> train.premiumSeat?.status != SeatStatusType.SOLD_OUT
                else -> true
            }

            val matchesBookAvailable = if (isBookAvailableOnly) {
                train.normalSeat.status != SeatStatusType.SOLD_OUT ||
                        train.premiumSeat?.status != SeatStatusType.SOLD_OUT
            } else true

            matchesTrainType && matchesSeatType && matchesBookAvailable
        }

        Log.d(TAG, "🎯 [applyClientSideFilter] 필터 결과 ${filteredTrains.size}개")

        _uiState.value = currentState.copy(filteredTrains = filteredTrains)
    }

    /**
     * 무한 스크롤 - 추가 데이터 로드
     */
    fun loadMoreTrains() {
        val currentState = _uiState.value
        if (currentState !is ReservationUiState.Success) return
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
                trainType = currentFilters.trainType?.serverValue,  // ✅ serverValue 사용
                seatType = currentFilters.seatType,
                isBookAvailable = currentFilters.isBookAvailable,
                cursor = currentState.nextCursor
            ).onSuccess { result ->
                Log.d(TAG, "✅ [loadMoreTrains] 추가 성공: 새로 ${result.trains.size}개, nextCursor=${result.nextCursor}")
                _uiState.value = currentState.copy(
                    trains = currentState.trains + result.trains,
                    filteredTrains = currentState.filteredTrains + result.trains,
                    nextCursor = result.nextCursor
                )
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
        val currentState = _uiState.value
        if (currentState is ReservationUiState.Success) {
            Log.d(TAG, "🔄 [refresh] 새로고침 실행 (origin=${currentState.origin}, destination=${currentState.destination})")
            searchTrains(
                origin = currentState.origin,
                destination = currentState.destination,
                trainType = currentFilters.trainType,  // ✅ enum 그대로 전달
                seatType = currentFilters.seatType,
                isBookAvailable = currentFilters.isBookAvailable
            )
        } else {
            Log.d(TAG, "🔄 [refresh] 새로고침 무시 (현재 상태: ${currentState::class.simpleName})")
        }
    }

    //  FilterState 수정
    private data class FilterState(
        val trainType: TrainType? = null,  // String → TrainType enum
        val seatType: String? = null,
        val isBookAvailable: Boolean? = null
    )
}