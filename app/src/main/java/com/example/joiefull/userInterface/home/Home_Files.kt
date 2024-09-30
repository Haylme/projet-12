package com.example.joiefull.userInterface.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.joiefull.R
import com.example.joiefull.contentData.ClothesItem
import com.example.joiefull.contentData.Picture
import com.example.joiefull.contentData.RateContent


@Composable
fun HomeDisplay(viewModel: HomeViewModel = hiltViewModel(), navController: NavController) {

    viewModel.fetchAll()
    viewModel.getRate()

    val clothesItemsState = viewModel.fullData.collectAsState()
    val rateContent = viewModel.rateContentFlow.collectAsState()


    val clothesItems = clothesItemsState.value
    val sortedItems = viewModel.selectById(clothesItems)

    val sortByCategory = sortedItems.sortedBy { it.category }

    val rateData = rateContent.value

    RecyclerView(
        itemClothe = sortByCategory,
        rateContent = rateData,
        navController =  navController

    )
}


@Composable
fun RecyclerView(

    itemClothe: List<ClothesItem>,
    rateContent: List<RateContent>,
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavController
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(12.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp)
    ) {
        items(itemClothe.size) { index ->
            val rate = viewModel.rate(clothesId = itemClothe[index].id, usersRating = rateContent)
            HomeUi(
                clothesData = itemClothe[index],
                rate = rate.toDouble(),
                navController = navController


            )
        }
    }
}


@Composable
fun HomeUi(
    clothesData: ClothesItem,
    rate: Double,

    navController: NavController
) {


    Column {

        Text(
            text = clothesData.category,
            style = MaterialTheme.typography.titleMedium


        )
        Box(
            modifier = Modifier
                .height(198.dp)

        ) {
            AsyncImage(
                model = clothesData.picture.url,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(16.dp))
                    .clickable {
                        navController.navigate("detail/${clothesData.id}")
                    }


            )


            Card(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp),
                shape = RoundedCornerShape(34.dp),

                ) {
                var isClickable by remember {
                    mutableStateOf(false)
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(8.dp)
                ) {
                    Image(
                        painter = if (!isClickable) {
                            painterResource(id = R.drawable.fav_empty)
                        } else {
                            painterResource(id = R.drawable.fav_full)
                        },
                        contentDescription = clothesData.picture.description,
                        modifier = Modifier
                            .clickable { isClickable = !isClickable }
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = clothesData.likes.toString(),
                        color = Color.White,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
        Column {


            Row {
                Text(
                    text = clothesData.name,


                    )

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

                Text(
                    text = clothesData.price.toString()


                )
                Spacer(modifier = Modifier.width(10.dp))

                Text(text = clothesData.original_price.toString())


            }
        }


    }


}

@Preview
@Composable
fun PreviewRecyclerView() {
    val sampleClothesItems = listOf(
        ClothesItem(
            category = "Shirt",
            id = 1,
            likes = 100,
            name = "Casual Shirt",
            original_price = 29.99,
            picture = Picture(description = "A casual shirt", url = ""),
            price = 19.99
        ),
        ClothesItem(
            category = "Pants",
            id = 2,
            likes = 150,
            name = "Jeans",
            original_price = 49.99,
            picture = Picture(description = "A pair of jeans", url = ""),
            price = 39.99
        )
    )

    val sampleRateContent = listOf(
        RateContent(id = 1, starsRating = 5),
        RateContent(id = 2, starsRating = 4)
    )


    val navController = rememberNavController()

    RecyclerView(
        itemClothe = sampleClothesItems,
        rateContent = sampleRateContent,
        navController = navController
    )

}