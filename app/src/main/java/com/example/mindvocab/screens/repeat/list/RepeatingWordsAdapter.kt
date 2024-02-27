package com.example.mindvocab.screens.repeat.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mindvocab.R
import com.example.mindvocab.databinding.ItemRepeatingWordBinding
import com.example.mindvocab.model.word.entities.WordToRepeat

class RepeatingWordsAdapter : ListAdapter<WordToRepeat, RepeatingWordsAdapter.ViewHolder>(ItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRepeatingWordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        with(holder.binding) {
            word.text = item.word
            wordTranscription.text = item.transcription

            wordStartedAtValue.text = ""
            wordLastRepeatedAtValue.text = item.lastRepeatedAt.toString()
            wordTimesRepeatedValue.text = item.timesRepeated.toString()

            Glide.with(wordWordSetImage.context)
                .load(item.word)
                .circleCrop()
                .placeholder(R.drawable.ic_category)
                .error(R.drawable.ic_category)
                .into(wordWordSetImage)
        }
    }

    class ViewHolder(val binding: ItemRepeatingWordBinding) : RecyclerView.ViewHolder(binding.root)

    object ItemCallback : DiffUtil.ItemCallback<WordToRepeat>() {
        override fun areItemsTheSame(oldItem: WordToRepeat, newItem: WordToRepeat): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: WordToRepeat, newItem: WordToRepeat): Boolean {
            return oldItem == newItem
        }
    }
}