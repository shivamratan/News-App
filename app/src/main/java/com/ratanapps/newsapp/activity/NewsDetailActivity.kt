package com.ratanapps.newsapp.activity

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.ratanapps.newsapp.R
import com.ratanapps.newsapp.base.BaseActivity
import com.ratanapps.newsapp.db.entity.ArticleTable
import com.ratanapps.newsapp.model.News
import com.ratanapps.newsapp.network.UrlConstant
import com.ratanapps.newsapp.util.Utils
import com.ratanapps.newsapp.util.extensions.toast
import com.ratanapps.newsapp.viewmodel.NewsDetailsViewModel
import com.thefinestartist.finestwebview.FinestWebView
import kotlinx.android.synthetic.main.activity_news_detail.*

class NewsDetailActivity : BaseActivity() {

    lateinit var viewModel:NewsDetailsViewModel

    override fun getLayoutId(): Int = R.layout.activity_news_detail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)

        setSupportActionBar(toolBar_newsdescription)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        supportActionBar?.title = ""
        main_collapsing.title = ""

        configureViewModel()

        val intent = getIntent()
        if(intent.hasExtra("articleInfo"))
        {
            var common_article:Any
            var title:String;var description:String;var author:String;
            var source:String;var publishedAt:String;var urlToImage:String;var url:String
            var isFavourite:Boolean
           if(intent.getStringExtra("source").equals(UrlConstant.HOME_NEWS_REDIRECTION))
           {
               val article:News.Article = intent.getParcelableExtra<News.Article>("articleInfo")
                common_article = article

               title = article.title?:""
               description = article.description?:""
               author = "by ${article.author?:" Anonymous"}"
               source = article.source?.name?:""
               publishedAt = article.publishedAt?:""
               urlToImage = article.urlToImage?:""
               url = article.url?:""
               isFavourite = article.isFavourite
           }
           else {
               val article: ArticleTable = intent.getParcelableExtra<ArticleTable>("articleInfo")
                common_article = article

               title = article.title?:""
               description = article.description?:""
               author = "by ${article.author}"
               source = article.source?:""
               publishedAt = article.publishedAt?:""
               urlToImage = article.urlToImage?:""
               url = article.url?:""
               isFavourite = true
           }
               news_description_Title.setText(title)
               newsDescription.setText(description)
               textViewbyauthor.setText(author)
               textViewbynewspaper.setText(source)
               textViewpublished.setText(publishedAt)

            if(isFavourite)
                fab_favourite.setImageResource(R.drawable.ic_bookmark_white_24dp)
            else
                fab_favourite.setImageResource(R.drawable.ic_bookmark_border_white_24dp)


               if (Utils.isValidString(urlToImage))
                   Glide.with(this@NewsDetailActivity).load(urlToImage).placeholder(R.drawable.ic_dummy_image).into(main_backdrop)

               textView_readmore.setOnClickListener({
                   if (Utils.isValidString(url)) {
                       FinestWebView.Builder(this@NewsDetailActivity).show(url)
                   }
               })

               fab_favourite.setOnClickListener {
                    isFavourite = !isFavourite
                    if(isFavourite){
                        if(common_article is ArticleTable)
                          viewModel.bookMarkArticle(common_article)
                        else if (common_article is News.Article)
                          viewModel.bookMarkArticle(common_article)


                        fab_favourite.setImageResource(R.drawable.ic_bookmark_white_24dp)
                        toast(resources.getString(R.string.ac_bookmark_added))
                    }else{
                        if(common_article is ArticleTable)
                            viewModel.deleteBookMarkArticle(common_article)
                        else if (common_article is News.Article)
                            viewModel.deleteBookMarkArticle(common_article)

                        fab_favourite.setImageResource(R.drawable.ic_bookmark_border_white_24dp)
                        toast(resources.getString(R.string.ac_bookmark_removed))
                    }
               }

           }
        else{
            finish()
        }

    }

    private fun configureViewModel(){
        viewModel = ViewModelProviders.of(this,viewModelFactory)[NewsDetailsViewModel::class.java]
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
           android.R.id.home ->finish()
        }

        return super.onOptionsItemSelected(item)
    }


}
