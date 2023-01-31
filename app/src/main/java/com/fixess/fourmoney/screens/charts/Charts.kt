package com.fixess.fourmoney.screens.charts

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fixess.fourmoney.dataclasses.charts.PieChartSlice
import com.fixess.fourmoney.enums.Type
import com.fixess.fourmoney.screens.charts.models.ChartsEvent
import com.fixess.fourmoney.screens.charts.models.ChartsState
import me.bytebeats.views.charts.pie.PieChart
import me.bytebeats.views.charts.pie.PieChartData
import me.bytebeats.views.charts.pie.render.SimpleSliceDrawer
import me.bytebeats.views.charts.simpleChartAnimation

@Composable
fun Charts(chartsModel: ChartsModel){

    val viewState = chartsModel.viewState.observeAsState(ChartsState())
    var  list: List<PieChartSlice> = viewState.value.listOfSlices
    var listOfSlices : MutableList<PieChartData.Slice> = ArrayList()

    with(viewState.value){
        Column(verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {

            Box(modifier = Modifier.size(160.dp).padding(10.dp)){
                list.forEach {
                    listOfSlices.add(PieChartData.Slice(it.weight,it.color))
                }
                PieChart(
                    pieChartData = PieChartData(slices = listOfSlices),
                    animation = simpleChartAnimation(),
                    sliceDrawer = SimpleSliceDrawer()
                )
            }

            LazyColumn{
                itemsIndexed(list){ index,item ->
                    Card(elevation = 3.dp, shape = RoundedCornerShape(10.dp),modifier = Modifier.padding(5.dp)){
                        Row(modifier = Modifier
                            .padding(3.dp)
                            .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                            Text(text = item.type.tag, fontSize = 15.sp)
                            Text(text = item.weight.toInt().toString(), fontSize = 15.sp)
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