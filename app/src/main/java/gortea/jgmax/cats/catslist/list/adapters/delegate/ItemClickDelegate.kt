package gortea.jgmax.cats.catslist.list.adapters.delegate

import gortea.jgmax.cats.catslist.data.model.CatModel

interface ItemClickDelegate {
    fun onItemSelected(item: CatModel, position: Int)
}
