package com.denchic45.knowyourrights.ui.quizPlayer.singleChoice

import com.denchic45.knowyourrights.domain.model.Question
import com.denchic45.knowyourrights.ui.base.BaseViewModel
import com.denchic45.knowyourrights.ui.model.SingleChoiceItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class SingleChoiceViewModel @Inject constructor() : BaseViewModel() {

    val answers = MutableStateFlow<List<SingleChoiceItem>>(emptyList())

    fun onQuestionLoad(question: Question) {
        answers.value = (question.choice as Question.Choice.SingleChoice).answers.map { SingleChoiceItem(it) }
    }

    fun onAnswerItemSelect(position: Int) {
        answers.update {
            it.mapIndexed {i, item-> item.copy(isChecked = i == position)}
//            it.toMutableList().apply {
//                set(position, it[position].copy(isChecked = true))
//            }
        }
    }

}