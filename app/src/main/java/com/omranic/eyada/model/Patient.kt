package com.omranic.eyada.model

import android.os.Parcelable
import androidx.annotation.Keep

@Keep
@kotlinx.parcelize.Parcelize
class Patient (
    val id: Int,
    val firstName: String,
    val lastName: String,
    val age: Int,
    val phone: String,
    val address: Address
) : Parcelable