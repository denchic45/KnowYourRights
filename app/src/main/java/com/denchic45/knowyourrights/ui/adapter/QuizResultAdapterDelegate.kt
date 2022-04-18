package com.denchic45.knowyourrights.ui.adapter

import android.view.ViewGroup
import com.denchic45.knowyourrights.databinding.ItemQuizResultBinding
import com.denchic45.knowyourrights.ui.model.QuizResultItem
import com.denchic45.knowyourrights.utils.viewBinding
import com.denchic45.widget.extendedAdapter.ListItemAdapterDelegate

class QuizResultAdapterDelegate :
    ListItemAdapterDelegate<QuizResultItem, QuizResultAdapterDelegate.QuizResultHolder>() {

    class QuizResultHolder(itemQuizResultBinding: ItemQuizResultBinding) :
        BaseViewHolder<QuizResultItem, ItemQuizResultBinding>(itemQuizResultBinding) {
        override fun onBind(item: QuizResultItem) {
            with(binding) {
                tvResultTitle.text = item.name
                tvResultCount.text = "${item.yourMaxResult}/${item.questionsCount}"
                progressResult.progress = (item.yourMaxResult.toDouble() / item.questionsCount.toDouble() * 100).toInt()
            }
        }
    }

    override fun isForViewType(item: Any): Boolean {
        return item is QuizResultItem
    }

    override fun onBindViewHolder(item: QuizResultItem, holder: QuizResultHolder) {
        holder.onBind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup): QuizResultHolder {
        return QuizResultHolder(parent.viewBinding(ItemQuizResultBinding::inflate))
    }
}