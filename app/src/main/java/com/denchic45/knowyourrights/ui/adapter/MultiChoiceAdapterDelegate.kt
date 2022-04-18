package com.denchic45.knowyourrights.ui.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.ViewGroup
import com.denchic45.knowyourrights.databinding.ItemMultiChoiceBinding
import com.denchic45.knowyourrights.ui.model.MultiChoiceItem
import com.denchic45.knowyourrights.utils.viewBinding
import com.denchic45.widget.extendedAdapter.ListItemAdapterDelegate

class MultiChoiceAdapterDelegate :
    ListItemAdapterDelegate<MultiChoiceItem, MultiChoiceAdapterDelegate.MultiChoiceHolder>() {

    class MultiChoiceHolder(itemMultiChoiceBinding: ItemMultiChoiceBinding) :
        BaseViewHolder<MultiChoiceItem, ItemMultiChoiceBinding>(itemMultiChoiceBinding) {

        override fun onBind(item: MultiChoiceItem) {
            with(binding.root) {
                text = item.answer
                isChecked = item.isChecked
            }
        }

        fun changeColor(color: Int) {
            binding.root.buttonTintList = ColorStateList(
                arrayOf(
                    intArrayOf(
                        android.R.attr.state_enabled
                    )
                ), intArrayOf(
                    color
                )
            )
        }
    }

    override fun isForViewType(item: Any): Boolean {
        return item is MultiChoiceItem
    }

    override fun onBindViewHolder(item: MultiChoiceItem, holder: MultiChoiceHolder) {
        holder.onBind(item)
    }


    override fun onBindViewHolder(item: MultiChoiceItem, holder: MultiChoiceHolder, payload: Any) {
        super.onBindViewHolder(item, holder, payload)
        when (payload) {
            PAYLOAD_TRUE -> {
                holder.changeColor(Color.GREEN)
            }
            PAYLOAD_FALSE -> {
                holder.changeColor(Color.RED)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup): MultiChoiceHolder {
        return MultiChoiceHolder(parent.viewBinding(ItemMultiChoiceBinding::inflate))
    }

    companion object {
        const val PAYLOAD_TRUE = "PAYLOAD_TRUE"
        const val PAYLOAD_FALSE = "PAYLOAD_FALSE"
    }
}