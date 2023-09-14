package com.example.mindvocab.screens.category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mindvocab.R
import com.example.mindvocab.model.caregory.Category
import com.example.mindvocab.databinding.CategoryItemBinding

class CategoryAdapter(
    private val listener: Listener
) : ListAdapter<Category, CategoryAdapter.ViewHolder>(ItemCallback), View.OnClickListener {

    interface Listener {
        fun onCategoryDetail(category: Category)
        fun onCategorySelectedToggle(category: Category)
    }

    override fun onClick(view: View) {
        val category = view.tag as Category

        when (view.id) {
            R.id.categorySelectedIcon -> {
                listener.onCategorySelectedToggle(category)
            }
            else -> {
                listener.onCategoryDetail(category)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CategoryItemBinding.inflate(inflater, parent, false)

        binding.root.setOnClickListener(this)
        binding.categorySelectedIcon.setOnClickListener(this)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = getItem(position)

        with(holder.binding) {
            root.tag = category
            categorySelectedIcon.tag = category

            categoryName.text = category.name
            categoryWordsStatistic.text = categoryWordsStatistic.context.getString(R.string.category_words, category.wordsKnown.size, category.words.size)

            Glide.with(categoryIcon.context)
                .load(category.icon)
                .circleCrop()
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder)
                .into(categoryIcon)

            categorySelectedIcon.setImageResource (
                if (category.isSelected) {
                    R.drawable.ic_check_box_filled
                } else {
                    R.drawable.ic_check_box_empty
                }
            )
        }
    }


    class ViewHolder (val binding: CategoryItemBinding) : RecyclerView.ViewHolder(binding.root)

    object ItemCallback : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }
    }
}