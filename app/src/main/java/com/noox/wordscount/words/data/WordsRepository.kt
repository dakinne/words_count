package com.noox.wordscount.words.data

import android.net.Uri
import com.noox.wordscount.common.Result
import kotlinx.coroutines.flow.Flow

class WordsRepository(private val dataSource: WordsDataSource) {

    suspend fun loadWords(uri: Uri): Flow<Result<String>> = dataSource.loadWords(uri)

}
