package com.noox.wordscount

import com.noox.wordscount.words.ui.Words
import com.noox.wordscount.words.ui.Words.SortType.*
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

    @Test
    fun words_sortByAlphabetical() {
        val words = Words()
        words.add("bb")
        words.add("cc")
        words.add("bb")
        words.add("aa")

        words.sortBy(Alphabetical)

        assertEquals("aa", words.items[0].text)
        assertEquals("bb", words.items[1].text)
        assertEquals("cc", words.items[2].text)
    }

    @Test
    fun words_sortByPosition() {
        val words = Words()
        words.add("bb")
        words.add("cc")
        words.add("bb")
        words.add("aa")

        words.sortBy(Position)

        assertEquals("bb", words.items[0].text)
        assertEquals("cc", words.items[1].text)
        assertEquals("aa", words.items[2].text)
    }

    @Test
    fun words_sortByAppearance() {
        val words = Words()
        words.add("bb")
        words.add("cc")
        words.add("bb")
        words.add("aa")
        words.add("cc")
        words.add("cc")

        words.sortBy(Appearance)

        assertEquals("cc", words.items[0].text)
        assertEquals("bb", words.items[1].text)
        assertEquals("aa", words.items[2].text)
    }
}
