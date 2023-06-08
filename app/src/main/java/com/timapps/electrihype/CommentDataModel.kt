import android.os.Parcel
import android.os.Parcelable

data class CommentDataModel(
    val comment: String,
    val author: String) : Parcelable {
    // You can add additional functions or properties here, if needed

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(comment)
        parcel.writeString(author)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CommentDataModel> {
        override fun createFromParcel(parcel: Parcel): CommentDataModel {
            return CommentDataModel(parcel)
        }

        override fun newArray(size: Int): Array<CommentDataModel?> {
            return arrayOfNulls(size)
        }
    }
}
