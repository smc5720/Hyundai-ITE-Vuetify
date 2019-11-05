package com.example.aedinfo

import android.os.Parcel
import android.os.Parcelable

class Aed(address: String, number: String, long: String) : Parcelable{
    var address : String = address

    var number : String = number

    var long : String = long

    constructor(source: Parcel) : this(
        source.readString()!!, source.readString()!!, source.readString()!!
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(address)
        writeString(number)
        writeString(long)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Aed> = object : Parcelable.Creator<Aed> {
            override fun createFromParcel(source: Parcel): Aed = Aed(source)
            override fun newArray(size: Int): Array<Aed?> = arrayOfNulls(size)
        }
    }
}