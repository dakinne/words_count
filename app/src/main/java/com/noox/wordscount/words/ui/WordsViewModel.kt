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

class WordsViewModel(
    private val loadWords: LoadWords
) : ViewModel() {

    val words: LiveData<Words> get() = _words
    private val _words = MutableLiveWords()

    fun loadWordsFrom(uri: Uri) {
        viewModelScope.launch {
            loadWords(uri).collect { result ->
                when (result) {
                    is Result.Success ->  { _words.add(result.data) }
                    is Result.Error -> Log.e("NOOX", "ERROR: ${result.type.name}")
                }
            }
        }
    }

    private class MutableLiveWords: MutableLiveData<Words>() {
        init {
            value = Words()
        }

        fun add(word: String) {
            value!!.add(word)
            //value = value!!
        }
    }
}
// Log.i("NOOX", "collected ${result.data}")