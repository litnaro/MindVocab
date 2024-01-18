package com.example.mindvocab.screens.learn.wordset

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mindvocab.R
import com.example.mindvocab.databinding.ItemWordSetBinding
import com.example.mindvocab.model.sets.entity.WordSet

class WordSetAdapter(
    private val listener: Listener
) : ListAdapter<WordSet, WordSetAdapter.ViewHolder>(ItemCallback), View.OnClickListener {

    interface Listener {
        fun addWordSetToLearning(wordSet: WordSet)
        fun onWordSetDetail(wordSet: WordSet)
    }

    override fun onClick(view: View) {
        val wordSet = view.tag as WordSet
        when(view.id) {
            R.id.wordSetIsSelected -> {
                listener.addWordSetToLearning(wordSet)
            }
            else -> {
                listener.onWordSetDetail(wordSet)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemWordSetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.root.setOnClickListener(this)
        binding.wordSetIsSelected.setOnClickListener(this)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        with(holder.binding) {
            root.tag = item
            wordSetIsSelected.tag = item

            if (item.isSelected) {
                wordSetIsSelected.setImageResource(R.drawable.ic_check_box_filled)
            } else {
                wordSetIsSelected.setImageResource(R.drawable.ic_check_box_empty)
            }

            wordSetTitle.text = item.name
            wordSetProgress.text = "${item.accountCompletedWordsCount}/${item.wordsCount}"

            Glide.with(wordSetPhoto.context)
                .load(item.photo)
                .circleCrop()
                .placeholder(R.drawable.ic_article)
                .error(R.drawable.ic_article)
                .into(wordSetPhoto)

        }
    }

    class ViewHolder(val binding: ItemWordSetBinding) : RecyclerView.ViewHolder(binding.root)

    object ItemCallback : DiffUtil.ItemCallback<WordSet>() {
        override fun areItemsTheSame(oldItem: WordSet, newItem: WordSet): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: WordSet, newItem: WordSet): Boolean {
            return oldItem == newItem
        }

    }
}