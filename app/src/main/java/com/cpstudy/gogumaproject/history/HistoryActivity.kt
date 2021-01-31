package com.cpstudy.gogumaproject.history

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.cpstudy.gogumaproject.R
import com.cpstudy.gogumaproject.databinding.ActivityHistoryBinding
import com.cpstudy.gogumaproject.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class HistoryActivity : AppCompatActivity() {

    private val historyRepository: StoreHistoryRepository by inject()

    private val historyViewModel: HistoryViewModel by inject()

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityHistoryBinding>(this, R.layout.activity_history)
    }

    private lateinit var historyAdapter: HistoryAdapter

    private var histories: List<StoreHistory> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        historyAdapter = HistoryAdapter(onDelete = { history ->
            lifecycleScope.launch {
                historyViewModel.removeHistory(history)
            }
        })

        binding.historyList.adapter = historyAdapter

        lifecycleScope.launch {
            historyAdapter.submitList(histories)
            historyViewModel.histories.collect {
                historyAdapter.submitList(it)
            }
        }
    }
}