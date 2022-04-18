package com.denchic45.knowyourrights.ui.model

data class QuizItem(
    override val id: String,
    val name: String,
    val questionsCount: Int,
    val imageUrl: String,
    val yourMaxResult: Int? = null,
    val lectureUrl:String
) : UiModel