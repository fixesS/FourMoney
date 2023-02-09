 package com.fixess.testapp

import androidx.compose.animation.*
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
 @OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun MoneySpentCard(initMoney: Float = 100f){
    //var money by remember { mutableStateOf(initMoney) }
    var color by remember { mutableStateOf(Color.White) }
    val animatedMoney by animateIntAsState(targetValue = initMoney.toInt())
    val colorOfMoney by animateColorAsState(if(animatedMoney >= 0f && animatedMoney<5000) Color.Green else if(animatedMoney>=5000 && animatedMoney<10000) Color.Yellow else if (animatedMoney>=10000) Color.Red else Color.Blue )
    Card(shape = MaterialTheme.shapes.large, modifier = Modifier.fillMaxSize()){
            Column(modifier = Modifier.padding(8.dp)) {
                Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier
                    .padding(1.dp).fillMaxWidth()){
                    Image(painter = painterResource(id = R.drawable.coin), contentDescription ="1" , colorFilter = ColorFilter.tint(colorOfMoney), contentScale = ContentScale.Crop, modifier = Modifier
                        .width(40.dp)
                        .height(40.dp))
                    Text("Денег потрачено",fontSize = 18.sp, modifier = Modifier.align(Alignment.Bottom))
                }
                Row(modifier = Modifier.padding(1.dp).fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Text("$animatedMoney", fontSize = 30.sp, fontWeight = FontWeight.Bold)
                }

            }

    }
}