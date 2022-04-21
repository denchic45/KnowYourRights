package com.denchic45.knowyourrights.ui.adapter

import android.content.res.ColorStateList
import android.view.ViewGroup
import com.denchic45.knowyourrights.R
import com.denchic45.knowyourrights.databinding.ItemSingleChoiceBinding
import com.denchic45.knowyourrights.ui.model.SingleChoiceItem
import com.denchic45.knowyourrights.utils.colors
import com.denchic45.knowyourrights.utils.viewBinding
import com.denchic45.widget.extendedAdapter.ListItemAdapterDelegate


class SingleChoiceAdapterDelegate(private val isEnabled: Boolean) :
    ListItemAdapterDelegate<SingleChoiceItem, SingleChoiceAdapterDelegate.SingleChoiceHolder>() {

    class SingleChoiceHolder(
        itemSingleChoiceBinding: ItemSingleChoiceBinding,
    ) :
        BaseViewHolder<SingleChoiceItem, ItemSingleChoiceBinding>(itemSingleChoiceBinding) {
        override fun onBind(item: SingleChoiceItem) {
            with(binding.root) {
                text = item.answer
                isChecked = item.isChecked
//                isEnabled = enabled
                item.isCorrect?.let {
                    changeColor(
                        if (it)
                            R.color.green_600
                        else
                            R.color.red_600
                    )
                }
            }
        }

        fun unselectRadio() {
            binding.root.isChecked = false
        }

        fun changeColor(colorId: Int) {
            binding.root.buttonTintList = ColorStateList(
                arrayOf(
                    intArrayOf(
                        android.R.attr.state_enabled
                    )
                ), intArrayOf(
                    itemView.context.colors(colorId)
                )
            )
        }
    }

    override fun isForViewType(item: Any): Boolean {
        return item is SingleChoiceItem
    }

    override fun onBindViewHolder(item: SingleChoiceItem, holder: SingleChoiceHolder) {
        holder.onBind(item)
    }

    override fun onBindViewHolder(
        item: SingleChoiceItem,
        holder: SingleChoiceHolder,
        payload: Any
    ) {
        super.onBindViewHolder(item, holder, payload)
        when (payload) {
            PAYLOAD_UNSELECT_RADIO -> {
                holder.unselectRadio()
            }
            PAYLOAD_TRUE -> {
                holder.changeColor(R.color.green_600)
            }
            PAYLOAD_FALSE -> {
                holder.changeColor(R.color.red_600)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup): SingleChoiceHolder {
        return SingleChoiceHolder(parent.viewBinding(ItemSingleChoiceBinding::inflate))
    }

    companion object {
        const val PAYLOAD_UNSELECT_RADIO = "PAYLOAD_UNSELECT_RADIO"
        const val PAYLOAD_TRUE = "PAYLOAD_TRUE"
        const val PAYLOAD_FALSE = "PAYLOAD_FALSE"
    }
}