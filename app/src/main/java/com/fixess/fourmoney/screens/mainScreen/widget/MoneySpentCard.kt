package com.fixess.testapp

import androidx.compose.animation.*
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fixess.fourmoney.R

@Preview(showBackground = true)
@Composable
fun MoneySpentCard(initMoney: Int = 100){
    var money by remember { mutableStateOf(0) }
    val animatedMoney by animateIntAsState(targetValue = money)
    Card(backgroundColor = Color.White, elevation = 20.dp, shape = RoundedCornerShape(10.dp),modifier = Modifier.padding(5.dp)){
            Column(modifier = Modifier.padding(8.dp)) {
                Row(horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier
                    .padding(1.dp)
                    .fillMaxWidth()){
                    Image(painter = painterResource(id = R.drawable.coin), contentDescription ="1" , colorFilter = ColorFilter.tint(Color.Gray), contentScale = ContentScale.Crop, modifier = Modifier
                        .width(40.dp)
                        .height(40.dp))
                    Text("Денег потрачено",fontSize = 14.sp,color = Color.DarkGray, modifier = Modifier.align(Alignment.Bottom))
                }

                Text("$animatedMoney", fontSize = 30.sp, color = Color.Black, fontWeight = FontWeight.Bold)

            }

    }
}