package com.adityafakhri.medfluffy.presentation.adapter

import android.os.Parcel
import android.os.Parcelable

data class ResultScan(
    val imageUrl: String? = null,
    val accuracy: String? = null,
    val prediction: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(imageUrl)
        parcel.writeString(accuracy)
        parcel.writeString(prediction)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ResultScan> {
        override fun createFromParcel(parcel: Parcel): ResultScan {
            return ResultScan(parcel)
        }

        override fun newArray(size: Int): Array<ResultScan?> {
            return arrayOfNulls(size)
        }
    }
}