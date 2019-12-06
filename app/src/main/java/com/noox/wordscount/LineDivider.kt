package com.noox.wordscount

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration

class LineDivider(context: Context) : DividerItemDecoration(context, VERTICAL) {

    init {
        val drawable = ContextCompat.getDrawable(context, R.drawable.line_divider)
        if (drawable != null) setDrawable(drawable)
    }
}
