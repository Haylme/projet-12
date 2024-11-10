package com.example.joiefull.repository

import com.example.joiefull.contentData.ClothesItem
import com.example.joiefull.contentData.RateContent
import com.example.joiefull.retrofit.CallApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Singleton


@Singleton
class Repository () {

    private val _list = mutableListOf<RateContent>()
    private val _rateContentFlow = MutableStateFlow<List<RateContent>>(_list)
    val rateContentFlow : StateFlow<List<RateContent>> get()  = _rateContentFlow


    fun addRating (clotheId: Int, starsRating: Int){

        _list.add(RateContent(clotheId,starsRating))
        _rateContentFlow.value = _list.toList()

    }
    fun getRatings(): List<RateContent> {
        return rateContentFlow.value
    }



    suspend fun dataFetch(): List<ClothesItem>{

        return CallApi.fetchData()


    }






}