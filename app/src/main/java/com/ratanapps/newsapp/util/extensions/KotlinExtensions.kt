package com.ratanapps.newsapp.util.extensions

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import android.widget.Toast

//for Activity
fun Activity.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

//For fragment
fun Fragment.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(activity as Context, message, duration).show()
}