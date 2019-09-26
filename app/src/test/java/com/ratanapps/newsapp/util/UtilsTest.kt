package com.ratanapps.newsapp.util

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class UtilsTest{

    @Test
    fun stringValidator_correctString_ReturnsTrue(){
        assertTrue(Utils.isValidString("samplestring"))
    }

    @Test
    fun stringValidator_wrongString1_ReturnsFalse(){
        assertFalse(Utils.isValidString(null))
    }

    @Test
    fun stringValidator_wrongString2_ReturnsFalse(){
        assertFalse(Utils.isValidString(""))
    }


    @Test
    fun urlValidator_correctUrl1_ReturnsTrue(){
        //same as our baseUrl of News API
        val BASE_URL_TEST = "https://newsapi.org/v2/"
        assertTrue(Utils.isValidUrl(BASE_URL_TEST))
    }

    @Test
    fun urlValidator_correct2_ReturnsTrue(){
        assertTrue(Utils.isValidUrl("https://www.newsapi.org/v2"))
    }

    @Test
    fun urlValidator_wrongUrl1_ReturnsFalse(){
        //testing the case of spacing in the url
        assertFalse(Utils.isValidUrl("https:// newsapi.org/v2"))
    }

    @Test
    fun urlValidator_wrongUrl2_ReturnsFalse(){
        //testing the case of url with no domain
        assertFalse(Utils.isValidUrl("newsapi.com"))
    }

}