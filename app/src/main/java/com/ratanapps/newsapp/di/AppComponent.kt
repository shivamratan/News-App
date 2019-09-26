package com.ratanapps.newsapp.di

import com.ratanapps.newsapp.base.BaseActivity
import dagger.Component
import io.github.philippeboisney.retrokotlin.di.ViewModelModule
import javax.inject.Singleton

@Singleton
@Component(modules = [NetModule::class, ViewModelModule::class,RepoModule::class,RxjavaModule::class,UtilModule::class])
interface AppComponent {
    fun inject(baseActivity: BaseActivity)
}