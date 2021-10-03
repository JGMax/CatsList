package gortea.jgmax.cats.catslist.repository

import gortea.jgmax.cats.catslist.data.model.CatModel
import io.reactivex.Single

interface ListRepository {
    fun getList(limit: Int): Single<List<CatModel>>
}
