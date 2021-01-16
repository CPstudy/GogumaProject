package com.cpstudy.gogumaproject.history

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cpstudy.gogumaproject.AppDatabase
import com.cpstudy.gogumaproject.R

class HistoryActivity : AppCompatActivity() {

    private val historyRepository: StoreHistoryRepository by lazy {
        val database = AppDatabase.getDatabase(this)
        StoreHistoryRepository(database.historyDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
    }
}