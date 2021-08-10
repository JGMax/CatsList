package gortea.jgmax.catslist.ui.presenters

import gortea.jgmax.catslist.data.local.cats.model.CatsListLocalItem

interface CatsListPresenter {
    fun onCatsItemSelected(catsItem: CatsListLocalItem)
    fun getList(): List<CatsListLocalItem?>
}