package com.noox.wordscount.words.data

import android.content.ContentResolver
import android.net.Uri
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.noox.wordscount.common.Result
import java.io.BufferedReader

class WordsDataSource(private val contentResolver: ContentResolver) {

    private val byWord = "\\s+".toRegex()

    suspend fun loadWords(uri: Uri) : Flow<Result<String>> = flow {
        val inputStream = contentResolver.openInputStream(uri)
        val reader = inputStream?.let { BufferedReader(it.reader()) }

        if (reader == null) {
            emit(Result.Error())
        } else {
            reader.useLines { lines ->
                lines.forEach { line ->
                    line.split(byWord).forEach { word ->
                        emit(Result.Success(word))
                    }
                }
            }
        }
    }
}
