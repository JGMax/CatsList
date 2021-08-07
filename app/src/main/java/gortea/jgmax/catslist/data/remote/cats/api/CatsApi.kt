package gortea.jgmax.catslist.data.remote.cats.api

import gortea.jgmax.catslist.data.local.constants.API_KEY
import gortea.jgmax.catslist.data.remote.cats.model.CatsListItem
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CatsApi {
    @Headers("x-api-key: $API_KEY")
    @GET("./v1/images/search")
    suspend fun getCatsList(@Query("limit") limit: Int): List<CatsListItem>
}