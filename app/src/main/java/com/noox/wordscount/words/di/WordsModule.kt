package com.noox.wordscount.words.di

import com.noox.wordscount.words.data.WordsDataSource
import com.noox.wordscount.words.data.WordsRepository
import com.noox.wordscount.words.domain.LoadWords
import com.noox.wordscount.words.ui.WordsViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val wordsModule = module {

    viewModel { WordsViewModel(loadWords = get()) }

    single { LoadWords(repository = get()) }
    single { WordsRepository(dataSource = get()) }
    single { WordsDataSource(contentResolver = get()) }
}