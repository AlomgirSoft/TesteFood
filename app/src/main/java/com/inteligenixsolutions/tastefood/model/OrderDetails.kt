package com.inteligenixsolutions.tastefood.model


import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

class OrderDetails(
    val userId: String = "",
    val userName: String = "",
    val foodNames: MutableList<String>? = null,
    val foodImage: MutableList<String>? = null,
    val foodPrice: MutableList<String>? = null,
    val foodQuantities: MutableList<Int>? = null,
    val address: String = "",
    val totalPrice: String = "",
    val phoneNumber: String = "",
    val itemPushKey: String = "",
    val currentTime: Long = 0,
    val orderAccepted: Boolean = false,
    val paymentReceived: Boolean = false
) : Serializable {
    constructor(parcel: Parcel) : this(
        userId = parcel.readString() ?: "",
        userName = parcel.readString() ?: "",
        foodNames = parcel.createStringArrayList(),
        foodImage = parcel.createStringArrayList(),
        foodPrice = parcel.createStringArrayList(),
        foodQuantities = parcel.createIntArray()?.toMutableList(),
        address = parcel.readString() ?: "",
        totalPrice = parcel.readString() ?: "",
        phoneNumber = parcel.readString() ?: "",
        itemPushKey = parcel.readString() ?: "",
        currentTime = parcel.readLong(),
        orderAccepted = parcel.readByte() != 0.toByte(),
        paymentReceived = parcel.readByte() != 0.toByte()
    )

    fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userId)
        parcel.writeString(userName)
        parcel.writeStringList(foodNames)
        parcel.writeStringList(foodImage)
        parcel.writeStringList(foodPrice)
        parcel.writeIntArray(foodQuantities?.toIntArray())
        parcel.writeString(address)
        parcel.writeString(totalPrice)
        parcel.writeString(phoneNumber)
        parcel.writeString(itemPushKey)
        parcel.writeLong(currentTime)
        parcel.writeByte(if (orderAccepted) 1 else 0)
        parcel.writeByte(if (paymentReceived) 1 else 0)
    }

    fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OrderDetails> {
        override fun createFromParcel(parcel: Parcel): OrderDetails {
            return OrderDetails(parcel)
        }

        override fun newArray(size: Int): Array<OrderDetails?> {
            return arrayOfNulls(size)
        }
    }
}
