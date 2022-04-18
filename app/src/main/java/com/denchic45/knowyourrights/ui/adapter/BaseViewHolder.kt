package com.denchic45.knowyourrights.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.denchic45.kts.ui.adapter.OnItemClickListener
import com.denchic45.kts.ui.adapter.OnItemLongClickListener

abstract class BaseViewHolder<T, VB : ViewBinding>(
    val binding: VB,
    val onItemClickListener: OnItemClickListener = OnItemClickListener { },
    val onItemLongClickListener: OnItemLongClickListener = OnItemLongClickListener { }
) : RecyclerView.ViewHolder(binding.root) {

    init {
        itemView.setOnClickListener {
            onItemClick()
        }
        itemView.setOnLongClickListener {
            onItemLongClick()
            true
        }
    }

    open fun onItemClick() {
        onItemClickListener.onItemClick(adapterPosition)
    }

    open fun onItemLongClick() {
        onItemLongClickListener.onLongItemClick(adapterPosition)
    }

    abstract fun onBind(item: T)

    open fun onBind(item: T, payload: Any) {
    }
}
