package com.example.mindvocab.screens.word

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mindvocab.databinding.ItemWordBinding
import com.example.mindvocab.model.word.learning.entities.WordToLearn

class WordAdapter : ListAdapter<WordToLearn, WordAdapter.ViewHolder>(ItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemWordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            word.text = item.word
            wordTranslation.text = item.translationList.toString()

            //TODO: add word progress
//            when(item.progress.toInt()) {
//                0 -> {
//                    wordStatus.text = "Новое слово"
//                }
//                6 -> {
//                    wordStatus.text = "Выучено"
//                }
//                else -> {
//                    wordStatus.text = "Новое слово"
//                }
//            }
        }
    }

    class ViewHolder(val binding: ItemWordBinding) : RecyclerView.ViewHolder(binding.root)

    object ItemCallback: DiffUtil.ItemCallback<WordToLearn>() {
        override fun areItemsTheSame(oldItem: WordToLearn, newItem: WordToLearn): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: WordToLearn, newItem: WordToLearn): Boolean {
            return oldItem == newItem
        }
    }
}