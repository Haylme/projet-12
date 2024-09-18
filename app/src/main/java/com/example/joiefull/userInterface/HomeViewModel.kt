package com.example.joiefull.userInterface

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.joiefull.contentData.Clothes
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


    private val _fullData = MutableStateFlow(Clothes())
    val fullData: StateFlow<Clothes> = _fullData.asStateFlow()


    val rateContentFlow: StateFlow<List<RateContent>> = repository.rateContentFlow


    fun fetchAll() {

        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.dataFetch()


            if (response.isNotEmpty()) {

                _fullData.value = response

            }


        }


    }


    fun rate(clothesId: Int, usersRating: List<RateContent>): Int {

        var sum = 0
        val totalSize = usersRating.size



        for (item in usersRating) {
            if (item.id == clothesId) {

                sum += item.starsRating
            }
        }
        val response = sum / totalSize

        return response
    }

    fun selectById(

        item: ArrayList<ClothesItem>
    ): List<ClothesItem> {
        val result = mutableListOf<ClothesItem>()

        for (id in 0 until item.size) {
            for (i in item) {
                if (i.id == id) {
                    val clothesData = ClothesItem(
                        category = i.category,
                        id = i.id,
                        likes = i.likes,
                        name = i.name,
                        originalPrice = i.originalPrice,
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



}



