package com.denchic45.knowyourrights.ui.quizPlayer.finish

import androidx.lifecycle.viewModelScope
import com.denchic45.knowyourrights.domain.useCase.FindQuizResultByQuizUseCase
import com.denchic45.knowyourrights.ui.base.BaseViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject
import javax.inject.Named

class FinishViewModel @Inject constructor(
    @Named(FinishFragment.QUIZ_ID)
    quizId: String,
    private val findQuizResultByQuizUseCase: FindQuizResultByQuizUseCase
) : BaseViewModel() {


    val result = flow { emit(findQuizResultByQuizUseCase(quizId)) }
        .filterNotNull()
        .shareIn(
            viewModelScope,
            SharingStarted.Lazily,
            replay = 1
        )
}