package com.example.mindvocab.screens.statistic

import android.graphics.text.LineBreaker
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mindvocab.R
import com.example.mindvocab.databinding.ItemAchievementBinding
import com.example.mindvocab.model.achievement.Achievement

class AchievementAdapter : ListAdapter<Achievement, AchievementAdapter.ViewHolder>(ItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAchievementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            achievementName.text = item.title

            achievementDescription.text = item.description
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                achievementDescription.justificationMode = LineBreaker.JUSTIFICATION_MODE_INTER_WORD
            }

            Glide.with(achievementPhoto.context)
                .load(item.icon)
                .circleCrop()
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder)
                .into(achievementPhoto)

            achievementProgressBarText.text = "0/${item.progress}"
            achievementProgress.max = item.progress
            achievementProgress.progress = item.progress / 2
        }
    }

    class ViewHolder(val binding: ItemAchievementBinding) : RecyclerView.ViewHolder(binding.root)

    object ItemCallback: DiffUtil.ItemCallback<Achievement>() {
        override fun areItemsTheSame(oldItem: Achievement, newItem: Achievement): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Achievement, newItem: Achievement): Boolean {
            return oldItem == newItem
        }
    }
}