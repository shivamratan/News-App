package com.ratanapps.newsapp.viewmodel

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.ratanapps.newsapp.base.BaseViewModel
import com.ratanapps.newsapp.db.entity.ArticleTable
import com.ratanapps.newsapp.db.repo.DatabaseRepository
import com.ratanapps.newsapp.di.OBSERVER_ON
import com.ratanapps.newsapp.di.SUBCRIBER_ON
import com.ratanapps.newsapp.model.News
import com.ratanapps.newsapp.network.NewsRepository
import io.reactivex.Scheduler
import javax.inject.Inject
import javax.inject.Named

class NewsHomeViewModel @Inject constructor( val newsRepository: NewsRepository,val databaseRepository: DatabaseRepository , @param:Named(SUBCRIBER_ON) private val subscriberOn: Scheduler,
                                            @param:Named(OBSERVER_ON) private val observerOn: Scheduler,application: Application) :BaseViewModel(application) {

    val newsData:MutableLiveData<News> = MutableLiveData()
    val isLoading:MutableLiveData<Boolean> = MutableLiveData()
    val showLoadingNewsHome:MutableLiveData<Boolean> = MutableLiveData()
    var errorMessage:MutableLiveData<String> = MutableLiveData()
    var articleList:LiveData<List<ArticleTable>>?

    init {
        isLoading.value = false
        showLoadingNewsHome.value = false
        articleList = databaseRepository.getAllSavedArticle()

    }


    fun bookMarkArticle(article: News.Article){
        val tableArticle: ArticleTable = ArticleTable(article.source?.name,article.author,article.title,article.description,article.url,article.urlToImage,article.publishedAt,article.content)
        databaseRepository.insertArticle(tableArticle)
    }

    fun deleteBookMarkArticle(article: News.Article){
     //   val tableArticle: ArticleTable = ArticleTable(article.source?.name,article.author,article.title,article.description,article.url,article.urlToImage,article.publishedAt,article.content)
        databaseRepository.deleteBookMarkByTitle(article.title?:"")
    }

    fun deleteBookMarkArticle(article: ArticleTable){
       databaseRepository.deleteBookMark(article)
    }

    fun getNews(newsType:String,newsGenre:String){
        this.disposable.addAll(this.newsRepository.getNews(newsType,newsGenre)
                .subscribeOn(subscriberOn)
                .observeOn(observerOn)
                .doOnSubscribe{
                    this.isLoading.value = true
                }
                .doOnComplete{
                    this.isLoading.value = true
                }
                .doOnError{
                    this.isLoading.value = false
                }
                .subscribe({
                    this.newsData.value = it
                },{
                    this.errorMessage.value = it.message
                }))
    }


}