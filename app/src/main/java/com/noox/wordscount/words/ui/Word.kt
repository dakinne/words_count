package com.noox.wordscount.words.ui

data class Word(val text: String, val position: Int) {

    var timesItAppears: Int = 1
        private set

    fun increaseTimesItAppears() {
        timesItAppears++
    }

    override fun toString(): String {
        return "Word(text=$text, position=$position, timesItAppears=$timesItAppears)"
    }
}