package com.cpstudy.gogumaproject.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class HistoryViewModel(private val historyRepository: StoreHistoryRepository) : ViewModel() {

    val histories = historyRepository.findAll()

    init {
        viewModelScope.launch {
            loadHistory()
        }
    }

    fun removeHistory(storeHistory: StoreHistory) = viewModelScope.launch {
        historyRepository.delete(storeHistory)
    }

    private suspend fun loadHistory() {
        historyRepository.insert(StoreHistory(storeName = "맥도날드", latitude = 126f, longitude = 20f))
        historyRepository.insert(StoreHistory(storeName = "롯데리아", latitude = 126f, longitude = 20f))
        historyRepository.insert(StoreHistory(storeName = "스시스시", latitude = 126f, longitude = 20f))
        historyRepository.insert(StoreHistory(storeName = "이태리옥", latitude = 126f, longitude = 20f))
        historyRepository.insert(StoreHistory(storeName = "코이라멘", latitude = 126f, longitude = 20f))
        historyRepository.insert(StoreHistory(storeName = "도시", latitude = 126f, longitude = 20f))
    }
}
