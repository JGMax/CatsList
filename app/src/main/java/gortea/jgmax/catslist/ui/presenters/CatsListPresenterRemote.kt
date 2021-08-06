package gortea.jgmax.catslist.ui.presenters

import gortea.jgmax.catslist.data.local.cats.constants.CATS_PAGE_LIMIT
import gortea.jgmax.catslist.data.remote.cats.api.CatsApi

interface CatsListPresenterRemote : CatsListPresenter {
    fun fetchCatsList(catsApi: CatsApi, limit: Int = CATS_PAGE_LIMIT)
}