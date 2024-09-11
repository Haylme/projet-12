package com.example.joiefull.repository

import com.example.joiefull.contentData.Clothes
import com.example.joiefull.retrofit.CallApi

class Repository {


    suspend fun dataFetch(): Clothes{

        return CallApi.fetchData()


    }




}