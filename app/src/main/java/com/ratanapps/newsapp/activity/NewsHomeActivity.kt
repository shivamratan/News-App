package com.ratanapps.newsapp.activity

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.ratanapps.newsapp.R
import com.ratanapps.newsapp.base.BaseActivity
import com.ratanapps.newsapp.fragment.NewsFavouriteFragment
import com.ratanapps.newsapp.fragment.NewsHomeFragment
import com.ratanapps.newsapp.viewmodel.NewsHomeViewModel
import kotlinx.android.synthetic.main.activity_news_home.*

class NewsHomeActivity : BaseActivity() {

    private  var newsTypeMap:LinkedHashMap<String,String>
    private var queryMap:LinkedHashMap<String,String>
    private lateinit var viewModel:NewsHomeViewModel
    private var newsTypeInitFlag:Boolean = true
    private var queryInitFlag:Boolean = true


    init {
        newsTypeMap = LinkedHashMap<String,String>()
        newsTypeMap.put("Top Headline","top-headlines")
        newsTypeMap.put("Everything","everything")

        queryMap = LinkedHashMap<String,String>()
        queryMap.put("India","in")
        queryMap.put("US","us")
    }

    override fun getLayoutId(): Int = R.layout.activity_news_home

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(toolbar_layout_id)
        supportActionBar?.title = resources.getString(R.string.home_news_title)

        configureSpinners()
        setUp()
        configureBottomNavigation()
        configureViewModel()
        fetchNewsDataFromServer()
    }

    private fun configureSpinners(){

       val newsTypeAdapter:ArrayAdapter<String> = ArrayAdapter(this@NewsHomeActivity, R.layout.layout_spinner_custom_textview,newsTypeMap.keys.toTypedArray())
       newsTypeAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
       spinner_newsType.adapter = newsTypeAdapter

       spinner_newsType.onItemSelectedListener = object:AdapterView.OnItemSelectedListener{
           override fun onNothingSelected(parent: AdapterView<*>?) {

           }

           override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
             if(!newsTypeInitFlag)
                fetchNewsDataFromServer()
             else
                 newsTypeInitFlag = false
           }

       }

       val queryAdapter:ArrayAdapter<String> = ArrayAdapter(this@NewsHomeActivity,R.layout.layout_spinner_custom_textview,queryMap.keys.toTypedArray())
       queryAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
       spinner_newsGenre.adapter = queryAdapter
       spinner_newsGenre.onItemSelectedListener = object:AdapterView.OnItemSelectedListener{
           override fun onNothingSelected(parent: AdapterView<*>?) {

           }

           override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
               if(!queryInitFlag)
                   fetchNewsDataFromServer()
               else
                   queryInitFlag = false
           }

       }
    }


    private fun configureBottomNavigation(){
        navigation.setOnNavigationItemSelectedListener {
           val fragmentTransaction = supportFragmentManager.beginTransaction()

            when(it.itemId){
                R.id.navigation_home -> {
                    fragmentTransaction.replace(R.id.newsFragmentContainer,NewsHomeFragment.getInstance()?:NewsHomeFragment())
                    supportActionBar?.title = resources.getString(R.string.home_news_title)
                    news_filter_pallete.visibility = View.VISIBLE
                }
                R.id.navigation_fav -> {
                    fragmentTransaction.replace(R.id.newsFragmentContainer,NewsFavouriteFragment.getInstance()?:NewsFavouriteFragment())
                    supportActionBar?.title = resources.getString(R.string.fav_news_title)
                    news_filter_pallete.visibility = View.GONE
                }
            }
            fragmentTransaction.commit()

            true
        }
    }


    private fun configureViewModel(){
        viewModel = ViewModelProviders.of(this,viewModelFactory)[NewsHomeViewModel::class.java]
    }

    private fun setUp(){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.newsFragmentContainer,NewsHomeFragment.getInstance()?:NewsHomeFragment())
        fragmentTransaction.commit()

    }

    private fun fetchNewsDataFromServer(){
        val newsType = newsTypeMap.toList().get(spinner_newsType.selectedItemPosition).second
        val newsGenre = queryMap.toList().get(spinner_newsGenre.selectedItemPosition).second
        this.viewModel.showLoadingNewsHome.value = true
        this.viewModel.getNews(newsType,newsGenre)
    }
}
