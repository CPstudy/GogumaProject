package com.cpstudy.gogumaproject.history

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface StoreHistoryDao {
    @Insert
    suspend fun insertStore(history: StoreHistory)

    @Query("SELECT * FROM storehistory")
    suspend fun findAll(): Flow<List<StoreHistory>>

    @Delete
    fun deleteStore(history: StoreHistory)
}