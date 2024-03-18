package com.example.mindvocab.screens.repeat.words

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mindvocab.R
import com.example.mindvocab.databinding.ItemRepeatingWordBinding
import com.example.mindvocab.model.word.WordsCalculations
import com.example.mindvocab.model.word.entities.WordToRepeatDetail

class RepeatingWordsAdapter : ListAdapter<WordToRepeatDetail, RepeatingWordsAdapter.ViewHolder>(ItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRepeatingWordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        val context = holder.binding.root.context

        with(holder.binding) {
            word.text = item.word
            wordTranscription.text = item.transcription

            wordStartedAtValue.text = item.startedAt
            wordLastRepeatedAtValue.text = item.lastRepeatedAt
            //TODO remove request to WordsCalculations
            wordTimesRepeatedValue.text = context.getString(R.string.amount_of_words, item.timesRepeated, WordsCalculations.TIMES_REPEATED_TO_LEARN)

            Glide.with(wordWordSetImage.context)
                .load(item.wordSetImage)
                .centerCrop()
                .placeholder(R.drawable.ic_category)
                .error(R.drawable.ic_category)
                .into(wordWordSetImage)
        }
    }

    class ViewHolder(val binding: ItemRepeatingWordBinding) : RecyclerView.ViewHolder(binding.root)

    object ItemCallback : DiffUtil.ItemCallback<WordToRepeatDetail>() {
        override fun areItemsTheSame(oldItem: WordToRepeatDetail, newItem: WordToRepeatDetail): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: WordToRepeatDetail, newItem: WordToRepeatDetail): Boolean {
            return oldItem == newItem
        }
    }
}