package com.ratanapps.newsapp.di

import android.app.Application
import com.ratanapps.newsapp.db.repo.DatabaseRepository
import com.ratanapps.newsapp.network.NewsRepository
import com.ratanapps.newsapp.network.api.RetrofitApiService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepoModule
{
    @Singleton
    @Provides
    fun provideNewsRepository(retrofitApiService: RetrofitApiService)= NewsRepository(retrofitApiService)

    @Singleton
    @Provides
    fun provideDatabaseRepository(application: Application)= DatabaseRepository(application)
}