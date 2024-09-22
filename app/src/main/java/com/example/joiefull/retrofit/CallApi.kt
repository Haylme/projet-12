package com.example.joiefull.retrofit

import com.example.joiefull.contentData.Clothes
import com.example.joiefull.contentData.ClothesItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

object CallApi {

    suspend fun fetchData(): List<ClothesItem> {
        val service: ApiService = ApiService.retrofit.create(ApiService::class.java)
        return withContext(Dispatchers.IO) {
            val response: Response<Clothes> = service.getvalue()

            if (response.isSuccessful) {
                val clothes: Clothes = response.body() ?: throw IllegalStateException("Received null as response")
                val clothesList: List<ClothesItem> = clothes.toList()
                clothesList.sortedBy { it.id }
            } else {
                throw IllegalStateException(
                    "Failed to fetch data: ${response.errorBody()?.string()}"
                )
            }
        }
    }
}
