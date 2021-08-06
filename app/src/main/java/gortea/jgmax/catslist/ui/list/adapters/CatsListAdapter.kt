package gortea.jgmax.catslist.ui.list.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import gortea.jgmax.catslist.data.remote.cats.model.CatsListItem
import gortea.jgmax.catslist.databinding.CatsListItemBinding
import gortea.jgmax.catslist.ui.list.adapters.holders.CatsItemViewHolder

class CatsListAdapter : ListAdapter<CatsListItem, CatsItemViewHolder>(comparator) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatsItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CatsListItemBinding.inflate(inflater, parent, false)
        return CatsItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CatsItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}