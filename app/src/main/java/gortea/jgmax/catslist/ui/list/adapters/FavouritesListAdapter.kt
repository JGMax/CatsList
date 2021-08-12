package gortea.jgmax.catslist.ui.list.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import gortea.jgmax.catslist.data.local.cats.model.CatsListItem
import gortea.jgmax.catslist.databinding.CatsListItemBinding
import gortea.jgmax.catslist.ui.delegates.ItemClickDelegate
import gortea.jgmax.catslist.ui.list.adapters.holders.CatsItemViewHolder

class FavouritesListAdapter : ListAdapter<CatsListItem, CatsItemViewHolder>(comparator) {
    private var clickDelegate: ItemClickDelegate? = null

    fun attachClickDelegate(clickDelegate: ItemClickDelegate) {
        this.clickDelegate = clickDelegate
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatsItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CatsListItemBinding.inflate(inflater, parent, false)
        return CatsItemViewHolder(binding, clickDelegate)
    }

    override fun onBindViewHolder(holder: CatsItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    private companion object {
        private val comparator = object : DiffUtil.ItemCallback<CatsListItem>() {
            override fun areItemsTheSame(oldItem: CatsListItem, newItem: CatsListItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CatsListItem, newItem: CatsListItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}