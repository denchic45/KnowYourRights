package com.denchic45.knowyourrights.ui.quizPlayer.multiChoice

import com.denchic45.knowyourrights.domain.model.Question
import com.denchic45.knowyourrights.ui.base.BaseViewModel
import com.denchic45.knowyourrights.ui.model.MultiChoiceItem
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class MultiChoiceViewModel @Inject constructor() : BaseViewModel() {

    val answers = MutableStateFlow<List<MultiChoiceItem>>(emptyList())
    val selectedItems = mutableSetOf<Int>()

    fun onQuestionLoad(question: Question) {
        answers.value =
            (question.choice as Question.Choice.MultiChoice).answers.map { MultiChoiceItem(it) }
    }

    fun onAnswerItemSelect(position: Int) {
        if (selectedItems.contains(position)) {
            selectedItems.remove(position)
        } else {
            selectedItems.add(position)
        }
    }

}