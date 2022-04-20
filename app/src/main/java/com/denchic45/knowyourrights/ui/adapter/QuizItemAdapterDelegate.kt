package com.denchic45.knowyourrights.ui.adapter

import android.content.res.ColorStateList
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.ImageViewCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.denchic45.knowyourrights.R
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

                tvQuizTitleQuestionsCount.text = itemView.context.resources.getQuantityString(
                    R.plurals.question,
                    item.questionsCount,
                    item.questionsCount
                )

                tvQuizResult.text = if (item.yourMaxResult != 0) {
                    ivQuizResult.visibility = View.VISIBLE
                    ImageViewCompat.setImageTintList(
                        ivQuizResult,
                        ColorStateList.valueOf(itemView.context.getColor(R.color.amber_600))
                    )
                    "Ваш результат: ${item.yourMaxResult}/${item.questionsCount}"
                    "Ваш результат: ${item.yourMaxResult}/${item.questionsCount}"
                } else {
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