package com.fixess.testapp.screens.MainScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
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
import com.fixess.fourmoney.dataclasses.charts.Category
import com.fixess.fourmoney.enums.Type

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MostPopularType(list : List<Category>){
    var type : Type
    try{
        type = list[0].type
    }catch (e : Exception){
        type = Type.UNKNOWN
    }
    //var list1 by remember { mutableStateOf(list) }
    Card(shape = MaterialTheme.shapes.large, modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier.padding(5.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally){
            Text(text = "Самый популярный тип трат:", fontSize = 18.sp,textAlign = TextAlign.Center)
            Row(modifier = Modifier.padding(2.dp), horizontalArrangement = Arrangement.Center){
                Image(painter = painterResource(id = type.icon), contentDescription = "icon",colorFilter = ColorFilter.tint(type.color), contentScale = ContentScale.Crop, modifier = Modifier
                    .width(50.dp)
                    .height(50.dp))
            }
        }
    }
}