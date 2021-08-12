package gortea.jgmax.catslist

import android.app.Application
import androidx.room.Room
import gortea.jgmax.catslist.data.local.cats.favourites.room.dao.CatsListDao
import gortea.jgmax.catslist.data.local.cats.favourites.room.database.CatsListDatabase
import gortea.jgmax.catslist.data.local.constants.API_BASE_URL
import gortea.jgmax.catslist.data.remote.cats.api.CatsApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CatsApp : Application() {
    lateinit var catsApi: CatsApi
    lateinit var catsDao: CatsListDao

    override fun onCreate() {
        super.onCreate()
        configureRetrofit()
        configureDao()
    }

    private fun configureDao() {
        val db = Room.databaseBuilder(
            applicationContext,
            CatsListDatabase::class.java,
            CatsListDatabase.DATABASE_NAME
        ).build()
        catsDao = db.CatsListDao()
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