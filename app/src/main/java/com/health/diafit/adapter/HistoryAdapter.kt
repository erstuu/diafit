package com.health.diafit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.health.diafit.databinding.HistoryItemBinding
import com.health.diafit.models.HistoryEntity
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

class HistoryAdapter : ListAdapter<HistoryEntity, HistoryAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: HistoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: HistoryEntity) {
            binding.apply {
                val inputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val outputDateFormat = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault())
                val formattedDate = try {
                    val date = user.checkDate?.trim()?.let {
                        inputDateFormat.parse(it)
                    }
                    date?.let {
                        outputDateFormat.format(it)
                    }
                } catch (e: ParseException) {
                    e.printStackTrace()
                    "Invalid date"
                }
                tvDate.text = formattedDate
                tvResultGlucose.text = user.bloodGlucoseLevel.toString()
                tvResultHemoglobin.text = user.hbA1cLevel.toString()
                tvResultCheck.text = user.diabetesCategory.toString()
            }
        }
    }

    companion object DIFF_CALLBACK : DiffUtil.ItemCallback<HistoryEntity>() {
        override fun areItemsTheSame(oldItem: HistoryEntity, newItem: HistoryEntity): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: HistoryEntity, newItem: HistoryEntity): Boolean {
            return oldItem == newItem
        }
    }
}