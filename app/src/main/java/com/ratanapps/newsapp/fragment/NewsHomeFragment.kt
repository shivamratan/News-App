package com.ratanapps.newsapp.fragment


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.ratanapps.newsapp.R
import com.ratanapps.newsapp.activity.NewsHomeActivity
import com.ratanapps.newsapp.adapter.NewsRecyclerAdapter
import com.ratanapps.newsapp.model.News
import com.ratanapps.newsapp.viewmodel.NewsHomeViewModel
import kotlinx.android.synthetic.main.fragment_news_home.view.*

class NewsHomeFragment : Fragment() {

    lateinit var viewModel:NewsHomeViewModel
    lateinit var newsAdapter:NewsRecyclerAdapter
    lateinit var newsLoadingFrame:FrameLayout
    lateinit var newsRecylerView:RecyclerView
    lateinit var errorFrame:FrameLayout

    val emptyList = ArrayList<News.Article>()

    companion object{
         var newHomeFragmentReference: NewsHomeFragment? =null

        public fun getInstance():NewsHomeFragment?{
            if(newHomeFragmentReference==null)
                newHomeFragmentReference = NewsHomeFragment()

            return newHomeFragmentReference
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       activity?.let {
           viewModel = ViewModelProviders.of(it)[NewsHomeViewModel::class.java]
       }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view:View=  inflater.inflate(R.layout.fragment_news_home, container, false)

        newsLoadingFrame = view.loadingFrame
        newsRecylerView = view.newsRecyclerView
        errorFrame = view.error_Frame



        newsLoadingFrame.visibility = View.VISIBLE
        newsAdapter = NewsRecyclerAdapter(activity as Context, emptyList,viewModel)
        newsRecylerView.adapter = newsAdapter
        newsRecylerView.layoutManager = LinearLayoutManager(activity)

        observeData()

        return view
    }

    private fun observeData(){
        this.viewModel.newsData.observe(activity as NewsHomeActivity, Observer {
            //toast("Data Loaded !")
            //Log.e("DATA_PRINT",it.toString())
            val articleList:List<News.Article> = it?.articles?:emptyList

            newsAdapter.updateAdapter(articleList)
            newsLoadingFrame.visibility = View.INVISIBLE
            newsRecylerView.visibility = View.VISIBLE
            errorFrame.visibility = View.GONE
            this.viewModel.showLoadingNewsHome.value = false
        })

        this.viewModel.isLoading.observe(activity as NewsHomeActivity, Observer {
            val loadingFlag:Boolean = it?:false
            /*if(loadingFlag){
               // newsLoadingFrame.visibility = View.VISIBLE
                newsRecylerView.visibility = View.INVISIBLE
                errorFrame.visibility = View.INVISIBLE
            }else{
                newsLoadingFrame.visibility = View.INVISIBLE
                //newsRecyclerView.visibility = View.INVISIBLE
            }*/
        })

        this.viewModel.showLoadingNewsHome.observe(activity as NewsHomeActivity, Observer {
         if(it?:false) {
             newsLoadingFrame.visibility = View.VISIBLE
             errorFrame.visibility = View.INVISIBLE
             newsRecylerView.visibility = View.INVISIBLE
         }
        })

        this.viewModel.errorMessage.observe(activity as NewsHomeActivity, Observer {
            Log.e("DATA_ERR",it)
            //newsLoadingFrame.visibility = View.INVISIBLE
            //this.viewModel.errorMessage= MutableLiveData()
            newsRecylerView.visibility = View.INVISIBLE
            newsLoadingFrame.visibility = View.INVISIBLE
            errorFrame.visibility = View.VISIBLE
        })
    }


}
