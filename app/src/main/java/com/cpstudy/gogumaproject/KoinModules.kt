package com.cpstudy.gogumaproject

import androidx.room.RoomDatabase
import com.cpstudy.gogumaproject.history.HistoryViewModel
import com.cpstudy.gogumaproject.history.StoreHistoryDao
import com.cpstudy.gogumaproject.history.StoreHistoryDao_Impl
import com.cpstudy.gogumaproject.history.StoreHistoryRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModules = module {
    single<RoomDatabase> { AppDatabase.getDatabase(androidApplication()) }

    single<StoreHistoryDao> { StoreHistoryDao_Impl(get()) }

    single { StoreHistoryRepository(get()) }

    viewModel { HistoryViewModel(get()) }
}