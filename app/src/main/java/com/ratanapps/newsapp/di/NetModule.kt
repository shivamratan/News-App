package com.ratanapps.newsapp.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ratanapps.newsapp.BuildConfig
import com.ratanapps.newsapp.network.UrlConstant
import com.ratanapps.newsapp.network.UrlConstant.Companion.TIMEOUT_REQUEST
import com.ratanapps.newsapp.network.api.RetrofitApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetModule
{
    @Provides
    fun provideGson():Gson = GsonBuilder().create()

    @Provides
    fun provideHttpClient():OkHttpClient = OkHttpClient().newBuilder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            })
            .connectTimeout(TIMEOUT_REQUEST, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_REQUEST, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_REQUEST, TimeUnit.SECONDS)
            .build()

    @Provides
    fun providesRetrofitClient(okHttpClient: OkHttpClient, gson: Gson): Retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .baseUrl(UrlConstant.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    fun getRetrofitApiService(retrofit: Retrofit):RetrofitApiService = retrofit.create(RetrofitApiService::class.java)

}
