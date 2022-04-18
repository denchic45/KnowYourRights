package com.denchic45.knowyourrights.ui.result

import androidx.lifecycle.viewModelScope
import com.denchic45.knowyourrights.domain.useCase.FindQuizResultUseCase
import com.denchic45.knowyourrights.ui.base.BaseViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

class QuizResultViewModel @Inject constructor(
    @Named(QuizResultFragment.QUIZ_RESULT_ID)
    quizResultId: String,
    findQuizResultUseCase: FindQuizResultUseCase
) : BaseViewModel() {

    val result = flow { emit(findQuizResultUseCase(quizResultId)) }
        .shareIn(
            viewModelScope,
            SharingStarted.Lazily
        )

    init {
        viewModelScope.launch {
            result.collect {
                setTitle(it.quizItem.name)
            }
        }
    }
}