package gortea.jgmax.catslist

import android.app.Application
import android.util.Log
import gortea.jgmax.catslist.data.local.keys.API_BASE_URL
import gortea.jgmax.catslist.data.remote.cats.CatsApi
import gortea.jgmax.catslist.data.remote.cats.CatsListItem
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CatsApp : Application() {
    lateinit var catsApi: CatsApi

    override fun onCreate() {
        super.onCreate()
        configureRetrofit()
    }

    private fun configureRetrofit() {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        catsApi = retrofit.create(CatsApi::class.java)
        catsApi.getCatsList(10).enqueue(object: Callback<List<CatsListItem>> {
            override fun onResponse(
                call: Call<List<CatsListItem>>,
                response: Response<List<CatsListItem>>
            ) {
                Log.e("response", response.body().toString())
            }

            override fun onFailure(call: Call<List<CatsListItem>>, t: Throwable) {
                Log.e("failure", t.localizedMessage.toString())
            }

        })
    }
}