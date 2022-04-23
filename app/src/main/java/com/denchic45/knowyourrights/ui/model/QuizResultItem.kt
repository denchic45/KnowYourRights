package com.denchic45.knowyourrights.ui.model

import java.time.LocalDateTime

data class QuizResultItem(
    override val id: String,
    val name: String,
    val questionsCount: Int,
    val maxResult: Int,
    val timestamp: LocalDateTime
) : UiModel