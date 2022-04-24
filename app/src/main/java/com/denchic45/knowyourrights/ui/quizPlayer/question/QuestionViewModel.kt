package com.denchic45.knowyourrights.ui.quizPlayer.question

import androidx.lifecycle.viewModelScope
import com.denchic45.knowyourrights.ui.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class QuestionViewModel @Inject constructor() : BaseViewModel() {

    val timeLeft = (400 downTo 0)
        .asSequence()
        .asFlow()
        .onEach {
            delay(50)
        }
        .onCompletion {
            showDialog("Время вышло", "")
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 100)
}