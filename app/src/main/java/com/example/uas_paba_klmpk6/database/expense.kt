package com.example.uas_paba_klmpk6.database

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class expense(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_expense")
    var id : Int = 0,

    @ColumnInfo(name = "amount")
    var amount : Int = 0,

    @ColumnInfo(name = "category")
    var category : String? = null,

    @ColumnInfo(name = "title")
    var title : String? = null,

    @ColumnInfo(name = "note")
    var note : String? = null,

    @ColumnInfo(name = "date")
    var date : String? = null,

    @ColumnInfo(name = "location")
    var location : String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(amount)
        parcel.writeString(category)
        parcel.writeString(title)
        parcel.writeString(note)
        parcel.writeString(date)
        parcel.writeString(location)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<expense> {
        override fun createFromParcel(parcel: Parcel): expense {
            return expense(parcel)
        }

        override fun newArray(size: Int): Array<expense?> {
            return arrayOfNulls(size)
        }
    }
}
