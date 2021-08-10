package gortea.jgmax.catslist.ui.list.adapters.holders

import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import gortea.jgmax.catslist.R
import gortea.jgmax.catslist.data.local.cats.model.CatsListLocalItem
import gortea.jgmax.catslist.data.remote.cats.model.CatsListItem
import gortea.jgmax.catslist.databinding.CatsListItemBinding

class CatsItemViewHolder(
    private val binding: CatsListItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: CatsListLocalItem) {
        with(binding) {
            Glide
                .with(root)
                .load(item.url)
                .placeholder(R.drawable.ic_cats_placeholder)
                .centerCrop()
                .into(catImageView)
            catCardView.setOnClickListener {
                Toast.makeText(root.context, "Item clicked", Toast.LENGTH_SHORT).show()
            }
        }
    }
}