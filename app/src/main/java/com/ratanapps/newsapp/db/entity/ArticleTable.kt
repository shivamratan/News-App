package com.ratanapps.newsapp.db.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable

@Entity
data class ArticleTable(@ColumnInfo(name ="publishedby") var source: String? = "",
                        @ColumnInfo(name ="author") var author: String? = "",
                        @ColumnInfo(name ="title") var title: String? = "",
                        @ColumnInfo(name ="description") var description: String? = "",
                        @ColumnInfo(name = "url") var url: String? = "",
                        @ColumnInfo(name ="urlToImage") var urlToImage: String? = "",
                        @ColumnInfo(name ="publishedAt") var publishedAt: String? = "",
                        @ColumnInfo(name ="content") var content: String? = "",
                        @PrimaryKey(autoGenerate = true) var uid:Int = 0):Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(source)
        parcel.writeString(author)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(url)
        parcel.writeString(urlToImage)
        parcel.writeString(publishedAt)
        parcel.writeString(content)
        parcel.writeInt(uid)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ArticleTable> {
        override fun createFromParcel(parcel: Parcel): ArticleTable {
            return ArticleTable(parcel)
        }

        override fun newArray(size: Int): Array<ArticleTable?> {
            return arrayOfNulls(size)
        }
    }

}