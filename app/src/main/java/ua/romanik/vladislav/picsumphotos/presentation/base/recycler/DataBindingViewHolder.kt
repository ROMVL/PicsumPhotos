package ua.romanik.vladislav.picsumphotos.presentation.base.recycler

import androidx.databinding.ViewDataBinding

abstract class DataBindingViewHolder<T>(
    private val dataBinding: ViewDataBinding
) : androidx.recyclerview.widget.RecyclerView.ViewHolder(dataBinding.root) {

    abstract fun onBind(data: T)

}