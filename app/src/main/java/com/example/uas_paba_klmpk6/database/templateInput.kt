package com.example.uas_paba_klmpk6.database

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class templateInput(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_template")
    var id : Int = 0,

    @ColumnInfo(name = "type")
    var type: String ? = null,

    @ColumnInfo(name = "amount")
    var amount: Int = 0,

    @ColumnInfo(name = "category")
    var category: String? = null,

    @ColumnInfo(name = "title")
    var title: String? = null,

    @ColumnInfo(name = "note")
    var note: String? = null,

    @ColumnInfo(name = "reminder")
    var reminder: Int,

    @ColumnInfo(name = "date")
    val date: String? = null,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(type)
        parcel.writeInt(amount)
        parcel.writeString(category)
        parcel.writeString(title)
        parcel.writeString(note)
        parcel.writeInt(reminder)
        parcel.writeString(date)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<templateInput> {
        override fun createFromParcel(parcel: Parcel): templateInput {
            return templateInput(parcel)
        }

        override fun newArray(size: Int): Array<templateInput?> {
            return arrayOfNulls(size)
        }
    }
}
