package com.fixess.fourmoney.screens.charts.widget

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fixess.fourmoney.dataclasses.charts.PieChartSlice
import com.fixess.fourmoney.screens.charts.ChartsViewModel
import com.fixess.fourmoney.screens.charts.models.ChartsEvent
import com.fixess.fourmoney.tools.IntMonthToStringMonthConverter
import com.google.gson.Gson
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview
fun PurchaseCard(
    chartsViewModel: ChartsViewModel = ChartsViewModel(),
    selectedSlice: PieChartSlice = PieChartSlice(),
    gson: Gson = Gson(),
    onDeletePurchase: (PieChartSlice) -> Unit
){
    BackHandler(enabled = true) {
        chartsViewModel.obtainEvent(ChartsEvent.toCharts)
    }
    Surface(modifier = Modifier.fillMaxSize()){
        Column(horizontalAlignment = Alignment.CenterHorizontally){
            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(3.dp)
                .height(60.dp),onClick = { chartsViewModel.obtainEvent(ChartsEvent.toCharts) }) {
                Row(verticalAlignment = Alignment.Bottom){
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    Text(text = "Назад", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }

            }
            Row(horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth()){
                Image(painter = painterResource(id = selectedSlice.type.icon), contentDescription = "icon",colorFilter = ColorFilter.tint(selectedSlice.type.color), contentScale = ContentScale.Crop, modifier = Modifier
                    .width(110.dp)
                    .height(110.dp)
                    .padding(5.dp))
                Column() {
                    Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()){
                        Text(text = "Категория", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Text(text = selectedSlice.type.tag, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = selectedSlice.color)
                    }
                    Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()){
                        Text(text = "Стоимость", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Text(text = selectedSlice.weight.toString(), fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Green)
                    }
                }
            }
            Column(verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()){
                Text(text = "Дата покупки", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                val date = gson.fromJson(selectedSlice.timestamp,LocalDate::class.java)
                Text(text = "${date.dayOfMonth} ${IntMonthToStringMonthConverter().convert(date.monthValue)} ${date.year} года", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom) {
                Button(modifier = Modifier
                    .fillMaxWidth()
                    .padding(3.dp)
                    .height(60.dp),colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.errorContainer),onClick = { onDeletePurchase(selectedSlice) }) {
                    Row(){
                        Icon(Icons.Filled.Delete, contentDescription = "delete")
                        //Text(text = "Удалить")
                    }

                }
            }
        }
    }
}