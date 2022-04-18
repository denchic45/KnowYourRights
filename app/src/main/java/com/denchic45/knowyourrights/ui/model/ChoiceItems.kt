package com.denchic45.knowyourrights.ui.model

data class SingleChoiceItem(
    val answer: String,
    val isChecked: Boolean = false,
    override val id: String = ""
) : UiModel

data class MultiChoiceItem(
    val answer: String,
    val isChecked: Boolean = false,
    override val id: String = ""
) : UiModel

data class EnterChoiceItem(override val id: String = "") : UiModel