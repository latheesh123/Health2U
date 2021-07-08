package com.example.health2u.repo

import com.example.health2u.model.ResponseObject
import com.example.health2u.model.ServiceResult
import com.google.android.gms.common.api.internal.ApiKey
import com.iqvia.onekey.GetActivityByIdQuery

interface Repository {
    suspend fun getNewsArticles(country:String,category: String,apiKey: String):ServiceResult<ResponseObject>

    suspend fun getTestCenters(apiKey: String,q:String,at:String,limit:String):ServiceResult<ResponseObject>

}