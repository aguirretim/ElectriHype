package com.timapps.electrihype
import android.os.Parcel
import android.os.Parcelable

data class FeedPostDataModel(

    /**************************************
     * initialized Variables for Object.  *
     **************************************/

    var numberOfLikes: Int,
    var mainContentText: String,
    var imageResId: Int,
    var username: String
) : Parcelable {

    /****************************
     * Constructor for Object.  *
     ****************************/
    constructor() : this(0, "", 0, "")

    // Parcelable implementation
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(numberOfLikes)
        parcel.writeString(mainContentText)
        parcel.writeInt(imageResId)
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
