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

    private val wordsList = mutableListOf<Word>()
    private val wordsMap = mutableMapOf<String, Word>()

    val words: LiveData<List<Word>> get() = _words
    private val _words = MutableLiveData<List<Word>>(wordsList)

    fun loadWordsFrom(uri: Uri) {
        viewModelScope.launch {
            loadWords(uri).collect { result ->
                when (result) {
                    is Result.Success ->  { add(result.data) }
                    is Result.Error -> Log.e("NOOX", "ERROR: ${result.type.name}")
                }
            }
        }
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
        _words.value = wordsList
    }

    private fun updateWord(word: Word) {
        word.increaseTimesItAppears()
        _words.value = ArrayList<Word>(wordsList)
    }
}
