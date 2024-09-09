package com.example.joiefull.retrofit

import com.example.joiefull.contentData.Clothes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

object CallApi {

    suspend fun fetchData(): Clothes {
        val service: ApiService = ApiService.retrofit.create(ApiService::class.java)
        return withContext(Dispatchers.IO) {
            val response: Response<Clothes> = service.getvalue()

                if (response.isSuccessful) {
                    response.body() ?: throw IllegalStateException("Received null as response")
                } else {
                    throw IllegalStateException(
                        "Failed to fetch data: ${
                            response.errorBody()?.string()
                        }"
                    )
                }
        }
    }
}