package com.example.mindvocab.screens.learn

import android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.mindvocab.databinding.ItemSentenceExampleBinding

class ExampleAdapter(
    private val data: List<String>,
    private val listener: Listener
) : RecyclerView.Adapter<ExampleAdapter.ViewHolder>(), View.OnClickListener{

    interface Listener {
        fun onSentenceListen(sentence: String)
    }

    override fun onClick(view: View) {
        listener.onSentenceListen(view.tag as String)
    }


    class ViewHolder(val binding: ItemSentenceExampleBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSentenceExampleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.root.setOnClickListener(this)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.binding.root.tag = item
        holder.binding.listenSentence.tag = item

        holder.binding.sentence.text = item

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            holder.binding.sentence.justificationMode = JUSTIFICATION_MODE_INTER_WORD
        }
    }

}