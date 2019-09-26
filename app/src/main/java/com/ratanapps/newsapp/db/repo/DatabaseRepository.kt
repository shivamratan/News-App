package com.ratanapps.newsapp.db.repo

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import com.ratanapps.newsapp.db.AppDatabase
import com.ratanapps.newsapp.db.dao.NewsDao
import com.ratanapps.newsapp.db.entity.ArticleTable

class DatabaseRepository(application: Application)
{
    private var userDao: NewsDao?
    init {
        userDao = AppDatabase.getInstance(application)?.getNewsDao()
    }

    fun getAllSavedArticle(): LiveData<List<ArticleTable>>? {
        var liveData: LiveData<List<ArticleTable>>? = GetSavedArticleAsyncTask(userDao).execute().get()

        return liveData
    }

    fun deleteBookMark(article: ArticleTable){
        DeleteArticleAsyncTask(userDao).execute(article)
    }

    fun deleteBookMarkByTitle(articleTitle:String){
        DeleteArticleByTitleAsyncTask(userDao).execute(articleTitle)
    }

    fun insertArticle(article: ArticleTable){
        InsertArticleAsyncTask(userDao).execute(article)
    }

    class DeleteArticleByTitleAsyncTask(val newsDao: NewsDao?):AsyncTask<String,Void,Void>(){
        override fun doInBackground(vararg params: String): Void? {
            newsDao?.deleteArticleByTitle(params[0])
            return null
        }

    }

    class DeleteArticleAsyncTask(val newsDao: NewsDao?):AsyncTask<ArticleTable,Void,Void>(){
        override fun doInBackground(vararg params: ArticleTable): Void? {
            newsDao?.deleteArticle(params[0])
            return null
        }

    }

     class InsertArticleAsyncTask(val newsDao: NewsDao?):AsyncTask<ArticleTable,Void,Void>(){
         override fun doInBackground(vararg params: ArticleTable): Void? {
            newsDao?.insertData(params[0])
            return null
         }


     }

     class GetSavedArticleAsyncTask(val newsDao: NewsDao?):AsyncTask<Void,Void,LiveData<List<ArticleTable>>>(){
        override fun doInBackground(vararg params: Void?): LiveData<List<ArticleTable>>? {
         return newsDao?.getAll()
        }

    }
}