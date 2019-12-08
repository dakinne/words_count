package com.noox.wordscount.words.ui

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noox.wordscount.common.Result
import com.noox.wordscount.words.domain.usecase.LoadWords
import com.noox.wordscount.words.ui.WordsList.SortType
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class WordsViewModel(
    private val loadWords: LoadWords
) : ViewModel() {

    private val wordsList = WordsList()

    val words: LiveData<WordsList> get() = _words
    private val _words = MutableLiveData<WordsList>(wordsList)

    fun loadWordsFrom(uri: Uri) {
        clear()
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
        wordsList.add(text)
        _words.value = wordsList
    }

    private fun clear() {
        wordsList.clear()
        _words.value = wordsList
    }

    fun sortBy(sortType: SortType) {
        wordsList.sortBy(sortType)
        _words.value = wordsList
    }
}
