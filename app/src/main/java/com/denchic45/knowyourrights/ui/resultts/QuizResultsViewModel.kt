package com.denchic45.knowyourrights.ui.resultts

import androidx.lifecycle.viewModelScope
import com.denchic45.knowyourrights.domain.useCase.FindQuizResultsUseCase
import com.denchic45.knowyourrights.ui.base.BaseViewModel
import com.denchic45.knowyourrights.ui.model.QuizResultItem
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class QuizResultsViewModel @Inject constructor(
    findQuizResultsUseCase: FindQuizResultsUseCase
) : BaseViewModel() {

    val results: StateFlow<List<QuizResultItem>> = findQuizResultsUseCase().stateIn(
        viewModelScope,
        SharingStarted.Lazily, emptyList()
    )


    fun onResultItemClick(position: Int) {
        viewModelScope.launch {
            navigateTo(
                QuizResultsFragmentDirections.actionNavigationQuizResultsToQuizResultFragment(
                    results.first()[position].id
                )
            )
        }
    }
}