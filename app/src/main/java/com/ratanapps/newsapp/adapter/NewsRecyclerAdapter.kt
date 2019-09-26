package com.ratanapps.newsapp.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.ratanapps.newsapp.R
import com.ratanapps.newsapp.activity.NewsDetailActivity
import com.ratanapps.newsapp.model.News
import com.ratanapps.newsapp.network.UrlConstant
import com.ratanapps.newsapp.util.extensions.toast
import com.ratanapps.newsapp.viewmodel.NewsHomeViewModel
import kotlinx.android.synthetic.main.layout_item_news.view.*

class NewsRecyclerAdapter(val context: Context,var newsList: List<News.Article>,val viewModel: NewsHomeViewModel): RecyclerView.Adapter<NewsRecyclerAdapter.NewsViewHolder>()
{
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): NewsViewHolder = NewsViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_item_news,p0,false))

    override fun getItemCount()= newsList.size

    override fun onBindViewHolder(viewHolder: NewsViewHolder, p1: Int) {

        val article = newsList.get(p1)
        viewHolder.tv_newsTitle.setText(article.title)
        viewHolder.tv_newsTag.setText(article.publishedAt)
        article.urlToImage?.also {
            Glide.with(context).load(it).placeholder(R.drawable.ic_dummy_image).into(viewHolder.iv_newsImage)
        }

        if(article.isFavourite)
            viewHolder.iv_newsFavourite.setImageResource(R.drawable.ic_bookmark_black_24dp)
        else
            viewHolder.iv_newsFavourite.setImageResource(R.drawable.ic_bookmark_border_black_24dp)

        viewHolder.tv_newsSource.setText(article.source?.name)

    }

     fun updateAdapter(newsList:List<News.Article>){
        this.newsList = newsList
        notifyDataSetChanged()
    }

     fun throwIntent(adapterPos:Int){
        val intent = Intent(context,NewsDetailActivity::class.java)
        intent.putExtra("source",UrlConstant.HOME_NEWS_REDIRECTION)
        intent.putExtra("articleInfo",newsList.get(adapterPos))
        context.startActivity(intent)
    }

     inner class NewsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
         var tv_newsSource:TextView
         var tv_newsTitle:TextView
         var iv_newsImage:ImageView
         var tv_newsTag:TextView
         var iv_newsFavourite:ImageView

        init {
            tv_newsSource = itemView.newsSource
            tv_newsTitle = itemView.newsTitle
            iv_newsImage = itemView.iv_news_image
            tv_newsTag = itemView.tv_timetag
            iv_newsFavourite = itemView.iv_favourite


            iv_newsFavourite.setOnClickListener {
                val article:News.Article = newsList.get(adapterPosition)
                article.isFavourite = !article.isFavourite
                if(article.isFavourite){
                    viewModel.bookMarkArticle(article)
                    iv_newsFavourite.setImageResource(R.drawable.ic_bookmark_black_24dp)
                    (context as Activity).toast(context.resources.getString(R.string.ac_bookmark_added))
                }else{
                    viewModel.deleteBookMarkArticle(article)
                    iv_newsFavourite.setImageResource(R.drawable.ic_bookmark_border_black_24dp)
                    (context as Activity).toast(context.resources.getString(R.string.ac_bookmark_removed))
                }


            }

            itemView.setOnClickListener({
                throwIntent(adapterPosition)
            })
        }
    }
}