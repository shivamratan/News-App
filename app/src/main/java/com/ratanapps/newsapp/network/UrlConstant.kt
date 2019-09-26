package com.ratanapps.newsapp.network

class UrlConstant {
    companion object{
        const val TIMEOUT_REQUEST:Long = 30
        const val BASE_URL = "https://newsapi.org/v2/"
        const val API_STATUS_OK = "ok"
        const val API_STATUS_ERR = "error"

        const val API_NEWS_TYPE_TOP_HEADLINE = "top-headlines"
        const val API_NEWS_TYPE_EVERYTHING ="everything"

        const val FAV_NEWS_REDIRECTION = "fav_news_redirection"
        const val HOME_NEWS_REDIRECTION = "home_news_redirection"

        const val API_KEY ="************************************"

    }
}