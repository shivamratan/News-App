package com.ratanapps.newsapp.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.ratanapps.newsapp.db.entity.ArticleTable

@Dao
interface NewsDao {


    @Query("SELECT * FROM ArticleTable")
    fun getAll():LiveData<List<ArticleTable>>

    @Query("DELETE FROM ArticleTable WHERE title =:title")
    fun deleteArticleByTitle(title:String)

    @Insert
    fun insertData(vararg article: ArticleTable)

    @Delete
    fun deleteArticle(article: ArticleTable)
}