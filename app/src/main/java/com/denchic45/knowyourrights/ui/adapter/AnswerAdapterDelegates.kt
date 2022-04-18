package com.denchic45.knowyourrights.ui.adapter

import android.os.Handler
import android.os.Looper
import android.view.ViewGroup
import com.denchic45.knowyourrights.databinding.ItemAnswerBinding
import com.denchic45.knowyourrights.domain.model.PassedQuestion
import com.denchic45.knowyourrights.domain.model.Question
import com.denchic45.knowyourrights.ui.model.MultiChoiceItem
import com.denchic45.knowyourrights.ui.model.SingleChoiceItem
import com.denchic45.knowyourrights.utils.viewBinding
import com.denchic45.widget.extendedAdapter.DelegationAdapterExtended
import com.denchic45.widget.extendedAdapter.ListItemAdapterDelegate
import com.denchic45.widget.extendedAdapter.adapter

class AnswerAdapterDelegate :
    ListItemAdapterDelegate<PassedQuestion, AnswerAdapterDelegate.AnswerHolder>() {

    class AnswerHolder(itemAnswerBinding: ItemAnswerBinding) :
        BaseViewHolder<PassedQuestion, ItemAnswerBinding>(itemAnswerBinding) {
        override fun onBind(item: PassedQuestion) {
            with(binding) {
                tvQuestionName.text = item.question.title
                val adapter: DelegationAdapterExtended = adapter {
                    delegates(SingleChoiceAdapterDelegate(), MultiChoiceAdapterDelegate())
                }
                rvAnswers.adapter = adapter

                when (item.answer) {
                    is Question.Answer.SingleAnswer -> {
                        adapter.submit((item.question.choice as Question.Choice.SingleChoice).answers
                            .map { someAnswer ->
                                SingleChoiceItem(
                                    answer = someAnswer,
                                    isChecked = item.answer.value == someAnswer
                                )
                            })
                        Handler(Looper.getMainLooper()).post {
                            adapter.notifyItemChanged(
                                item.question.choice.answers.indexOf(
                                    item.question.choice.correctAnswer.value
                                ), SingleChoiceAdapterDelegate.PAYLOAD_TRUE
                            )
                            if (item.question.choice.correctAnswer.value != item.answer.value) {
                                adapter.notifyItemChanged(
                                    item.question.choice.answers.indexOf(
                                        item.answer.value
                                    ), SingleChoiceAdapterDelegate.PAYLOAD_FALSE
                                )
                            }
                        }
                    }
                    is Question.Answer.MultiAnswer -> {
                        adapter.submit((item.question.choice as Question.Choice.MultiChoice).answers
                            .map { someAnswer ->
                                MultiChoiceItem(
                                    answer = someAnswer,
                                    isChecked = item.answer.value.contains(someAnswer)
                                )
                            })

                        Handler(Looper.getMainLooper()).post {
                            item.question.choice.getCorrectAndWrongAnswerPositions(
                                item.answer
                            ).apply {
                                first.forEach {
                                    adapter.notifyItemChanged(
                                        it,
                                        MultiChoiceAdapterDelegate.PAYLOAD_TRUE
                                    )
                                }
                                second.forEach {
                                    adapter.notifyItemChanged(
                                        it,
                                        MultiChoiceAdapterDelegate.PAYLOAD_FALSE
                                    )
                                }
                            }
                        }
                    }
                    is Question.Answer.EnterAnswer -> {}
                }


            }
        }

    }

    override fun isForViewType(item: Any): Boolean {
        return item is PassedQuestion
    }

    override fun onBindViewHolder(item: PassedQuestion, holder: AnswerHolder) {
        holder.onBind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup): AnswerHolder {
        return AnswerHolder(parent.viewBinding(ItemAnswerBinding::inflate))
    }
}