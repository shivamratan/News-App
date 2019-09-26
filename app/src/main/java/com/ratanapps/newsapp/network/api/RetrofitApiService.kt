package com.ratanapps.newsapp.network.api;

import com.ratanapps.newsapp.model.News
import com.ratanapps.newsapp.network.UrlConstant
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApiService
{
    @GET("top-headlines")
    fun getTopHeadlineNews(@Query("country") country:String, @Query("apiKey") apiKey:String =UrlConstant.API_KEY): Observable<News>

    @GET("everything")
    fun getEverythingNews( @Query("q")query:String, @Query("apiKey") apiKey:String =UrlConstant.API_KEY): Observable<News>

}
