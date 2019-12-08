package com.noox.wordscount.words.ui

import androidx.recyclerview.widget.RecyclerView
import com.noox.wordscount.databinding.ItemListBinding

class WordViewHolder(
    private val binding: ItemListBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(word: Word) {
        binding.word.text = word.text
        binding.count.text = word.timesItAppears.toString()
    }

}
