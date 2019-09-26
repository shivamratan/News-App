package com.ratanapps.newsapp.activity

import android.app.Instrumentation
import android.support.test.InstrumentationRegistry.getInstrumentation
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import com.ratanapps.newsapp.R
import com.ratanapps.newsapp.fragment.NewsFavouriteFragment
import com.ratanapps.newsapp.fragment.NewsHomeFragment
import junit.framework.TestCase.assertNotNull
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NewsHomeActivityTest {

    @Rule @JvmField
    val activityTestRule:ActivityTestRule<NewsHomeActivity> = ActivityTestRule<NewsHomeActivity>(NewsHomeActivity::class.java)

    val monitor: Instrumentation.ActivityMonitor = getInstrumentation().addMonitor(NewsDetailActivity::class.java.name,null,false)
    var newsHomeActivity: NewsHomeActivity? = null

    @Before
    fun setUp() {
        newsHomeActivity = activityTestRule.activity

    }

    @Test
    fun activityHomeLaunchTest(){
        val view: View? = newsHomeActivity?.findViewById(R.id.newsFragmentContainer)
        assertNotNull(view)
    }

    @Test
    fun activityHomeFragmentNewsLaunchTest(){
        val fragmentContainer: FrameLayout? = newsHomeActivity?.findViewById(R.id.newsFragmentContainer)
        assertNotNull(fragmentContainer)

        val newsHomeFragment = NewsHomeFragment()
        newsHomeActivity?.supportFragmentManager?.beginTransaction()?.replace(fragmentContainer?.id?:0,newsHomeFragment)?.commitAllowingStateLoss()
        Thread.sleep(400)
        val recyclerView = newsHomeFragment.view?.findViewById<RecyclerView>(R.id.newsRecyclerView)
        assertNotNull(recyclerView)

    }

    @Test
    fun activityHomeBookMarkFragmentLaunchTest(){
        val fragmentContainer: FrameLayout? = newsHomeActivity?.findViewById(R.id.newsFragmentContainer)
        assertNotNull(fragmentContainer)

        val newsFavFragment = NewsFavouriteFragment()
        newsHomeActivity?.supportFragmentManager?.beginTransaction()?.replace(fragmentContainer?.id?:0,newsFavFragment)?.commitAllowingStateLoss()
        getInstrumentation().waitForIdleSync()
        val recyclerView = newsFavFragment.view?.findViewById<RecyclerView>(R.id.bookmarkRecyclerView)
        assertNotNull(recyclerView)
    }


    @Test
    fun testNewsHomeRecyclerViewClickToLaunchNewsDetailActivity(){
        val fragmentContainer: FrameLayout? = newsHomeActivity?.findViewById(R.id.newsFragmentContainer)
        assertNotNull(fragmentContainer)

        val newsHomeFragment = NewsHomeFragment()
        newsHomeActivity?.supportFragmentManager?.beginTransaction()?.replace(fragmentContainer?.id?:0,newsHomeFragment)?.commitAllowingStateLoss()
        getInstrumentation().waitForIdleSync()
        val recyclerView = newsHomeFragment.view?.findViewById<RecyclerView>(R.id.newsRecyclerView)
        assertNotNull(recyclerView)


        Thread.sleep(10000)
        if(recyclerView?.adapter?.itemCount!! >0){
         onView(withId(R.id.newsRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,click()))
         val newsDetailActivity  = getInstrumentation().waitForMonitorWithTimeout(monitor,10000)

         assertNotNull(newsDetailActivity)
        }
    }

    @Test
    fun testSavedNewsRecyclerViewClickToLaunchNewsDetailActivity(){
        val fragmentContainer: FrameLayout? = newsHomeActivity?.findViewById(R.id.newsFragmentContainer)
        assertNotNull(fragmentContainer)

        val newsHomeFragment = NewsHomeFragment()
        newsHomeActivity?.supportFragmentManager?.beginTransaction()?.replace(fragmentContainer?.id?:0,newsHomeFragment)?.commitAllowingStateLoss()
        getInstrumentation().waitForIdleSync()
        val recyclerView = newsHomeFragment.view?.findViewById<RecyclerView>(R.id.newsRecyclerView)
        assertNotNull(recyclerView)


        Thread.sleep(10000)
        if(recyclerView?.adapter?.itemCount!! >0) {
            onView(withId(R.id.newsRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, object :ViewAction{
                override fun getDescription(): String {
                    return ""
                }

                override fun getConstraints(): Matcher<View>? {
                 return null
                }

                override fun perform(uiController: UiController?, view: View?) {
                    val bookmark = view?.findViewById<ImageView>(R.id.iv_favourite)
                    bookmark?.performClick()
                }

            }))

         val newsBookMarkFragment = NewsFavouriteFragment()
         newsHomeActivity?.supportFragmentManager?.beginTransaction()?.replace(fragmentContainer?.id?:0,newsBookMarkFragment)?.commitAllowingStateLoss()
         getInstrumentation().waitForIdleSync()

         val bookMarkRecyclerView = newsBookMarkFragment.view?.findViewById<RecyclerView>(R.id.bookmarkRecyclerView)
         assertNotNull(bookMarkRecyclerView)

         Thread.sleep(1000)
         onView(withId(R.id.bookmarkRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,click()))
         val newsDetailActivity = getInstrumentation().waitForMonitorWithTimeout(monitor,5000)
         assertNotNull(newsDetailActivity)

        }
    }



    @After
    fun tearDown() {
        newsHomeActivity = null
    }
}