package com.example.mindvocab.screens.statistic

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mindvocab.R
import com.example.mindvocab.databinding.ItemAchievementBinding
import com.example.mindvocab.model.achievement.entities.Achievement

class AchievementAdapter (
    private val listener: Listener
) : ListAdapter<Achievement, AchievementAdapter.ViewHolder>(ItemCallback), View.OnClickListener {

    fun interface Listener {
        fun onAchievementDetail(achievement: Achievement)
    }

    override fun onClick(view: View) {
        val achievement = view.tag as Achievement
        listener.onAchievementDetail(achievement)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAchievementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.root.setOnClickListener(this)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            root.tag = item
            achievementName.text = item.title

            Glide.with(achievementPhoto.context)
                .load(item.image)
                .circleCrop()
                .placeholder(R.drawable.ic_meditation)
                .error(R.drawable.ic_meditation)
                .into(achievementPhoto)

            achievementProgress.max = item.maxProgress
            achievementProgress.progress = if (item.progress > item.maxProgress) item.maxProgress else item.progress

            if (item.progress >= item.maxProgress) {
                achievementCompletedIcon.visibility = View.VISIBLE
            } else {
                achievementCompletedIcon.visibility = View.GONE
            }
        }
    }

    class ViewHolder(val binding: ItemAchievementBinding) : RecyclerView.ViewHolder(binding.root)

    object ItemCallback : DiffUtil.ItemCallback<Achievement>() {
        override fun areItemsTheSame(oldItem: Achievement, newItem: Achievement): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Achievement, newItem: Achievement): Boolean {
            return oldItem == newItem
        }
    }
}