package com.cpstudy.gogumaproject.history

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class StoreHistoryRepository(private val historyDao: StoreHistoryDao) {

    @WorkerThread
    suspend fun insert(history: StoreHistory) {
        historyDao.insertStore(history)
    }

    @WorkerThread
    suspend fun delete(history: StoreHistory) {
        historyDao.deleteStore(history)
    }

    @WorkerThread
    fun findAll(): Flow<List<StoreHistory>> {
        return historyDao.findAll()
    }
}