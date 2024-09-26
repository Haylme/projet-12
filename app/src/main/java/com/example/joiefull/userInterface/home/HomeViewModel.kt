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


    fun rate(clothesId: Int, usersRating: List<RateContent>): Int {

        val response :Int
        var sum = 0
        val totalSize = usersRating.size


        if (totalSize > 0) {
            for (item in usersRating) {
                if (item.id == clothesId) {

                    sum += item.starsRating
                }
            }
             response = sum / totalSize


        }else {
            response = 0


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
                        originalPrice = i.originalPrice,
                        picture = Picture(description = i.picture.description, url = i.picture.url),
                        price = i.price
                    )
                    result.add(clothesData)
                    break


                }
            }
        }
        result.sortedBy { it.category }
        return result
    }


}



