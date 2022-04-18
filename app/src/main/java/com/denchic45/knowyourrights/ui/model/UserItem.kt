package com.denchic45.knowyourrights.ui.model

data class UserItem(
    override val id: String,
    val title: String,
    val photoUrl: String,
    val subtitle: String? = null
): UiModel