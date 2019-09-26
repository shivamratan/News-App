package com.ratanapps.newsapp.util

import java.net.URL

class Utils {
    companion object{

        fun isValidString(str:String?):Boolean{
            return str!=null&&!str.isEmpty();
        }

        fun isValidUrl(url:String):Boolean{
            try{
                URL(url).toURI()
                return true
            }catch (e:Exception){
                return false
            }
        }
    }
}