package com.denchic45.knowyourrights.ui.model

data class QuizItem(
    override val id: String,
    val name: String,
    val questionsCount: Int,
    val imageUrl: String,
    val maxResult: Int,
    val lectureUrl: String
) : UiModel