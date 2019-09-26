package com.ratanapps.newsapp.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ratanapps.newsapp.myapp.NewsApplication
import com.ratanapps.newsapp.viewmodel.ViewModelFactory
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity()
{
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        configureDagger()

    }


    private fun configureDagger() = (this.application as NewsApplication).appComponent.inject(this)

    abstract fun getLayoutId():Int
}