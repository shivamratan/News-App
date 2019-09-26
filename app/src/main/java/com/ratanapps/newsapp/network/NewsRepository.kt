package com.ratanapps.newsapp.network

import com.ratanapps.newsapp.model.News
import com.ratanapps.newsapp.network.api.RetrofitApiService
import io.reactivex.Observable

class NewsRepository(private val retrofitApiService: RetrofitApiService)
{
    fun getNews(newsType:String,newsGenre:String): Observable<News> {

      if(newsType.equals(UrlConstant.API_NEWS_TYPE_TOP_HEADLINE))
        return this.retrofitApiService.getTopHeadlineNews(newsGenre)
      else
        return this.retrofitApiService.getEverythingNews(newsGenre)
    }
}