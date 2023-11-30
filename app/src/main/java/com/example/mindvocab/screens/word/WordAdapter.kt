package com.example.mindvocab.screens.word

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mindvocab.databinding.ItemWordBinding
import com.example.mindvocab.model.word.Word

class WordAdapter : ListAdapter<Word, WordAdapter.ViewHolder>(ItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemWordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            word.text = item.word
            wordTranslation.text = item.translationList.toString()

            when(item.progress.toInt()) {
                0 -> {
                    wordStatus.text = "Новое слово"
                }
                6 -> {
                    wordStatus.text = "Выучено"
                }
                else -> {
                    wordStatus.text = "Новое слово"
                }
            }
        }
    }

    class ViewHolder(val binding: ItemWordBinding) : RecyclerView.ViewHolder(binding.root)

    object ItemCallback: DiffUtil.ItemCallback<Word>() {
        override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem == newItem
        }
    }
}