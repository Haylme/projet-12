package com.example.joiefull.userInterface.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.joiefull.contentData.ClothesItem
import com.example.joiefull.contentData.Picture
import com.example.joiefull.contentData.RateContent
import com.example.joiefull.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: Repository) : ViewModel() {


    private val _fullData = MutableStateFlow<List<ClothesItem>>(emptyList())
    val fullData: StateFlow<List<ClothesItem>> = _fullData.asStateFlow()


    val rateContentFlow: StateFlow<List<RateContent>> = repository.rateContentFlow


    fun fetchAll() {

        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.dataFetch()

            if (response.isNotEmpty()) {
                _fullData.value = response
            }

        }
    }

    fun getRate() {
        repository.getRatings()

    }


    fun rate(clothesId: Int, usersRating: List<RateContent>): Double {

        val response: Double
        var sum = 0.0
        val totalSize = usersRating.size
        val dataSize = usersRating.filter { it.id == clothesId }.size

        if (totalSize > 0) {
            for (item in usersRating) {
                if (item.id == clothesId) {

                    sum += item.starsRating
                }
            }
            response = sum / dataSize


        } else {
            response = 0.0


        }
        return response

    }

    fun selectById(item: List<ClothesItem>): ArrayList<ClothesItem> {
        val result = arrayListOf<ClothesItem>()
        for (id in item.indices) {
            for (i in item) {
                if (i.id == id) {
                    val clothesData = ClothesItem(
                        category = i.category,
                        id = i.id,
                        likes = i.likes,
                        name = i.name,
                        original_price = i.original_price,
                        picture = Picture(description = i.picture.description, url = i.picture.url),
                        price = i.price
                    )
                    result.add(clothesData)
                    break


                }
            }
        }

        return result
    }


    private val _newId = MutableStateFlow<Int?>(null)
    var newId:StateFlow<Int?> = _newId.asStateFlow()


    fun fetchId (id: Int){

        viewModelScope.launch(Dispatchers.IO) {


            _newId.value = id



        }



    }





}






