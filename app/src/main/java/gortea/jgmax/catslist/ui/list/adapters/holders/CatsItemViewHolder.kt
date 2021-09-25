package gortea.jgmax.catslist.ui.list.adapters.holders

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import gortea.jgmax.catslist.R
import gortea.jgmax.catslist.data.local.cats.model.CatsListItem
import gortea.jgmax.catslist.databinding.CatsListItemBinding
import gortea.jgmax.catslist.ui.delegates.ItemClickDelegate

class CatsItemViewHolder(
    private val binding: CatsListItemBinding,
    private val clickDelegate: ItemClickDelegate?
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: CatsListItem) {
        with(binding) {
            Glide
                .with(root)
                .load(item.url)
                .placeholder(R.drawable.ic_cats_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(catImageView)
            catCardView.setOnClickListener {
                clickDelegate?.onItemSelected(item)
            }
        }
    }
}