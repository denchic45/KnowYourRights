package com.denchic45.knowyourrights.ui.model

import com.denchic45.knowyourrights.domain.model.DomainModel

interface UiModel:DomainModel {
    override val id: String
}