package com.denchic45.knowyourrights.ui.model

data class QuizResultItem(
    override val id: String,
    val name: String,
    val questionsCount: Int,
    val yourMaxResult: Int
) : UiModel