package com.noox.wordscount.words.ui

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noox.wordscount.common.Result
import com.noox.wordscount.words.domain.LoadWords
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

class WordsViewModel(
    private val loadWords: LoadWords
) : ViewModel() {

    enum class SortType { Alphabetical, Position, Appearance }

    private val wordsList = mutableListOf<Word>()
    private val wordsMap = mutableMapOf<String, Word>()

    val words: LiveData<Words> get() = _words
    private val _words = MutableLiveData<Words>(Words(wordsList))

    fun loadWordsFrom(uri: Uri) {
        emptyWords()
        viewModelScope.launch {
            loadWords(uri).collect { result ->
                when (result) {
                    is Result.Success ->  { add(result.data) }
                    is Result.Error -> Log.e("NOOX", "ERROR: ${result.type.name}")
                }
            }
        }
    }

    fun sortBy(sortType: SortType) {
        when (sortType) {
            SortType.Alphabetical -> wordsList.sortBy { it.text }
            SortType.Position -> wordsList.sortBy { it.position }
            SortType.Appearance -> wordsList.sortByDescending { it.timesItAppears }
        }
        _words.value = Words(wordsList, ActionType.Sort)
    }

    private fun emptyWords() {
        wordsList.clear()
        wordsMap.clear()
        _words.value = Words(wordsList, ActionType.Clear)
    }

    private fun add(text: String) {
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
        _words.value = Words(wordsList, ActionType.Add(word))
    }

    private fun updateWord(word: Word) {
        word.increaseTimesItAppears()
        _words.value = Words(wordsList, ActionType.Update(word))
    }
}
