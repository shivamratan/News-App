package com.ratanapps.newsapp.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class News(
    @SerializedName("status")  var status: String? = null,
    @SerializedName("totalResults")  var totalResults: Int? = null,
    @SerializedName("articles")  var articles: List<Article>? = null){

    data class Source(
            @SerializedName("id")
            var id: String? = "",
            @SerializedName("name")
            var name: String? = ""):Parcelable{
        constructor(parcel: Parcel) : this(
                parcel.readString(),
                parcel.readString()) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(id)
            parcel.writeString(name)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Source> {
            override fun createFromParcel(parcel: Parcel): Source {
                return Source(parcel)
            }

            override fun newArray(size: Int): Array<Source?> {
                return arrayOfNulls(size)
            }
        }
    }


    data class Article(
            @SerializedName("source") var source: Source? = Source(),
            @SerializedName("author") var author: String? = "",
            @SerializedName("title") var title: String? = "",
            @SerializedName("description") var description: String? = "",
            @SerializedName("url") var url: String? = "",
            @SerializedName("urlToImage") var urlToImage: String? = "",
            @SerializedName("publishedAt") var publishedAt: String? = "",
            @SerializedName("content") var content: String? = "",
            var isTopHeadline:Boolean = true,
            var isFavourite:Boolean = false):Parcelable{
        constructor(parcel: Parcel) : this(
                parcel.readParcelable(Source::class.java.classLoader),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readByte() != 0.toByte(),
                parcel.readByte() != 0.toByte()) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeParcelable(source, flags)
            parcel.writeString(author)
            parcel.writeString(title)
            parcel.writeString(description)
            parcel.writeString(url)
            parcel.writeString(urlToImage)
            parcel.writeString(publishedAt)
            parcel.writeString(content)
            parcel.writeByte(if (isTopHeadline) 1 else 0)
            parcel.writeByte(if (isFavourite) 1 else 0)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Article> {
            override fun createFromParcel(parcel: Parcel): Article {
                return Article(parcel)
            }

            override fun newArray(size: Int): Array<Article?> {
                return arrayOfNulls(size)
            }
        }

    }

}

