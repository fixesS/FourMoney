package com.fixess.fourmoney.ui.screens.donate

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.fixess.fourmoney.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Donate(donateViewModel: DonateViewModel){
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()
    Surface{
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = rememberAsyncImagePainter(model = R.drawable.anime_heart, imageLoader = imageLoader), contentDescription = "love_image", modifier = Modifier
                .width(400.dp)
                .height(300.dp))
            Text(text = "Спасибо, что используете FourMoney!",fontSize = 25.sp,textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold)
            Column(verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxHeight()) {
                Text(text = "Контакты:",fontSize = 25.sp,textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold)
                Column(horizontalAlignment = Alignment.Start) {
                    SelectionContainer() {
                        Text("Telegram: @fixesS",fontSize = 25.sp,textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold)
                    }

                }
            }
        }
    }
}