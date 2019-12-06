package com.noox.wordscount

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class WordsTest {

    @Test
    fun words_addOneWord() {
        val words = Words()
        words.add("noox")
        assertEquals(words.items.size, 1)
    }

    @Test
    fun words_addWordTwice() {
        val words = Words()
        words.add("noox")
        words.add("noox")
        assertEquals(1, words.items.size)
        assertEquals(2, words.items[0].timesItAppears)
        assertEquals(0, words.items[0].position)
    }

    @Test
    fun words_addTwoDiferentsWords() {
        val words = Words()
        words.add("noox")
        words.add("dyss")
        assertEquals(2, words.items.size)
        assertEquals(1, words.items[0].timesItAppears)
        assertEquals(0, words.items[0].position)
        assertEquals(1, words.items[1].timesItAppears)
        assertEquals(1, words.items[1].position)
    }
}
