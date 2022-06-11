package com.omranic.eyada.model

import android.os.Parcelable

@kotlinx.parcelize.Parcelize
class Address (
    val address: String,
    val city: String
) : Parcelable