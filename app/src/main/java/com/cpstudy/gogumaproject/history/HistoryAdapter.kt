package com.cpstudy.gogumaproject.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cpstudy.gogumaproject.databinding.ItemLocationHistoryBinding

class HistoryAdapter(
    private val onDelete: (history: StoreHistory) -> Unit
) : ListAdapter<StoreHistory, HistoryAdapter.HistoryHolder>(DIFF_UTIL){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryHolder {
        val binding = ItemLocationHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class HistoryHolder(private val binding: ItemLocationHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(storeHistory: StoreHistory) {
            binding.historyTitle.text = storeHistory.storeName
            binding.deleteButton.setOnClickListener {
                onDelete(storeHistory)
            }
        }
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<StoreHistory>() {
            override fun areItemsTheSame(oldItem: StoreHistory, newItem: StoreHistory): Boolean {
                return oldItem.storeName == newItem.storeName
            }

            override fun areContentsTheSame(oldItem: StoreHistory, newItem: StoreHistory): Boolean {
                return oldItem == newItem
            }
        }
    }
}