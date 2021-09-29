package gortea.jgmax.cats.catslist.list.adapters.holders

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import gortea.jgmax.cats.catslist.data.model.CatModel
import gortea.jgmax.cats.catslist.list.adapters.delegate.ItemClickDelegate
import gortea.jgmax.cats.databinding.CatsListItemBinding

class CatsItemViewHolder(
    private val binding: CatsListItemBinding,
    private val clickDelegate: ItemClickDelegate?
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: CatModel) {
        with(binding) {
            Glide
                .with(root)
                .load(item.url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(catImageView)
            catCardView.setOnClickListener {
                clickDelegate?.onItemSelected(item)
            }
        }
    }
}