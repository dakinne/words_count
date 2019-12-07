package com.noox.wordscount.common.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val appModule = module {

    single { androidContext().contentResolver }
}