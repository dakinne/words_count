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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

class WordsViewModel(
    private val loadWords: LoadWords
) : ViewModel() {

    private val wordsList = WordsList()

    val words: LiveData<WordsList> get() = _words
    private val _words = MutableLiveData<WordsList>(wordsList)

    val showFilter: LiveData<Boolean> get() = _showFilter
    private val _showFilter = MutableLiveData<Boolean>(false)

    var filter = MutableLiveData<String>()

    @ExperimentalCoroutinesApi
    fun loadWordsFrom(uri: Uri) {
        clear()
        hideFilter()
        viewModelScope.launch {
            loadWords(uri)
                .onCompletion { showFilter() }
                .collect { result ->
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
        filter.value = ""
        _words.value = wordsList
    }

    fun sortBy(sortType: SortType) {
        wordsList.sortBy(sortType)
        _words.value = wordsList
    }

    fun onFilterChange() {
        wordsList.filter = filter.value
        _words.value = wordsList
    }

    private fun hideFilter() {
        _showFilter.value = false
    }

    private fun showFilter() {
        _showFilter.value = true
    }
}
