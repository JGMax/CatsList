package gortea.jgmax.catslist.ui.presenters

import gortea.jgmax.catslist.data.local.cats.constants.CATS_PAGE_LIMIT

interface CatsListPresenterLocal {
    fun fetchCatsList(limit: Int = CATS_PAGE_LIMIT)
}