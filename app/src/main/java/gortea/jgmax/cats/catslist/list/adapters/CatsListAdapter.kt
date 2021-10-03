package gortea.jgmax.cats.catslist.list.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import gortea.jgmax.cats.R
import gortea.jgmax.cats.catslist.data.model.CatModel
import gortea.jgmax.cats.catslist.list.adapters.delegate.ItemClickDelegate
import gortea.jgmax.cats.catslist.list.adapters.delegate.LoadingClickDelegate
import gortea.jgmax.cats.catslist.list.adapters.holders.CatsItemViewHolder
import gortea.jgmax.cats.catslist.list.adapters.holders.LoadingPresenter
import gortea.jgmax.cats.catslist.list.adapters.holders.LoadingViewHolder
import gortea.jgmax.cats.catslist.list.adapters.viewtypes.ViewTypes
import gortea.jgmax.cats.app.state.LoadingState
import gortea.jgmax.cats.databinding.CatsListItemBinding
import gortea.jgmax.cats.databinding.CatsListLoadingBinding

class CatsListAdapter(
    private val loadingOffset: Int = 0,
    private val onLoad: (() -> Unit)? = null
) : ListAdapter<CatModel?, RecyclerView.ViewHolder>(comparator) {
    private var isLoading = false
    private var itemClickDelegate: ItemClickDelegate? = null
    private var loadingClickDelegate: LoadingClickDelegate? = null
    private val loadingPresenter = LoadingPresenter()

    fun attachItemClickDelegate(clickDelegate: ItemClickDelegate) {
        this.itemClickDelegate = clickDelegate
    }

    fun attachLoadingClickDelegate(clickDelegate: LoadingClickDelegate) {
        this.loadingClickDelegate = clickDelegate
    }

    fun detachDelegates() {
        itemClickDelegate = null
        loadingClickDelegate = null
    }

    fun detachLoadingHolder() {
        loadingPresenter.detachLoadingHolder()
    }

    fun loadingFinished(withError: Boolean = false) {
        isLoading = withError
        if (withError) {
            loadingPresenter.setLoadingState(LoadingState.Failed(R.string.unknown_error))
        } else {
            loadingPresenter.setLoadingState(LoadingState.Success)
        }
    }

    fun loadingStarted() {
        isLoading = true
        loadingPresenter.setLoadingState(LoadingState.Loading)
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
                LoadingViewHolder(binding, loadingClickDelegate)
            }
            else -> {
                val binding = CatsListItemBinding.inflate(inflater, parent, false)
                CatsItemViewHolder(binding, itemClickDelegate)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CatsItemViewHolder -> holder.bind(getItem(position) ?: return)
            is LoadingViewHolder -> holder.bind(loadingPresenter)
        }
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        val pos = holder.adapterPosition
        val limit = itemCount - loadingOffset - 1
        if (pos >= limit && !isLoading) {
            onLoad?.invoke()
        }
    }

    private companion object {
        private val comparator = object : DiffUtil.ItemCallback<CatModel?>() {
            override fun areItemsTheSame(oldItem: CatModel, newItem: CatModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CatModel, newItem: CatModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}