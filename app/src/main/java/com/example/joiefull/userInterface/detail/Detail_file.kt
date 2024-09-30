package com.example.joiefull.userInterface.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.joiefull.R
import com.example.joiefull.contentData.ClothesItem
import com.example.joiefull.contentData.RateContent
import kotlin.math.floor


@Composable
fun DetailScreen(clothesId: Int, viewmodel: DetailViewModel = hiltViewModel()) {
    viewmodel.fetchAll()
    viewmodel.getRate()

    val clothesFullData = viewmodel.fullData.collectAsState()

    val rateContent = viewmodel.rateContentFlow.collectAsState()

    val fullDataClothes = clothesFullData.value

    val rate = rateContent.value


    val dataById = viewmodel.selectById(clothesId, fullDataClothes)

    DetailId(itemClothe = dataById, clothesId = clothesId, rateContent = rate)


}


@Composable
fun DetailId(
    itemClothe: List<ClothesItem>,
    clothesId: Int,
    rateContent: List<RateContent>,
    viewModel: DetailViewModel = hiltViewModel()
) {


    val clotheData = itemClothe.find { it.id == clothesId }

    val rate = viewModel.rate(clothesId, rateContent)

    var starsCount by remember {
        mutableStateOf(0)
    }
    var clickStar by remember {

        mutableStateOf(false)
    }

    var text by remember {
        mutableStateOf("")
    }


    ConstraintLayout {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()


        ) {


            item {
                Box(
                    modifier = Modifier
                        .fillMaxHeight(1f)
                )
                {

                    if (clotheData != null) {
                        AsyncImage(
                            model = clotheData.picture.url,
                            contentDescription = clotheData.picture.description,
                            modifier = Modifier

                                .clip(RoundedCornerShape(18.dp))
                                .padding(10.dp),




                        )
                    }

                    Card(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(16.dp)

                        ,
                        shape = RoundedCornerShape(34.dp),

                        ) {
                        var isClickable by remember {
                            mutableStateOf(false)
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(8.dp)
                        ) {
                            if (clotheData != null) {
                                Image(
                                    painter = if (!isClickable) {
                                        painterResource(id = R.drawable.fav_empty)
                                    } else {
                                        painterResource(id = R.drawable.fav_full)
                                    },
                                    contentDescription = clotheData.picture.description,
                                    modifier = Modifier
                                        .clickable { isClickable = !isClickable }
                                )
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            if (clotheData != null) {
                                Text(
                                    text = clotheData.likes.toString(),
                                    color = Color.White,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }


                }
            }

            item {
                Column {


                    Row {
                        if (clotheData != null) {
                            Text(
                                text = clotheData.name,


                                )
                        }

                        Spacer(modifier = Modifier.width(10.dp))


                        Image(
                            painter = painterResource(id = R.drawable.star),
                            contentDescription = null,


                            )
                        Text(
                            text = rate.toString()
                        )

                    }
                    Row {

                        if (clotheData != null) {
                            Text(
                                text = clotheData.price.toString()


                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))

                        if (clotheData != null) {
                            Text(text = clotheData.original_price.toString())
                        }


                    }
                }
                Spacer(modifier = Modifier.padding(top = 10.dp))

                if (clotheData != null) {
                    Text(text = clotheData.picture.description)
                }

                Spacer(modifier = Modifier.padding(top = 10.dp))

                Row {

                    Image(
                        painter = painterResource(id = R.drawable.kitano),
                        contentDescription = null,
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(40.dp)
                    )




                    repeat(5) { index ->
                        Icon(

                            painter = if (clickStar) {
                                painterResource(id = R.drawable.star_outline)
                            } else {
                                painterResource(id = R.drawable.star)

                            },
                            contentDescription = null,
                            modifier = Modifier
                                .clickable {

                                    if (!clickStar) {
                                        starsCount = index + 1

                                    }


                                    clickStar = true
                                    viewModel.add(clothesId, starsCount)


                                }
                        )

                        if (index in 1..3) {

                            Spacer(modifier = Modifier.width(6.dp))
                        }

                    }


                }





                TextField(
                    value = text,
                    onValueChange = { newText ->
                        text = newText
                    },
                    modifier = Modifier.onKeyEvent { keyEvent ->
                        if (keyEvent.nativeKeyEvent.keyCode == android.view.KeyEvent.KEYCODE_ENTER) {
                            viewModel.add(clothesId, starsCount)
                            true
                        } else {
                            false
                        }
                    },
                    placeholder = { Text("Partagez ici vos impressions sur cette pièce") }
                )

            }
        }
    }
}


@Composable
fun ratingStar(
    modifier: Modifier = Modifier,
    rating: Double,
    stars: Int = 5,


    ) {
    val filledStars = floor(rating).toInt()
    val unfilledStars = (stars - floor(rating)).toInt()
    val halfStar = !(rating.rem(1).equals(0.0))


    Row(modifier = modifier) {
        repeat(filledStars)
        {
            Icon(painter = painterResource(id = R.drawable.star), contentDescription = null)


        }
        if (halfStar) {


            Icon(
                painter = painterResource(id = R.drawable.star_half),
                contentDescription = null,

                )
        }
        repeat(unfilledStars) {
            Icon(painter = painterResource(id = R.drawable.star_outline), contentDescription = null)

        }


    }


}