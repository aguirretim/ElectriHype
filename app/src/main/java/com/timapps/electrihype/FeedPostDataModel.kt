package com.timapps.electrihype

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable

data class FeedPostDataModel(
    var id: String, 
    var numberOfLikes: Int,
    var mainContentText: String,
    var imageResId: Uri?,
    var username: String
) : Parcelable {

    constructor() : this("", 0, "", null, "")

    // Parcelable implementation
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readParcelable(Uri::class.java.classLoader),
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeInt(numberOfLikes)
        parcel.writeString(mainContentText)
        parcel.writeParcelable(imageResId, flags)
        parcel.writeString(username)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FeedPostDataModel> {
        override fun createFromParcel(parcel: Parcel): FeedPostDataModel {
            return FeedPostDataModel(parcel)
        }

        override fun newArray(size: Int): Array<FeedPostDataModel?> {
            return arrayOfNulls(size)
        }
    }
}
