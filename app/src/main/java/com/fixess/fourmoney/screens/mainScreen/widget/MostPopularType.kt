package com.fixess.testapp.screens.MainScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fixess.fourmoney.R

@Preview(showBackground = true)
@Composable
fun MostPopularType(){
    Card(backgroundColor = Color.White, elevation = 20.dp, shape = RoundedCornerShape(10.dp),modifier = Modifier.padding(5.dp)){
        Column(modifier = Modifier.padding(5.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally){
            Text(text = "Самый популярный тип трат:", fontSize = 14.sp, color = Color.DarkGray,textAlign = TextAlign.Center)
            Row(modifier = Modifier.padding(2.dp), horizontalArrangement = Arrangement.Center){
                Image(painter = painterResource(id = R.drawable.petrol), contentDescription = "petrol",colorFilter = ColorFilter.tint(Color.Gray), contentScale = ContentScale.Crop, modifier = Modifier
                    .width(50.dp)
                    .height(50.dp))
            }
        }
    }
}