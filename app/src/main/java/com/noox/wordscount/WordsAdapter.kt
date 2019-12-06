package com.noox.wordscount

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.noox.wordscount.databinding.ItemListBinding
import java.util.*


// TODO: Se podria mejorar haciendolo de esta manera
//class FeedAdapter(
//    private val viewBinders: Map<FeedItemClass, FeedItemBinder>
//) : ListAdapter<Any, RecyclerView.ViewHolder>(FeedDiffCallback(viewBinders))

class WordsAdapter : RecyclerView.Adapter<WordViewHolder>() {

    private val wordsList = mutableListOf<Word>()
    private val wordsMap = mutableMapOf<String, Word>()

    fun add(text: String) {
        val lowerCaseText = text.toLowerCase(Locale.ROOT)
        wordsMap[lowerCaseText]?.let {
            it.increaseTimesItAppears()
            notifyItemChanged(it.position)
            Log.i("noox", "item changed in ${it.position}")
            return
        }
        val word = Word(text, wordsList.size)
        wordsList.add(word)
        wordsMap[lowerCaseText] = word
        notifyItemInserted(word.position)
        Log.i("noox", "item inserted in ${word.position}")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = WordViewHolder(
        binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.bind(wordsList[position])
    }

    override fun getItemCount() : Int {
        Log.i("noox", "itemCount ${wordsList.size}")
        return wordsList.size
    }

}
