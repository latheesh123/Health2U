package com.example.health2u.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.lang.StringBuilder

sealed class ResponseObject

@Parcelize
open class BaseResposne(
    val code: Int = 0,
    val errorCode: ArrayList<Int> = ArrayList(),
    val errorMessage: ArrayList<String> = ArrayList(),
    val responseMessage: String? = null,
    val httpStatus: Int = 0
) : ResponseObject(), Parcelable

@Parcelize
data class NewsResponse(
    @SerializedName("articles") val articles: ArrayList<Sources>?
) : BaseResposne(), Parcelable

@Parcelize
data class CenterResponse(
    @SerializedName("items") val articles: ArrayList<Centers>?
) : BaseResposne(), Parcelable


@Parcelize
data class Centers(
    val title: String?,
    val address: Address?,
    val position:Position?,
    val distance: Int?
) : Parcelable


@Parcelize
data class Position(
    val lat: Double?,
    val lng: Double?
) : Parcelable



@Parcelize
data class Address(
    val label: String?,
    val countryName: String?,
    val stateCode: String?,
    val city: String?,
    val street:String?,
    val postalCode: String?,
    val county: String?
) : Parcelable


@Parcelize
data class Sources(
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val content: String?
) : Parcelable