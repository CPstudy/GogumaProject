package com.cpstudy.gogumaproject.history

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cpstudy.gogumaproject.R
import org.koin.android.ext.android.inject

class HistoryActivity : AppCompatActivity() {

    private val historyRepository: StoreHistoryRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
    }
}