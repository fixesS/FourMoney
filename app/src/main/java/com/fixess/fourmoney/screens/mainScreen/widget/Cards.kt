package com.fixess.fourmoney.screens.mainScreen.widget

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fixess.fourmoney.dataclasses.charts.Category
import com.fixess.fourmoney.enums.Type
import io.reactivex.Observable
import me.bytebeats.views.charts.pie.PieChart
import me.bytebeats.views.charts.pie.PieChartData
import me.bytebeats.views.charts.pie.render.SimpleSliceDrawer
import me.bytebeats.views.charts.simpleChartAnimation

@Composable
fun ChartCard(list: List<Category> = listOf()) {
    var listOfSlicesForUI : MutableList<PieChartData.Slice> = ArrayList()
    var takenList = list.take(5).toMutableList()
    Card(shape = MaterialTheme.shapes.large, modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier.padding(8.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Box(modifier = Modifier.height(80.dp)){
                Observable.fromArray(list).subscribe {
                    list.forEach {
                        listOfSlicesForUI.add(PieChartData.Slice(it.sumOfWeights,it.color))
                    }
                }
                PieChart(
                    pieChartData = PieChartData(slices = listOfSlicesForUI),
                    animation = simpleChartAnimation(),
                    sliceDrawer = SimpleSliceDrawer()
                )
            }
            LazyRow(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly){
                itemsIndexed(takenList){ index,item ->
                    Box(modifier = Modifier.clip(RoundedCornerShape(0.dp)).weight(1f)){
                        Image(painter = painterResource(id = item.type.icon), contentDescription = "icon",colorFilter = ColorFilter.tint(item.type.color), contentScale = ContentScale.Crop, modifier = Modifier
                            .width(30.dp)
                            .height(30.dp))
                    }

                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun MoneySpentCard(initMoney: Float = 100f){
    val animatedMoney by animateIntAsState(targetValue = initMoney.toInt())
    Card(shape = MaterialTheme.shapes.large, modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier.padding(8.dp)) {
            Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier
                .padding(1.dp).fillMaxWidth()){
                Text("Денег потрачено",fontSize = 20.sp, modifier = Modifier.align(Alignment.Bottom))
            }
            Row(modifier = Modifier.padding(1.dp).fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text("$animatedMoney", fontSize = 30.sp, fontWeight = FontWeight.Bold)
            }

        }

    }
}
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
            Text(text = "Самый популярный тип трат:", fontSize = 20.sp,textAlign = TextAlign.Center)
            Row(modifier = Modifier.padding(2.dp), horizontalArrangement = Arrangement.Center){
                Image(painter = painterResource(id = type.icon), contentDescription = "icon",colorFilter = ColorFilter.tint(type.color), contentScale = ContentScale.Crop, modifier = Modifier
                    .width(50.dp)
                    .height(50.dp))
            }
        }
    }
}