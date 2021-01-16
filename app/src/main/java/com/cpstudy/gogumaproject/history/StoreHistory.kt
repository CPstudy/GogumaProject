package com.cpstudy.gogumaproject.history

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 사용자가 제공받는 식당 위치를 로컬에 저장하는 엔티티
 */
@Entity
data class StoreHistory(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "store_name") val storeName: String,
    @ColumnInfo(name = "latitude") val latitude: Float,
    @ColumnInfo(name = "longitude") val longitude: Float
)