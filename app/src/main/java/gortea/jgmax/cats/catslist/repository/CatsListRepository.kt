package gortea.jgmax.cats.catslist.repository

import gortea.jgmax.cats.catslist.data.remote.CatsApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CatsListRepository @Inject constructor(
    private val catsApi: CatsApi
) : ListRepository {
    override fun getList(limit: Int) = catsApi.getCatsList(limit)
        .observeOn(Schedulers.io())
        .subscribeOn(AndroidSchedulers.mainThread())
}