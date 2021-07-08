package com.example.health2u.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ServiceException(val staus:Int,val errorcode:String?,val errorMessages: String): Parcelable