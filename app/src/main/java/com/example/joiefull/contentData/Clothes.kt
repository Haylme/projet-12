package com.example.joiefull.contentData

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable





@Serializable
class Clothes : ArrayList<ClothesItem>()

@Serializable
data class ClothesItem(
    @SerialName("category") val category: String,
    @SerialName("id") val id: Int,
    @SerialName("likes") val likes: Int,
    @SerialName("name") val name: String,
    @SerialName("original_price") val originalPrice: Double,
    @SerialName("picture") val picture: Picture,
    @SerialName("price") val price: Double
)

@Serializable
data class Picture(
    @SerialName("description") val description: String,
    @SerialName("url") val url: String
)
