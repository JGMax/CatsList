package gortea.jgmax.catslist.data.remote.cats

import gortea.jgmax.catslist.data.local.keys.API_KEY
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CatsApi {
    @Headers("x-api-key: $API_KEY")
    @GET("./v1/images/search")
    fun getCatsList(@Query("limit") limit: Int) : Call<List<CatsListItem>>
}