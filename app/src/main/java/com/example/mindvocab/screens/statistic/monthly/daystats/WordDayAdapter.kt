package com.example.mindvocab.screens.statistic.monthly.daystats

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mindvocab.R
import com.example.mindvocab.databinding.ItemWordWithCategoryImageBinding
import com.example.mindvocab.model.statistic.entities.WordDayStatistic
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class WordDayAdapter : ListAdapter<WordDayStatistic, WordDayAdapter.ViewHolder>(ItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemWordWithCategoryImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        with(holder.binding) {
            word.text = item.word
            wordTranscription.text = item.transcription

            time.text = SimpleDateFormat("hh:mm aa", Locale.getDefault()).format(Date(item.actionDate))

            Calendar.getInstance().apply {
                timeInMillis = item.actionDate
            }

            Glide.with(wordWordSetImage.context)
                .load(item.wordSetImage)
                .centerCrop()
                .placeholder(R.drawable.ic_category)
                .error(R.drawable.ic_category)
                .into(wordWordSetImage)
        }
    }

    class ViewHolder(val binding: ItemWordWithCategoryImageBinding) : RecyclerView.ViewHolder(binding.root)

    object ItemCallback : DiffUtil.ItemCallback<WordDayStatistic>() {
        override fun areItemsTheSame(oldItem: WordDayStatistic, newItem: WordDayStatistic): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: WordDayStatistic, newItem: WordDayStatistic): Boolean {
            return oldItem == newItem
        }
    }

}