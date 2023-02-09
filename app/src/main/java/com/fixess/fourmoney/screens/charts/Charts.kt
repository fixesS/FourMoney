package com.fixess.fourmoney.screens.charts

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.fixess.fourmoney.dataclasses.charts.Category
import com.fixess.fourmoney.dataclasses.charts.PieChartSlice
import com.fixess.fourmoney.screens.charts.models.ChartsEvent
import com.fixess.fourmoney.screens.charts.models.ChartsState
import com.fixess.fourmoney.screens.charts.models.ChartsSubState
import com.fixess.fourmoney.screens.charts.models.ChartsViewState
import com.fixess.fourmoney.screens.charts.widget.CategoryCard
import com.fixess.fourmoney.screens.charts.widget.PurchaseCard
import me.bytebeats.views.charts.pie.PieChart
import me.bytebeats.views.charts.pie.PieChartData
import me.bytebeats.views.charts.pie.render.SimpleSliceDrawer
import me.bytebeats.views.charts.simpleChartAnimation

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Charts(chartsViewModel: ChartsViewModel, navController : NavController){

    val viewState = chartsViewModel.viewState.observeAsState(ChartsState())
    var listOfSlices: MutableList<PieChartSlice> = viewState.value.listOfSlices
    var listOfCategories: MutableList<Category> = viewState.value.listOfCategories
    var listOfSlicesForUI : MutableList<PieChartData.Slice> = ArrayList()

    with(viewState.value){
        LaunchedEffect(key1 = ChartsSubState.CircleChart){
            chartsViewModel.obtainEvent(ChartsEvent.initial)
        }
        when(chartsSubState){

            ChartsSubState.CircleChart -> {

                Surface{
                    Column(verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
                        Crossfade(targetState = chartsViewState ){ state ->
                            Row(horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth()){
                                when(state){
                                    ChartsViewState.Categories -> {
                                        Button(shape = MaterialTheme.shapes.medium, modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(3.dp)
                                            .height(60.dp), onClick = {
                                            chartsViewModel.obtainEvent(ChartsEvent.toSlices)
                                        }) {
                                            Text(fontSize = 20.sp,
                                                fontWeight = FontWeight.Bold,
                                                text = "К покупкам")
                                        }
                                    }
                                    ChartsViewState.Slices -> {
                                        Button(shape = MaterialTheme.shapes.medium, modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(3.dp)
                                            .height(60.dp),onClick = {
                                            chartsViewModel.obtainEvent(ChartsEvent.toCategories)
                                        }) {
                                            Text(fontSize = 20.sp,
                                                fontWeight = FontWeight.Bold,
                                                text = "К категориям")
                                        }
                                    }
                                }
                            }
                        }
                        Crossfade(targetState = chartsViewState , animationSpec = spring(4f)) { state ->
                            when(state){
                                ChartsViewState.Categories -> {
                                    Text(
                                        text = "Категории",
                                        fontSize = 45.sp,
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center
                                    )
                                }
                                ChartsViewState.Slices -> {
                                    Text(
                                        text = "Покупки",
                                        fontSize = 45.sp,
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                        Box(modifier = Modifier
                            .size(160.dp)
                            .padding(10.dp)){
                            when(chartsViewState){
                                ChartsViewState.Categories ->{
                                    listOfCategories.forEach {
                                        listOfSlicesForUI.add(PieChartData.Slice(it.sumOfWeights,it.color))
                                    }
                                }
                                ChartsViewState.Slices ->{
                                    listOfSlices.forEach {
                                        listOfSlicesForUI.add(PieChartData.Slice(it.weight,it.color))
                                    }
                                }
                            }

                            PieChart(
                                pieChartData = PieChartData(slices = listOfSlicesForUI),
                                animation = simpleChartAnimation(),
                                sliceDrawer = SimpleSliceDrawer()
                            )
                        }

                        Crossfade(targetState = chartsViewState){state ->
                            LazyColumn{
                                when(state){
                                    ChartsViewState.Slices -> {
                                        itemsIndexed(listOfSlices){ index,item ->
                                            Card(elevation = CardDefaults.outlinedCardElevation(), shape = CardDefaults.elevatedShape,modifier = Modifier
                                                .padding(5.dp)
                                                .clip(RoundedCornerShape(10.dp))
                                                .clickable {
                                                    chartsViewModel.setSelecterPurchase(item)// жоский костыль вроде
                                                    //chartsViewModel.obtainEvent(ChartsEvent.toPurchase)
                                                }){
                                                Row(modifier = Modifier
                                                    .padding(6.dp)
                                                    .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
                                                    Text(text = "${item.type.tag}#${item.id}", fontSize = 17.sp)
                                                    Text(text = item.weight.toString(), fontSize = 17.sp)
                                                    Box(modifier = Modifier.clip(RoundedCornerShape(0.dp))){
                                                        //Icon(painter = ColorPainter(item.color),contentDescription = "color of type",tint = item.color)
                                                        Image(painter = painterResource(id = item.type.icon), contentDescription = "icon",colorFilter = ColorFilter.tint(item.type.color), contentScale = ContentScale.Crop, modifier = Modifier
                                                            .width(30.dp)
                                                            .height(30.dp))
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    ChartsViewState.Categories -> {
                                        itemsIndexed(listOfCategories){ index,item ->
                                            Card(elevation = CardDefaults.outlinedCardElevation(), shape = CardDefaults.elevatedShape,modifier = Modifier
                                                .padding(5.dp)
                                                .clip(CardDefaults.outlinedShape)
                                                .clickable {
                                                    chartsViewModel.setSelecterCategory(item)// жоский костыль вроде
                                                    //chartsViewModel.obtainEvent(ChartsEvent.toCategory)
                                                }){
                                                Row(modifier = Modifier
                                                    .padding(6.dp)
                                                    .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
                                                    Text(text = item.type.tag, fontSize = 17.sp)
                                                    Text(text = item.sumOfWeights.toString(), fontSize = 17.sp)
                                                    Box(modifier = Modifier.clip(RoundedCornerShape(10.dp))){
                                                        Image(painter = painterResource(id = item.type.icon), contentDescription = "icon",colorFilter = ColorFilter.tint(item.type.color), contentScale = ContentScale.Crop, modifier = Modifier
                                                            .width(30.dp)
                                                            .height(30.dp))
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            ChartsSubState.Purchase -> {
                PurchaseCard(chartsViewModel = chartsViewModel, selectedSlice = viewState.value.selectedSlice,gson = chartsViewModel.gson)
            }
            ChartsSubState.Category -> {
                CategoryCard(chartsViewModel = chartsViewModel, selectedCategory = viewState.value.selectedCategory)
            }
        }
    }
}