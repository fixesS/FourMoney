package com.fixess.fourmoney.screens.charts

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.CanvasHolder
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fixess.fourmoney.dataclasses.charts.Category
import com.fixess.fourmoney.dataclasses.charts.PieChartSlice
import com.fixess.fourmoney.enums.Type
import com.fixess.fourmoney.screens.charts.models.ChartsEvent
import com.fixess.fourmoney.screens.charts.models.ChartsState
import com.fixess.fourmoney.screens.charts.models.ChartsViewState
import me.bytebeats.views.charts.pie.PieChart
import me.bytebeats.views.charts.pie.PieChartData
import me.bytebeats.views.charts.pie.render.SimpleSliceDrawer
import me.bytebeats.views.charts.simpleChartAnimation

@Composable
fun Charts(chartsModel: ChartsModel){

    val viewState = chartsModel.viewState.observeAsState(ChartsState())
    var listOfSlices: List<PieChartSlice> = viewState.value.listOfSlices
    var listOfCategories: List<Category> = viewState.value.listOfCategories
    var listOfSlicesForUI : MutableList<PieChartData.Slice> = ArrayList()


    with(viewState.value){
        Column(verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
            Row(horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth()){
                Button(onClick = {
                    chartsModel.obtainEvent(ChartsEvent.toCategories)
                    chartsModel.obtainEvent(ChartsEvent.updateCategories)
                }) {
                    Text(fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        text = "Категории")
                }
                //Spacer(modifier = Modifier.width(10.dp))
                Button(onClick = {
                    chartsModel.obtainEvent(ChartsEvent.toSlices)
                    chartsModel.obtainEvent(ChartsEvent.updateSlices)
                }) {
                    Text(fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        text = "Попкупки")
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
                        Log.e("LIST SLICES",listOfSlices.toString())
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

            LazyColumn{
                when(chartsViewState){
                    ChartsViewState.Slices -> {
                        itemsIndexed(listOfSlices){ index,item ->
                            Card(elevation = 3.dp, shape = RoundedCornerShape(10.dp),modifier = Modifier.padding(5.dp)){
                                Row(modifier = Modifier
                                    .padding(3.dp)
                                    .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                                    Text(text = item.type.tag, fontSize = 15.sp)
                                    Text(text = item.weight.toString(), fontSize = 15.sp)
                                    Box(modifier = Modifier.clip(RoundedCornerShape(10.dp))){
                                        Icon(painter = ColorPainter(item.color),contentDescription = "color of type",tint = item.color)
                                    }
                                }
                            }
                        }
                    }
                    ChartsViewState.Categories -> {
                        itemsIndexed(listOfCategories){ index,item ->
                            Card(elevation = 3.dp, shape = RoundedCornerShape(10.dp),modifier = Modifier.padding(5.dp)){
                                Row(modifier = Modifier
                                    .padding(3.dp)
                                    .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                                    Text(text = item.type.tag, fontSize = 15.sp)
                                    Text(text = item.sumOfWeights.toString(), fontSize = 15.sp)
                                    Box(modifier = Modifier.clip(RoundedCornerShape(10.dp))){
                                        Icon(painter = ColorPainter(item.color),contentDescription = "color of type",tint = item.color)
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