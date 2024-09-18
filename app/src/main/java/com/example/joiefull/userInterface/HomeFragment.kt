package com.example.joiefull.userInterface

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.ImageLoader
import coil.compose.AsyncImage
import com.example.joiefull.R
import com.example.joiefull.contentData.ClothesItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.properties.Delegates


@AndroidEntryPoint
class HomeFragment : androidx.fragment.app.Fragment() {


    private lateinit var cat: String
    private var lik by Delegates.notNull<Int>()
    private lateinit var namae: String
    private var op: Double = 0.0
    private lateinit var pic: String
    private var prix: Double = 0.0


    companion object {
        fun newInstance() = HomeFragment()
    }

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val composeView = view.findViewById<ComposeView>(R.id.compose_view_home)


        viewModel.fetchAll()

        lifecycleScope.launch {

            viewModel.fullData.collect {

                    result ->

                val fullData = result

            }

        }






        composeView.apply {

            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)

            setContent {


            }

        }

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}

@Composable
fun HomeUi(clothesData: ClothesItem) {


    Column {

        Text(
            text = clothesData.category,
            style = MaterialTheme.typography.titleMedium


            )
        Box(
            modifier = Modifier
                .height(198.dp)
                .clip(RoundedCornerShape(8.dp))
        ) {
            AsyncImage(
                model = clothesData.picture.url,
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )


            Card(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp),
                shape = RoundedCornerShape(4.dp),

            ) {
                val isClickable by remember {
                    mutableStateOf(false)
                }


                Image(
                    painter = if(isClickable) {painterResource(id = R.drawable.fav_empty)} else {
                        painterResource(id = R.drawable.fav_full)},
                    contentDescription = clothesData.picture.description,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .clickable { }


                    )



                Text(
                    text = clothesData.likes.toString(),


                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(8.dp),
                    color = Color.White,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        Row {
            Text(
                text = clothesData.name,


                )

            Spacer(modifier = Modifier.width(10.dp))


            Image(
                painter = painterResource(id = R.drawable.star),
                contentDescription = null,


                )
        }


    }


}

