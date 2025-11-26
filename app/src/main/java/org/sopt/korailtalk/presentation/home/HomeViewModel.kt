package org.sopt.korailtalk.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.sopt.korailtalk.domain.repository.KorailTalkRepository
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val korailTalkRepository: KorailTalkRepository
) : ViewModel() {
    //상태 관리
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    //서버 로드 되지 않았으니까 api를 호출함
    private var isDataLoaded = false


    //실제 서버 통신
    fun getHomeBasicInfo() {
        //한번 api가 호출되면 더이상 호출하지 않음!
        if (isDataLoaded) return

        viewModelScope.launch {
            korailTalkRepository.getHomeBasicInfo()
                .onSuccess { response ->
                    isDataLoaded = true
                    Log.d("HomeViewModel", "성공! 데이터: ${response}")
                    //성공하면 받아온 데이터로 상태 교체
                    _uiState.update { currentState ->
                        currentState.copy(
                            startStation = response.origin,
                            endStation = response.destination
                        )
                    }

                }
                .onFailure { error ->
                    Log.e("HomeViewModel", "실패 : ${error.message}")
                    //실패 시
                }

        }
    }

    // 역 위치를 서로 바꾸는 로직
    fun swapStations() {
        _uiState.update { currentState ->
            val temp = currentState.startStation
            currentState.copy(
                startStation = currentState.endStation,
                endStation = temp
            )
        }
    }

}
