package com.noox.wordscount.words.domain

import android.net.Uri
import com.noox.wordscount.common.Result
import com.noox.wordscount.words.data.WordsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class LoadWords(private val repository: WordsRepository) {

    suspend operator fun invoke(uri: Uri): Flow<Result<String>> {
        return withContext(Dispatchers.IO) {
            repository.loadWords(uri)
        }
    }

}
