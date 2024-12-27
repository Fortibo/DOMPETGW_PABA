package com.example.uas_paba_klmpk6.database

import android.os.Parcel
import android.os.Parcelable
import androidx.versionedparcelable.VersionedParcelize

@VersionedParcelize
data class history(
    val type: String, // "income" atau "expense"
    val amount: Int,
    val category: String,
    val title: String,
    val note: String,
    val date: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(type)
        parcel.writeInt(amount)
        parcel.writeString(category)
        parcel.writeString(title)
        parcel.writeString(note)
        parcel.writeString(date)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<history> {
        override fun createFromParcel(parcel: Parcel): history {
            return history(parcel)
        }

        override fun newArray(size: Int): Array<history?> {
            return arrayOfNulls(size)
        }
    }
}