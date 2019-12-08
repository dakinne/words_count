package com.noox.wordscount.words.ui

import com.noox.wordscount.words.domain.model.Word

data class Words(val items: List<Word>, val lastAction: ActionType = ActionType.None)

sealed class ActionType {
    object None: ActionType()
    data class Add(val word: Word): ActionType()
    data class Update(val word: Word): ActionType()
    object Clear: ActionType()
    object Sort: ActionType()
}