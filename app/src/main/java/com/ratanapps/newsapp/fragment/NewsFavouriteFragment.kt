package com.ratanapps.newsapp.fragment


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.ratanapps.newsapp.R
import com.ratanapps.newsapp.adapter.FavNewsRecyclerAdapter
import com.ratanapps.newsapp.db.entity.ArticleTable
import com.ratanapps.newsapp.viewmodel.NewsHomeViewModel
import kotlinx.android.synthetic.main.fragment_news_favourite.view.*
import java.util.*
import kotlin.collections.ArrayList

class NewsFavouriteFragment : Fragment() {

    lateinit var viewModel:NewsHomeViewModel
    lateinit var bookMarkRecyclerView:RecyclerView
    lateinit var noItemFrame:FrameLayout
    lateinit var bookMarkLoadingFrame:FrameLayout
    lateinit var bookMarkRecyclerAdapter:FavNewsRecyclerAdapter
    val emptyList = ArrayList<ArticleTable>()


    companion object{
        var newFavFragmentReference: NewsFavouriteFragment? = null

        public fun getInstance():NewsFavouriteFragment?{
            if(newFavFragmentReference==null)
                newFavFragmentReference = NewsFavouriteFragment()

            return newFavFragmentReference
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
        val view = inflater.inflate(R.layout.fragment_news_favourite, container, false)
        bookMarkRecyclerView =view.bookmarkRecyclerView
        noItemFrame =view.no_item_found_frame
        bookMarkLoadingFrame = view.loadingFrame

        bookMarkRecyclerView.layoutManager = LinearLayoutManager(activity)
        bookMarkRecyclerAdapter = FavNewsRecyclerAdapter(activity as Context,Collections.emptyList(),viewModel)
        bookMarkRecyclerView.adapter = bookMarkRecyclerAdapter

        observeData()

        return view
    }


    private fun observeData(){
        this.viewModel.articleList?.observe(this, Observer {
            bookMarkRecyclerAdapter.updateAdapter(it?:emptyList)
            bookMarkRecyclerView.visibility = View.INVISIBLE
            bookMarkLoadingFrame.visibility = View.INVISIBLE
            noItemFrame.visibility = View.INVISIBLE

           if(it?.size!! >0)
               bookMarkRecyclerView.visibility = View.VISIBLE
           else
               noItemFrame.visibility = View.VISIBLE


        })
    }





}
