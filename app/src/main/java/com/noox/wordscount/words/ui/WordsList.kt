package com.noox.wordscount.words.ui

import com.noox.wordscount.words.domain.model.Word
import java.util.*

class WordsList {

    private val wordsList = mutableListOf<Word>()
    private val wordsMap = mutableMapOf<String, Word>()

    var lastAction: ActionType = ActionType.None
    val items: List<Word>
            get() {
                val filter = filter ?: return wordsList
                return wordsList.filter { it.text.startsWith(filter) }
            }

    var filter: String? = null
        set(value) {
            field = if (value.isNullOrBlank()) null else value
            lastAction = ActionType.Filter
        }

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
        lastAction = ActionType.Add(word)
    }

    private fun updateWord(word: Word) {
        word.increaseTimesItAppears()
        lastAction =  ActionType.Update(word)
    }

    fun sortBy(sortType: SortType) {
        when (sortType) {
            SortType.Alphabetical -> wordsList.sortBy { it.text }
            SortType.Position -> wordsList.sortBy { it.position }
            SortType.Appearance -> wordsList.sortByDescending { it.timesItAppears }
        }
        lastAction = ActionType.Sort
    }

    fun clear() {
        wordsList.clear()
        wordsMap.clear()
        filter = null
        lastAction = ActionType.Clear
    }

    enum class SortType { Alphabetical, Position, Appearance }

    sealed class ActionType {
        object None: ActionType()
        data class Add(val word: Word): ActionType()
        data class Update(val word: Word): ActionType()
        object Clear: ActionType()
        object Sort: ActionType()
        object Filter: ActionType()
    }
}
