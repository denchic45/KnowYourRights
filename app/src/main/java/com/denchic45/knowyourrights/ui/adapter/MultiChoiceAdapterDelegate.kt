package com.denchic45.knowyourrights.ui.adapter

import android.content.res.ColorStateList
import android.view.ViewGroup
import com.denchic45.knowyourrights.R
import com.denchic45.knowyourrights.databinding.ItemMultiChoiceBinding
import com.denchic45.knowyourrights.ui.model.MultiChoiceItem
import com.denchic45.knowyourrights.utils.colors
import com.denchic45.knowyourrights.utils.viewBinding
import com.denchic45.widget.extendedAdapter.ListItemAdapterDelegate

class MultiChoiceAdapterDelegate(private val isEnabled: Boolean) :
    ListItemAdapterDelegate<MultiChoiceItem, MultiChoiceAdapterDelegate.MultiChoiceHolder>() {

    class MultiChoiceHolder(
        itemMultiChoiceBinding: ItemMultiChoiceBinding,
        private val enabled: Boolean
    ) :
        BaseViewHolder<MultiChoiceItem, ItemMultiChoiceBinding>(itemMultiChoiceBinding) {

        override fun onBind(item: MultiChoiceItem) {
            with(binding.root) {
                text = item.answer
                isChecked = item.isChecked
                //    isEnabled = enabled
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

        fun changeColor(colorId: Int) {
            binding.root.buttonTintList = ColorStateList(
                arrayOf(
                    intArrayOf(android.R.attr.state_enabled)
                ), intArrayOf(itemView.context.colors(colorId))
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
                holder.changeColor(R.color.green_600)
            }
            PAYLOAD_FALSE -> {
                holder.changeColor(R.color.red_600)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup): MultiChoiceHolder {
        return MultiChoiceHolder(parent.viewBinding(ItemMultiChoiceBinding::inflate), isEnabled)
    }

    companion object {
        const val PAYLOAD_TRUE = "PAYLOAD_TRUE"
        const val PAYLOAD_FALSE = "PAYLOAD_FALSE"
    }
}