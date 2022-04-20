package com.denchic45.knowyourrights.ui.quizzes

import androidx.lifecycle.viewModelScope
import com.denchic45.knowyourrights.domain.useCase.FindQuizzesUseCase
import com.denchic45.knowyourrights.ui.base.BaseViewModel
import com.denchic45.knowyourrights.ui.model.QuizItem
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class QuizzesViewModel @Inject constructor(
    findQuizzesUseCase: FindQuizzesUseCase
) : BaseViewModel() {

    fun onQuizItemClick(pos: Int) {
        navigateTo(
            QuizzesFragmentDirections.actionNavigationHomeToQuizDetailsFragment(
                quizzes.value[pos].id
            )
        )
    }

    val quizzes: StateFlow<List<QuizItem>> =
        findQuizzesUseCase().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
}