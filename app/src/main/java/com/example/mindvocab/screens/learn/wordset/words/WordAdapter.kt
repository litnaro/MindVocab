package com.example.mindvocab.screens.learn.wordset.words

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mindvocab.R
import com.example.mindvocab.databinding.ItemWordBinding
import com.example.mindvocab.model.word.WordsCalculations
import com.example.mindvocab.model.word.entities.WordStatistic

class WordAdapter : ListAdapter<WordStatistic, WordAdapter.ViewHolder>(ItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemWordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        val context = holder.binding.root.context

        with(holder.binding) {
            word.text = item.word
            wordTranscription.text = item.transcription
            wordTranslation.text = item.translation

            when(item.wordStatus) {
                WordsCalculations.WordStatus.NEW -> {
                    wordStatus.text = context.getString(R.string.word_status_new)

                    wordTextProgress.visibility = View.GONE

                    wordProgressContainer.setCardBackgroundColor(context.getColor(R.color.word_new_status))

                    wordImageStatus.visibility = View.VISIBLE
                    wordImageStatus.setImageResource(R.drawable.ic_baseline_lightbulb)
                    wordImageStatus.setColorFilter(context.getColor(R.color.word_new_status))
                }
                WordsCalculations.WordStatus.IN_PROGRESS -> {
                    wordStatus.text = context.getString(R.string.word_status_in_progress)

                    wordTextProgress.visibility = View.VISIBLE
                    wordTextProgress.text = context.getString(R.string.percentage, item.learnProgress)

                    wordProgressContainer.setCardBackgroundColor(context.getColor(R.color.word_in_progress_status))

                    wordImageStatus.visibility = View.GONE
                }
                WordsCalculations.WordStatus.KNOWN -> {
                    wordStatus.text = context.getString(R.string.word_status_known)

                    wordTextProgress.visibility = View.GONE

                    wordProgressContainer.setCardBackgroundColor(context.getColor(R.color.word_known_status))

                    wordImageStatus.visibility = View.VISIBLE
                    wordImageStatus.setImageResource(R.drawable.ic_check)
                    wordImageStatus.setColorFilter(context.getColor(R.color.word_known_status))
                }
                WordsCalculations.WordStatus.LEARNED -> {
                    wordStatus.text = context.getString(R.string.word_status_learned)

                    wordTextProgress.visibility = View.GONE

                    wordProgressContainer.setCardBackgroundColor(context.getColor(R.color.word_learned_status))

                    wordImageStatus.visibility = View.VISIBLE
                    wordImageStatus.setImageResource(R.drawable.ic_check)
                    wordImageStatus.setColorFilter(context.getColor(R.color.word_learned_status))
                }
            }
        }
    }

    class ViewHolder(val binding: ItemWordBinding) : RecyclerView.ViewHolder(binding.root)

    object ItemCallback: DiffUtil.ItemCallback<WordStatistic>() {
        override fun areItemsTheSame(oldItem: WordStatistic, newItem: WordStatistic): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: WordStatistic, newItem: WordStatistic): Boolean {
            return oldItem == newItem
        }
    }
}