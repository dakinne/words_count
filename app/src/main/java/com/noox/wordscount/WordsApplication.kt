package com.noox.wordscount

import android.app.Application
import com.noox.wordscount.common.di.appModule
import com.noox.wordscount.words.di.wordsModule
import org.koin.android.ext.android.startKoin

class WordsApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin()
    }

    private fun initKoin() {
        val modules = listOf(
            appModule,
            wordsModule
        )
        startKoin(this, modules)
    }
}