package com.noox.wordscount.words.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.noox.wordscount.databinding.ItemListBinding
import com.noox.wordscount.words.ui.WordsList.ActionType

class WordsAdapter(val words: WordsList) : RecyclerView.Adapter<WordViewHolder>() {

    private var items = words.items

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        WordViewHolder(
            binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun update(words: WordsList) {
        items = words.items
        when (val action = words.lastAction) {
            is ActionType.Add ->  notifyItemInserted(action.word.position)
            is ActionType.Update ->  notifyItemChanged(action.word.position)
            is ActionType.Clear -> notifyDataSetChanged()
            is ActionType.Sort -> notifyDataSetChanged()
        }
    }

}
