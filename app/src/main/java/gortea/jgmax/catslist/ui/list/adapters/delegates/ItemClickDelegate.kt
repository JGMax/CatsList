package gortea.jgmax.catslist.ui.list.adapters.delegates

import gortea.jgmax.catslist.data.local.cats.model.CatsListItem

fun interface ItemClickDelegate {
    fun onItemSelected(item: CatsListItem)
}