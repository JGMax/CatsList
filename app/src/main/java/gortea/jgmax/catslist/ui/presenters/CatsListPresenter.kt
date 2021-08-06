package gortea.jgmax.catslist.ui.presenters

import gortea.jgmax.catslist.data.remote.cats.model.CatsListItem

interface CatsListPresenter {
    fun onCatsItemSelected(catsItem: CatsListItem)
    fun getList(): List<CatsListItem>
}