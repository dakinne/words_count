package com.noox.wordscount.words.ui

import java.util.*

class Words {

    private val wordsList = mutableListOf<Word>()
    private val wordsMap = mutableMapOf<String, Word>()

    val items : List<Word>
        get() = wordsList

    fun add(text: String) {
        val lowerCaseText = text.toLowerCase(Locale.ROOT)
        wordsMap[lowerCaseText]?.let {
            it.increaseTimesItAppears()
            return
        }
        val word = Word(text, wordsList.size)
        wordsList.add(word)
        wordsMap[lowerCaseText] = word
    }

}