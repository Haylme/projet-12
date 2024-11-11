package com.example.joiefull.userInterface.detail

import android.annotation.SuppressLint
import android.content.Context
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityManager
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.joiefull.R
import com.example.joiefull.contentData.ClothesItem
import com.example.joiefull.responsive.WindowInfo
import com.example.joiefull.responsive.rememberWindowInfo
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


@Composable
fun DetailScreen(
    clothesId: Int, viewmodel: DetailViewModel = hiltViewModel(), navController: NavController
) {
    viewmodel.fetchAll()
    viewmodel.getRate()

    val clothesFullData = viewmodel.fullData.collectAsState()


    val fullDataClothes = clothesFullData.value


    val dataById = viewmodel.selectById(clothesId, fullDataClothes)


    DetailId(
        itemClothe = dataById,
        clothesId = clothesId,
        navController = navController,

        )


}


@SuppressLint("DefaultLocale")
@Composable

fun DetailId(
    itemClothe: List<ClothesItem>,
    clothesId: Int,
    viewModel: DetailViewModel = hiltViewModel(),
    navController: NavController
) {
    var textFieldValue by remember { mutableStateOf("") }
    var isTextFieldVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val snackbarHostState = remember { SnackbarHostState() }

    val snackScope = rememberCoroutineScope()

    val accessibilityManager = remember {
        context.getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
    }


    val accessibility = remember {
        context.getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
    }
    val isAccessibilityEnabled = accessibility.isEnabled


    val windowInfo = rememberWindowInfo()
    if (windowInfo.screenWidth == WindowInfo.WindowType.Phone) {


        val scope = rememberCoroutineScope()

        viewModel.getRate()
        val rateData = viewModel.rateContentFlow.collectAsState(initial = emptyList())

        val rateValue = rateData.value

        val clotheData = itemClothe.find { it.id == clothesId }
        var rate by remember { mutableDoubleStateOf(0.0) }

        val checkIfEmpty = rateValue.any { it.id == clothesId }




        if (checkIfEmpty) {
            val initialRate = viewModel.rate(clothesId, rateValue)
            rate = String.format("%.1f", initialRate).toDouble()
        } else {

            rate = 0.0
        }


        var starsCount by remember { mutableIntStateOf(0) }

        var text by remember { mutableStateOf("") }
        var textFieldInput by remember { mutableStateOf("") }
        val starStates = remember { mutableStateListOf(false, false, false, false, false) }






        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {


            item {
                ConstraintLayout(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val (image, nameClothes, priceClothes, originalPriceClothes, rating, star, categoryClothes, textField, editTextField, profilpicture, allStars) = createRefs()

                    Box(modifier = Modifier
                        .size(width = 328.dp, height = 431.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .constrainAs(image) {
                            top.linkTo(parent.top)

                        }

                    ) {
                        if (clotheData != null) {
                            AsyncImage(
                                model = clotheData.picture.url,
                                contentDescription = clotheData.picture.description,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clickable {
                                        isTextFieldVisible = false

                                    },
                                contentScale = ContentScale.Crop,


                                )
                        }

                        Icon(painter =

                        painterResource(id = R.drawable.arrow_back),
                            contentDescription = null,
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(12.dp)
                                .clickable {

                                    navController.popBackStack()


                                }
                                .semantics {
                                    contentDescription = "Retour à l'écran d'accueil"

                                }


                        )



                        Icon(painter = painterResource(id = R.drawable.share),
                            contentDescription = null,

                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(12.dp)
                                .semantics {
                                    liveRegion = LiveRegionMode.Polite
                                    contentDescription =
                                        "Veuillez écrire un message et appuyé sur entré pour partager votre avis sur cette pièce"

                                }
                                .clickable {

                                    isTextFieldVisible = !isTextFieldVisible

                                })

                        if (isTextFieldVisible) {

                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.Black.copy(alpha = 0.5f))
                            )


                            OutlinedTextField(
                                value = textFieldValue,
                                onValueChange = { newText -> textFieldValue = newText },
                                maxLines = 4,
                                modifier = Modifier
                                    .widthIn(328.dp)
                                    .heightIn(53.dp)
                                    .align(Alignment.Center)
                                    .background(Color.White)

                                    .onKeyEvent {
                                        if (it.key == Key.Enter || it.nativeKeyEvent.keyCode == android.view.KeyEvent.KEYCODE_ENTER) {

                                            shareButton(
                                                context = context,
                                                itemId = clothesId,
                                                message = textFieldValue,
                                                url = "https://www.example.com/item"
                                            )

                                            textFieldValue = ""

                                            isTextFieldVisible = false

                                            snackScope.launch {
                                                snackbarHostState.showSnackbar("Vous avez partagé votre avis")

                                            }




                                            true
                                        } else {
                                            false
                                        }
                                    },
                                shape = RoundedCornerShape(18.dp),
                                placeholder = {

                                    Text(
                                        "Partagez ici vos impressions",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color.Black,
                                        fontSize = 14.sp,
                                    )
                                },

                                )
                        }



                        Card(
                            modifier = Modifier

                                .align(Alignment.BottomEnd)
                                .padding(20.dp)
                                .height(31.dp)
                                .widthIn(70.dp),
                            shape = RoundedCornerShape(34.dp),
                        ) {
                            var isClickable by remember { mutableStateOf(false) }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                if (clotheData != null) {
                                    Image(painter = if (!isClickable) {
                                        painterResource(id = R.drawable.fav_empty)
                                    } else {
                                        painterResource(id = R.drawable.fav_full)
                                    },
                                        contentDescription = clotheData.picture.description,
                                        modifier = Modifier
                                            .semantics {
                                                liveRegion = LiveRegionMode.Polite
                                                contentDescription = if (isClickable) {
                                                    "Ajouté aux favoris"
                                                } else {
                                                    "Retiré des favoris"
                                                }
                                            }

                                            .clickable {
                                                isClickable = !isClickable
                                            })
                                }

                                Spacer(modifier = Modifier.width(8.dp))

                                if (clotheData != null) {
                                    Text(
                                        text = clotheData.likes.toString(),
                                        color = Color.Black,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }
                    }

                    Column(verticalArrangement = Arrangement.spacedBy((-8).dp),
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .constrainAs(categoryClothes) {
                                top.linkTo(image.bottom)
                            }) {

                        Row(
                            modifier = Modifier

                                .width(328.dp), horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            if (clotheData != null) {
                                Text(
                                    text = clotheData.name,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier
                                        .layoutId(nameClothes)
                                        .weight(1f)
                                        .padding(start = 4.dp)
                                )
                            }


                            Row(
                                modifier = Modifier.padding(end = 8.dp)

                            ) {

                                Image(
                                    painter = painterResource(id = R.drawable.star),
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = rate.toString(),
                                    fontSize = 18.sp,
                                    modifier = Modifier.layoutId(rating)
                                )
                            }
                        }


                        Row(

                            modifier = Modifier
                                .padding(top = 8.dp)
                                .width(328.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            if (clotheData != null) {
                                Text(
                                    text = clotheData.price.toString(),
                                    fontSize = 18.sp,
                                    modifier = Modifier
                                        .layoutId(priceClothes)
                                        .padding(start = 4.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(18.dp))

                            if (clotheData != null) {
                                Text(
                                    text = clotheData.original_price.toString(),
                                    textDecoration = TextDecoration.LineThrough,
                                    fontSize = 18.sp,
                                    modifier = Modifier
                                        .layoutId(originalPriceClothes)
                                        .padding(end = 4.dp)
                                )
                            }
                        }




                        Spacer(
                            modifier = Modifier.height(15.dp)
                        )


                        Text(text = text)


                        Row(
                            modifier = Modifier.padding(top = 12.dp)


                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.kitano),
                                contentDescription = null,
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .size(40.dp)
                                    .layoutId(profilpicture),
                                contentScale = ContentScale.Crop
                            )

                            Spacer(modifier = Modifier.width(10.dp))







                            repeat(5) { index ->

                                if (isAccessibilityEnabled) {

                                    Icon(painter = if (!starStates[index]) {
                                        painterResource(id = R.drawable.star_outline)
                                    } else {
                                        painterResource(id = R.drawable.star)
                                    },
                                        contentDescription = "Etoiles pour noter l'article sur cinq étoiles",
                                        modifier = Modifier
                                            .size(48.dp)
                                            .padding(top = 5.dp)

                                            .clickable {


                                                for (i in 0..index) {

                                                    starStates[i] = true
                                                }
                                                for (i in index + 1 until starStates.size) {

                                                    starStates[i] = false
                                                }

                                                starsCount = starStates.count { it }


                                            }
                                            .semantics {
                                                contentDescription =
                                                    " Vous avez donné une note de $starsCount étoiles"

                                            }
                                            .layoutId(allStars))
                                } else {


                                    Icon(painter = if (!starStates[index]) {
                                        painterResource(id = R.drawable.star_outline)
                                    } else {
                                        painterResource(id = R.drawable.star)
                                    },
                                        contentDescription = "Etoiles pour noter l'article sur cinq étoiles",
                                        modifier = Modifier
                                            .padding(top = 5.dp)

                                            .clickable {


                                                for (i in 0..index) {

                                                    starStates[i] = true
                                                }
                                                for (i in index + 1 until starStates.size) {

                                                    starStates[i] = false
                                                }

                                                starsCount = starStates.count { it }


                                            }
                                            .semantics {
                                                contentDescription =
                                                    " Vous avez donné une note de $starsCount étoiles"

                                            }
                                            .layoutId(allStars))


                                    if (index in 1..3) {
                                        Spacer(modifier = Modifier.width(6.dp))
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(22.dp))

                        OutlinedTextField(
                            value = textFieldInput,
                            onValueChange = { newText -> textFieldInput = newText },
                            maxLines = 8,
                            modifier = Modifier
                                .widthIn(328.dp)
                                .heightIn(53.dp)
                                .semantics {
                                    contentDescription =
                                        "Veuillez écrire un commentaire sur cet article pour partager vos impressions puis appuyez sur entrée"
                                }
                                .onKeyEvent {
                                    if (starsCount > 0 && textFieldInput.isNotEmpty()) {
                                        if (it.key == Key.Enter || it.nativeKeyEvent.keyCode == android.view.KeyEvent.KEYCODE_ENTER) {
                                            viewModel.add(clothesId, starsCount)

                                            scope.launch {
                                                val newRates = viewModel.rateContentFlow.first()
                                                val newRateValue =
                                                    viewModel.rate(clothesId, newRates)
                                                val roundedNewRateValue: Double =
                                                    String
                                                        .format("%.1f", newRateValue)
                                                        .toDouble()
                                                rate = roundedNewRateValue
                                            }

                                            text = textFieldInput
                                            textFieldInput = ""
                                            starsCount = 0

                                            for (j in starStates.indices) {
                                                starStates[j] = false

                                            }

                                            if (isAccessibilityEnabled) {
                                                val announcement =
                                                    "Vous avez envoyé votre commentaire et votre note sur l'article"

                                                fun sendAccessibilityEventWithText(message: String) {
                                                    val event = AccessibilityEvent
                                                        .obtain()
                                                        .apply {

                                                            contentDescription = message
                                                        }
                                                    accessibilityManager.sendAccessibilityEvent(
                                                        event
                                                    )
                                                }

                                                sendAccessibilityEventWithText(announcement)
                                            }

                                            true
                                        } else {
                                            false
                                        }
                                    } else {
                                        false
                                    }
                                },
                            shape = RoundedCornerShape(18.dp),
                            placeholder = {
                                Text(
                                    "Partagez ici vos impressions sur cette pièce",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.Black,
                                    fontSize = 14.sp
                                )
                            },
                            colors = OutlinedTextFieldDefaults.colors(unfocusedContainerColor = Color.Transparent)
                        )
                    }
                }
            }
        }


    } else {


        val scope = rememberCoroutineScope()

        viewModel.getRate()
        val rateData = viewModel.rateContentFlow.collectAsState()

        val rateValue = rateData.value

        var clotheData = itemClothe.find { it.id == clothesId }


        var rate by remember { mutableDoubleStateOf(0.0) }

        val checkIfEmpty = rateValue.any { it.id == clothesId }




        if (checkIfEmpty) {
            val initialRate = viewModel.rate(clothesId, rateValue)



            rate = String.format("%.1f", initialRate).toDouble()

        } else {

            rate = 0.0
        }


        var starsCount by remember { mutableIntStateOf(0) }

        var text by remember { mutableStateOf("") }
        var textFieldInput by remember { mutableStateOf("") }
        val starStates = remember { mutableStateListOf(false, false, false, false, false) }



        LaunchedEffect(clothesId) {
            clotheData = itemClothe.find { it.id == clothesId }
            text = ""
        }





        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            item {
                ConstraintLayout(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val (image, nameClothes, priceClothes, originalPriceClothes, rating, star, categoryClothes, textField, editTextField, profilpicture, allStars) = createRefs()

                    Box(modifier = Modifier
                        .size(width = 451.dp, height = 408.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .constrainAs(image) {
                            top.linkTo(parent.top)

                        }

                    ) {
                        if (clotheData != null) {
                            AsyncImage(
                                model = clotheData!!.picture.url,
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clickable {

                                        isTextFieldVisible = false
                                    },
                                contentScale = ContentScale.Crop
                            )
                        }



                        Icon(painter = painterResource(id = R.drawable.share),
                            contentDescription = null,

                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(12.dp)
                                .semantics {
                                    liveRegion = LiveRegionMode.Polite
                                    contentDescription =
                                        "Veuillez écrire un message et appuyé sur entré pour partager votre avis sur cette pièce"

                                }
                                .clickable {
                                    isTextFieldVisible = !isTextFieldVisible


                                    if (isAccessibilityEnabled) {
                                        val announcement =
                                            "Veuillez écrire un message et appuyé sur entré pour partager votre avis sur cette pièce"

                                        fun sendAccessibilityEventWithText(message: String) {
                                            val event = AccessibilityEvent
                                                .obtain()
                                                .apply {

                                                    contentDescription = message
                                                }
                                            accessibilityManager.sendAccessibilityEvent(event)
                                        }

                                        sendAccessibilityEventWithText(announcement)
                                    }

                                })


                        if (isTextFieldVisible) {

                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.Black.copy(alpha = 0.5f))
                            )


                            OutlinedTextField(
                                value = textFieldValue,
                                onValueChange = { newText -> textFieldValue = newText },
                                maxLines = 4,
                                modifier = Modifier
                                    .widthIn(328.dp)
                                    .heightIn(53.dp)
                                    .align(Alignment.Center)
                                    .background(
                                        Color.White, shape = RoundedCornerShape(18.dp)
                                    )
                                    .onKeyEvent {
                                        if (it.key == Key.Enter || it.nativeKeyEvent.keyCode == android.view.KeyEvent.KEYCODE_ENTER) {

                                            shareButton(
                                                context = context,
                                                itemId = clothesId,
                                                message = textFieldValue,
                                                url = "https://www.example.com/item"
                                            )

                                            textFieldValue = ""

                                            isTextFieldVisible = false

                                            snackScope.launch {
                                                snackbarHostState.showSnackbar("Vous avez partagé votre avis")

                                            }




                                            true
                                        } else {
                                            false
                                        }
                                    },
                                shape = RoundedCornerShape(18.dp),
                                placeholder = {

                                    Text(
                                        "Partagez ici vos impressions",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color.Black,
                                        fontSize = 14.sp,

                                        )
                                },

                                )
                        }



                        Card(
                            modifier = Modifier

                                .align(Alignment.BottomEnd)
                                .padding(20.dp)
                                .height(31.dp)
                                .widthIn(70.dp),
                            shape = RoundedCornerShape(34.dp),
                        ) {
                            var isClickable by remember { mutableStateOf(false) }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                if (clotheData != null) {
                                    Image(painter = if (!isClickable) {
                                        painterResource(id = R.drawable.fav_empty)
                                    } else {
                                        painterResource(id = R.drawable.fav_full)
                                    },
                                        contentDescription = clotheData!!.picture.description,
                                        modifier = Modifier
                                            .semantics {
                                                liveRegion = LiveRegionMode.Polite
                                                contentDescription = if (isClickable) {
                                                    "Ajouté aux favoris"
                                                } else {
                                                    "Retiré des favoris"
                                                }
                                            }
                                            .clickable {
                                                isClickable = !isClickable
                                            })
                                }

                                Spacer(modifier = Modifier.width(8.dp))

                                if (clotheData != null) {
                                    Text(
                                        text = clotheData!!.likes.toString(),
                                        color = Color.Black,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }
                    }

                    Column(verticalArrangement = Arrangement.spacedBy((-8).dp),
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .constrainAs(categoryClothes) {
                                top.linkTo(image.bottom)
                            }) {

                        Row(
                            modifier = Modifier

                                .width(451.dp), horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            if (clotheData != null) {
                                Text(
                                    text = clotheData!!.name,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier
                                        .layoutId(nameClothes)
                                        .weight(1f)
                                        .padding(start = 4.dp)
                                )
                            }


                            Row(
                                modifier = Modifier.padding(end = 8.dp)

                            ) {

                                Image(
                                    painter = painterResource(id = R.drawable.star),
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = rate.toString(),
                                    fontSize = 18.sp,
                                    modifier = Modifier.layoutId(rating)
                                )
                            }
                        }


                        Row(

                            modifier = Modifier
                                .padding(top = 8.dp)
                                .width(451.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            if (clotheData != null) {
                                Text(
                                    text = clotheData!!.price.toString(),
                                    fontSize = 18.sp,
                                    modifier = Modifier
                                        .layoutId(priceClothes)
                                        .padding(start = 4.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(18.dp))

                            if (clotheData != null) {
                                Text(
                                    text = clotheData!!.original_price.toString(),
                                    textDecoration = TextDecoration.LineThrough,
                                    fontSize = 18.sp,
                                    modifier = Modifier
                                        .layoutId(originalPriceClothes)
                                        .padding(end = 4.dp)
                                )
                            }
                        }




                        Spacer(
                            modifier = Modifier.height(15.dp)
                        )


                        Text(
                            text = text


                        )


                        Row(
                            modifier = Modifier.padding(top = 12.dp)


                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.kitano),
                                contentDescription = null,
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .size(40.dp)
                                    .layoutId(profilpicture),
                                contentScale = ContentScale.Crop
                            )

                            Spacer(modifier = Modifier.width(10.dp))

                            repeat(5) { index ->

                                if (isAccessibilityEnabled) {

                                    Icon(painter = if (!starStates[index]) {
                                        painterResource(id = R.drawable.star_outline)
                                    } else {
                                        painterResource(id = R.drawable.star)
                                    },
                                        contentDescription = "Etoiles pour noter l'article sur cinq étoiles",
                                        modifier = Modifier
                                            .size(48.dp)
                                            .padding(top = 5.dp)

                                            .clickable {


                                                for (i in 0..index) {

                                                    starStates[i] = true
                                                }
                                                for (i in index + 1 until starStates.size) {

                                                    starStates[i] = false
                                                }

                                                starsCount = starStates.count { it }


                                            }
                                            .semantics {
                                                contentDescription =
                                                    " Vous avez donné une note de $starsCount étoiles"

                                            }
                                            .layoutId(allStars))
                                } else {


                                    Icon(painter = if (!starStates[index]) {
                                        painterResource(id = R.drawable.star_outline)
                                    } else {
                                        painterResource(id = R.drawable.star)
                                    },
                                        contentDescription = "Etoiles pour noter l'article sur cinq étoiles",
                                        modifier = Modifier
                                            .padding(top = 5.dp)

                                            .clickable {


                                                for (i in 0..index) {

                                                    starStates[i] = true
                                                }
                                                for (i in index + 1 until starStates.size) {

                                                    starStates[i] = false
                                                }

                                                starsCount = starStates.count { it }


                                            }
                                            .semantics {
                                                contentDescription =
                                                    " Vous avez donné une note de $starsCount étoiles"

                                            }
                                            .layoutId(allStars))


                                    if (index in 1..3) {
                                        Spacer(modifier = Modifier.width(6.dp))
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(22.dp))

                        OutlinedTextField(
                            maxLines = 8,

                            value = textFieldInput,
                            onValueChange = { newText -> textFieldInput = newText },
                            modifier = Modifier
                                .layoutId(textField)

                                .widthIn(451.dp)
                                .heightIn(53.dp)

                                .onKeyEvent {
                                    if (starsCount > 0) {


                                        if (it.key == Key.Enter || it.nativeKeyEvent.keyCode == android.view.KeyEvent.KEYCODE_ENTER) {
                                            viewModel.add(clothesId, starsCount)


                                            scope.launch {


                                                val newRates = viewModel.rateContentFlow.first()
                                                val newRateValue =
                                                    viewModel.rate(clothesId, newRates)
                                                val roundedNewRateValue: Double = String
                                                    .format("%.1f", newRateValue)
                                                    .toDouble()

                                                rate = roundedNewRateValue


                                            }

                                            if (isAccessibilityEnabled) {
                                                val announcement =
                                                    "Vous avez envoyé votre commentaire et votre note sur l'article"

                                                fun sendAccessibilityEventWithText(message: String) {
                                                    val event = AccessibilityEvent
                                                        .obtain()
                                                        .apply {

                                                            contentDescription = message
                                                        }
                                                    accessibilityManager.sendAccessibilityEvent(
                                                        event
                                                    )
                                                }

                                                sendAccessibilityEventWithText(announcement)
                                            }


                                            text = textFieldInput
                                            textFieldInput = ""
                                            starsCount = 0








                                            for (j in starStates.indices) {
                                                starStates[j] = false

                                            }

                                            true
                                        } else {
                                            false
                                        }
                                    } else {
                                        false
                                    }
                                },
                            shape = RoundedCornerShape(18.dp),
                            placeholder = {
                                Text(
                                    "Partagez ici vos impressions sur cette pièce",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.Black,
                                    fontSize = 14.sp
                                )

                            },

                            )


                    }
                }
            }
        }

    }


}

fun shareButton(context: Context, itemId: Int, message: String, url: String) {
    val appPageUrl = "$url/$itemId"

    val shareMessage = "$message: $appPageUrl"


    Toast.makeText(context, "Liens vers facebook partagé: $shareMessage", Toast.LENGTH_LONG)
        .show()
}


