package com.timapps.electrihype

data class FeedPostDataModel (
    /**************************************
     * initialized Variables for Object.  *
     **************************************/

    var numberOfLikes: Int,
    var mainContentText: String,
    var imageResId: Int,
    var username: String

) {
    /****************************
     * Constructor for Object.  *
     ****************************/

    constructor() : this(0, "", 0, "")

    /************************
     * Getters and setters  *
     ************************/


    /*fun getNumberOfLikes(): Int {
        return numberOfLikes
    }

    fun setNumberOfLikes(likes: Int) {
        numberOfLikes = likes
    }

    fun getMainContentText(): String {
        return mainContentText
    }

    fun setMainContentText(contentText: String) {
        mainContentText = contentText
    }

    fun getImageResId(): Int {
        return imageResId
    }

    fun setImageResId(resId: Int) {
        imageResId = resId
    }

    fun getUsername(): String {
        return username
    }

    fun setUsername(name: String) {
        username = name
    }*/
}

