package com.ratanapps.newsapp.di

import com.ratanapps.newsapp.myapp.NewsApplication
import dagger.Module
import dagger.Provides

@Module
class UtilModule
{
    @Provides
    fun provideApplicationInstance() = NewsApplication.getApplicationInstance()
}