package com.denchic45.knowyourrights.ui.model

data class SingleChoiceItem(
    val answer: String,
    val isChecked: Boolean = false,
    override val id: String = "",
    val isCorrect: Boolean?= null
) : UiModel

data class MultiChoiceItem(
    val answer: String,
    val isChecked: Boolean = false,
    override val id: String = "",
    val isCorrect: Boolean?= null
) : UiModel

data class EnterChoiceItem(
    override val id: String = "",
    val enteredAnswer: String,
    val isCorrect: Boolean? = null,
    val correctAnswer: String? = null
) : UiModel