package com.denchic45.knowyourrights.ui.adapter

import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.denchic45.knowyourrights.databinding.ItemQuizCardBinding
import com.denchic45.knowyourrights.ui.model.QuizItem
import com.denchic45.knowyourrights.utils.viewBinding
import com.denchic45.widget.extendedAdapter.ListItemAdapterDelegate

class QuizItemAdapterDelegate :
    ListItemAdapterDelegate<QuizItem, QuizItemAdapterDelegate.QuizItemHolder>() {

    override fun isForViewType(item: Any): Boolean = item is QuizItem

    override fun onBindViewHolder(item: QuizItem, holder: QuizItemHolder) {
        holder.onBind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup): QuizItemHolder {
        return QuizItemHolder(parent.viewBinding(ItemQuizCardBinding::inflate))
    }

    class QuizItemHolder(itemQuizCardBinding: ItemQuizCardBinding) :
        BaseViewHolder<QuizItem, ItemQuizCardBinding>(itemQuizCardBinding) {

        override fun onBind(item: QuizItem) {
            with(binding) {

                tvQuizName.text = item.name

                tvQuizTitleQuestionsCount.text = "${item.questionsCount} вопросов"

                tvQuizResult.text = item.yourMaxResult?.let {
                    ivQuizResult.visibility = View.VISIBLE
                    "Ваш результат: ${item.yourMaxResult}/${item.questionsCount}"
                } ?: run {
                    ivQuizResult.visibility = View.GONE
                    "Вы еще не проходили этот тест"
                }

                Glide.with(itemView)
                    .load(item.imageUrl)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade(100))
                    .into(ivQuiz)
            }
        }
    }
}