package com.cpstudy.gogumaproject

import androidx.room.RoomDatabase
import com.cpstudy.gogumaproject.history.StoreHistoryRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val appModules = module {
    single<RoomDatabase> { AppDatabase.getDatabase(androidApplication()) }

    single { StoreHistoryRepository(get()) }
}