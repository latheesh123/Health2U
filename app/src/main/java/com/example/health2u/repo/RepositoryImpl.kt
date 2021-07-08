package com.example.health2u.repo

import com.example.health2u.model.BaseResposne
import com.example.health2u.model.ResponseObject
import com.example.health2u.model.ServiceException
import com.example.health2u.model.ServiceResult
import com.example.health2u.service.ApiService
import com.example.health2u.service.RetrofitBase
import com.example.health2u.service.TestCenterApiService
import com.example.health2u.utils.Utils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException

class RepositoryImpl() : Repository {
    private val apiService: ApiService = RetrofitBase.getApiService(Utils.base_Url)
    private val testingapiService: TestCenterApiService =
        RetrofitBase.getCenterApiService(Utils.test_center_base_Url)
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun getNewsArticles(
        country: String,
        category: String,
        apiKey: String
    ): ServiceResult<ResponseObject> {

        val result = withContext(ioDispatcher)
        {

            processCall {
                apiService.getNews(country, category, apiKey)
            }
        }

        return when (result) {
            is ServiceResult.Success -> transformResponse(result.data)
            is ServiceResult.Error -> result
        }

    }

    override suspend fun getTestCenters(
        apiKey: String,
        q: String,
        at: String,
        limit: String
    ): ServiceResult<ResponseObject> {

        val result = withContext(ioDispatcher)
        {

            processCall {
                testingapiService.getCenters(apiKey, q, at, limit)
            }
        }

        return when (result) {
            is ServiceResult.Success -> transformResponse(result.data)
            is ServiceResult.Error -> result
        }
    }


    internal fun transformResponse(response: BaseResposne): ServiceResult<BaseResposne> {
        response.apply {
            return if (!errorCode.isNullOrEmpty())
                ServiceResult.Error(
                    ServiceException(
                        response.code,
                        errorCode.firstOrNull().toString(),
                        errorMessage.firstOrNull().orEmpty()
                    )
                )
            else
                ServiceResult.Success(response)
        }
    }


    suspend fun <T> processCall(
        call: suspend () -> Response<T>
    ): ServiceResult<T> {
        val networkError = ServiceException(201, "201", "Sorry. Internet might not be connected.")
        return try {
            val serviceCallback = call()
            val body = serviceCallback.body()
            if (serviceCallback.isSuccessful && body != null) {
                ServiceResult.Success(body)
            } else if (!serviceCallback.isSuccessful && serviceCallback.errorBody() != null) {
                ServiceResult.Error(processErrorBody(serviceCallback))
            } else {
                ServiceResult.Error(
                    ServiceException(
                        serviceCallback.code(),
                        serviceCallback.code().toString(),
                        serviceCallback.message().orEmpty()
                    )
                )
            }

        } catch (exception: Exception) {
            when (exception) {
                is IOException -> {
                    ServiceResult.Error(networkError)
                }
                else -> {
                    ServiceResult.Error(ServiceException(101, "101", exception.toString()))
                }
            }
        }

    }


    private fun <T> processErrorBody(response: Response<T>): ServiceException {
        val type = object : TypeToken<BaseResposne>() {}.type
        val errorResponse: BaseResposne? = Gson().fromJson(response.errorBody()?.charStream(), type)

        if (!errorResponse?.responseMessage.isNullOrEmpty()) {
            return ServiceException(
                response.code(),
                errorResponse?.httpStatus.toString(),
                errorResponse?.responseMessage.toString()
            )

        } else {
            return ServiceException(
                response.code(),
                errorResponse?.errorCode?.first().toString(),
                errorResponse?.errorMessage?.first().toString()
            )
        }
    }
}