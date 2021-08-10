package gortea.jgmax.catslist.ui.presenters

import gortea.jgmax.catslist.data.local.cats.model.CatsListItem

interface CatsListPresenter {
    fun onCatsItemSelected(catsItem: CatsListItem)
    fun getList(): List<CatsListItem?>
}