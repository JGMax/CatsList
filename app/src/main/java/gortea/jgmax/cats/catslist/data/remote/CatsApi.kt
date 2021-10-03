package gortea.jgmax.cats.catslist.data.remote

import gortea.jgmax.cats.BuildConfig
import gortea.jgmax.cats.catslist.data.model.CatModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CatsApi {
    @Headers("x-api-key: ${BuildConfig.API_KEY}")
    @GET("./v1/images/search")
    fun getCatsList(@Query("limit") limit: Int): Single<List<CatModel>>
}
