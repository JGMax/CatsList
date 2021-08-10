package gortea.jgmax.catslist

import android.app.Application
import gortea.jgmax.catslist.data.local.constants.API_BASE_URL
import gortea.jgmax.catslist.data.remote.cats.api.CatsApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
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
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .build()

        catsApi = retrofit.create(CatsApi::class.java)
    }
}