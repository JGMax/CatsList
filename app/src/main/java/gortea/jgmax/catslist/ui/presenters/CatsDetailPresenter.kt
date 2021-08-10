package gortea.jgmax.catslist.ui.presenters

import gortea.jgmax.catslist.data.local.cats.model.CatsListItem

interface CatsDetailPresenter {
    fun addToFavourites(item: CatsListItem)
    fun download(url: String)
}