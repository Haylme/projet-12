package com.example.joiefull.retrofit

import com.example.joiefull.contentData.Clothes
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {

    @GET("clothes")
    suspend fun getvalue (): Response<Clothes>





    companion object {



        const val api = "https://raw.githubusercontent.com/OpenClassrooms-Student-Center/D-velopper-une-interface-accessible-en-Jetpack-Compose/main/api/"

        val retrofit : Retrofit = Retrofit.Builder()
            .baseUrl(api)
            .addConverterFactory(GsonConverterFactory.create())

            .build()


    }

}