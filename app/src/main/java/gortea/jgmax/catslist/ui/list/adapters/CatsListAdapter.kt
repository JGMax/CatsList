package gortea.jgmax.catslist.ui.list.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import gortea.jgmax.catslist.data.local.cats.model.CatsListLocalItem
import gortea.jgmax.catslist.databinding.CatsListItemBinding
import gortea.jgmax.catslist.databinding.CatsListLoadingBinding
import gortea.jgmax.catslist.ui.list.adapters.holders.CatsItemViewHolder
import gortea.jgmax.catslist.ui.list.adapters.holders.LoadingViewHolder

class CatsListAdapter(
    private val loadingOffset: Int = 0,
    private val onLoad: (() -> Unit)? = null
) : ListAdapter<CatsListLocalItem?, RecyclerView.ViewHolder>(comparator) {
    private var isLoading = false
    private var updateWithError = false

    fun loadingFinished(withError: Boolean = false) {
        isLoading = false
        updateWithError = withError
    }

    fun loadingStarted() {
        isLoading = true
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            null -> ViewTypes.LOADING.value
            else -> ViewTypes.ITEM.value
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ViewTypes.LOADING.value -> {
                val binding = CatsListLoadingBinding.inflate(inflater, parent, false)
                LoadingViewHolder(binding.root)
            }
            else -> {
                val binding = CatsListItemBinding.inflate(inflater, parent, false)
                CatsItemViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CatsItemViewHolder -> holder.bind(getItem(position) ?: return)
        }
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        val pos = holder.adapterPosition
        val limit = itemCount - loadingOffset - 1
        if (pos >= limit && !isLoading && !updateWithError) {
            onLoad?.invoke()
        } else if (pos < limit) {
            updateWithError = false
        }
    }

    private companion object {
        private val comparator = object : DiffUtil.ItemCallback<CatsListLocalItem?>() {
            override fun areItemsTheSame(oldItem: CatsListLocalItem, newItem: CatsListLocalItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CatsListLocalItem, newItem: CatsListLocalItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}