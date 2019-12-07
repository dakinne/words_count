package com.noox.wordscount.common

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed class Result<out T : Any> {

    data class Success<out T : Any>(val data: T) : Result<T>()
    @Parcelize
    data class Error(val type: Type = Type.Generic) : Result<Nothing>(), Parcelable {
        enum class Type {
            Generic
        }
    }

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[type=$type]"
        }
    }
}
