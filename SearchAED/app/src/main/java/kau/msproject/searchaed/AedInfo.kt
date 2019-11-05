package kau.msproject.searchaed

import android.os.Parcel
import android.os.Parcelable

class AedInfo(
    val buildAddress: String,
    val zipCode1: String,
    val zipCode2: String,
    val org: String,
    val clerkTel: String,
    val buildPlace: String,
    val manager: String,
    val managerTel: String,
    val model: String
) : Parcelable {

    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(buildAddress)
        writeString(zipCode1)
        writeString(zipCode2)
        writeString(org)
        writeString(clerkTel)
        writeString(buildPlace)
        writeString(manager)
        writeString(managerTel)
        writeString(model)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<AedInfo> = object : Parcelable.Creator<AedInfo> {
            override fun createFromParcel(source: Parcel): AedInfo = AedInfo(source)
            override fun newArray(size: Int): Array<AedInfo?> = arrayOfNulls(size)
        }
    }
}