package com.denchic45.knowyourrights.ui.quizDetails

import androidx.lifecycle.viewModelScope
import com.denchic45.knowyourrights.domain.useCase.FindQuizUseCase
import com.denchic45.knowyourrights.ui.base.BaseViewModel
import com.denchic45.knowyourrights.ui.model.QuizItem
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

class QuizDetailsViewModel @Inject constructor(
    @Named(QuizDetailsFragment.QUIZ_ID) quizId: String,
    findQuizUseCase: FindQuizUseCase
) : BaseViewModel() {

    val quizDetails: SharedFlow<QuizItem> = flow { emit(findQuizUseCase.invoke(quizId)) }
        .shareIn(
            viewModelScope,
            SharingStarted.Lazily,
            replay = 1
        )

    val openQuizPlayer = MutableSharedFlow<String>()

    fun onPlayClick() {
        viewModelScope.launch {
            openQuizPlayer.emit(
                quizDetails.first().id
            )
        }
    }
}