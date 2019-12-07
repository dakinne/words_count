package com.noox.wordscount.words.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.noox.wordscount.databinding.ItemListBinding
import com.noox.wordscount.words.ui.WordsAdapter.SortType.*
import java.util.*


// TODO: Se podria mejorar haciendolo de esta manera
//class FeedAdapter(
//    private val viewBinders: Map<FeedItemClass, FeedItemBinder>
//) : ListAdapter<Any, RecyclerView.ViewHolder>(FeedDiffCallback(viewBinders))

class WordsAdapter : RecyclerView.Adapter<WordViewHolder>() {

    enum class SortType { Alphabetical, Position, Appearance }

    private val wordsList = mutableListOf<Word>()
    private val wordsMap = mutableMapOf<String, Word>()

    fun add(text: String) {
        val lowerCaseText = text.toLowerCase(Locale.ROOT)
        val word = wordsMap[lowerCaseText]

        if (word == null) {
            addNewWord(text, lowerCaseText)
        } else {
            updateWord(word)
        }
    }

    private fun addNewWord(text: String, lowerCaseText: String) {
        val word = Word(text, wordsList.size)
        wordsList.add(word)
        wordsMap[lowerCaseText] = word
        notifyItemInserted(word.position)
    }

    private fun updateWord(word: Word) {
        word.increaseTimesItAppears()
        notifyItemChanged(word.position)
    }

    fun sortBy(type: SortType) {
        when (type) {
            Alphabetical -> wordsList.sortBy { it.text }
            Position -> wordsList.sortBy { it.position }
            Appearance -> wordsList.sortByDescending { it.timesItAppears }
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        WordViewHolder(
            binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.bind(wordsList[position])
    }

    override fun getItemCount() : Int {
        return wordsList.size
    }

}
