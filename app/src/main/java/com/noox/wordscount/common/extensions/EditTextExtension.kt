package com.noox.wordscount.common.extensions

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

fun EditText.onAfterChange(block: (editable: Editable?) -> Unit) {
    this.addTextChangedListener(object: TextWatcher {
        override fun afterTextChanged(s: Editable?) { block(s) }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}