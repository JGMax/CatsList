package gortea.jgmax.catslist.ui.presenters

import gortea.jgmax.catslist.data.local.cats.favourites.room.dao.CatsListDao

interface CatsListPresenterLocal : CatsListPresenter {
    fun fetchCatsList(catsDao: CatsListDao)
}