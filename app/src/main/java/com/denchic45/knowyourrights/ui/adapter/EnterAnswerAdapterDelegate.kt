package com.denchic45.knowyourrights.ui.adapter

import android.view.ViewGroup
import com.denchic45.knowyourrights.databinding.ItemEnterAnswerBinding
import com.denchic45.knowyourrights.ui.model.EnterChoiceItem
import com.denchic45.knowyourrights.utils.viewBinding
import com.denchic45.widget.extendedAdapter.ListItemAdapterDelegate


class EnterAnswerAdapterDelegate :
    ListItemAdapterDelegate<EnterChoiceItem, EnterAnswerAdapterDelegate.EnterChoiceHolder>() {

    class EnterChoiceHolder(itemEnterAnswerBinding: ItemEnterAnswerBinding) :
        BaseViewHolder<EnterChoiceItem, ItemEnterAnswerBinding>(itemEnterAnswerBinding) {
        override fun onBind(item: EnterChoiceItem) {
            binding.et.setText(item.enteredAnswer)
        }

        fun setError(correctAnswer: String) {
            binding.til.error = correctAnswer
        }
    }

    override fun isForViewType(item: Any): Boolean {
        return item is EnterChoiceItem
    }

    override fun onBindViewHolder(item: EnterChoiceItem, holder: EnterChoiceHolder) {
        holder.onBind(item)
    }

    override fun onBindViewHolder(
        item: EnterChoiceItem,
        holder: EnterChoiceHolder,
        payload: Any
    ) {
        super.onBindViewHolder(item, holder, payload)
        when (payload as Payload) {
            is Payload.True -> {
            }
            is Payload.False -> {
                holder.setError((payload as Payload.False).correctAnswer)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup): EnterChoiceHolder {
        return EnterChoiceHolder(parent.viewBinding(ItemEnterAnswerBinding::inflate))
    }

    companion object {

        sealed class Payload {
            class False(val correctAnswer: String) : Payload()

            object True : Payload()
        }
    }
}