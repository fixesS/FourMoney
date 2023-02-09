package com.fixess.testapp



import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fixess.fourmoney.dataclasses.charts.Category
import io.reactivex.Observable
import me.bytebeats.views.charts.pie.PieChart
import me.bytebeats.views.charts.pie.PieChartData
import me.bytebeats.views.charts.pie.render.SimpleSliceDrawer
import me.bytebeats.views.charts.simpleChartAnimation

@Preview(showBackground = true)
@Composable
fun ChartCard(list: List<Category> = listOf()) {


    var listOfSlicesForUI : MutableList<PieChartData.Slice> = ArrayList()
    var takenList = list.take(5).toMutableList()
    Card(shape = MaterialTheme.shapes.large, modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier.padding(8.dp).fillMaxWidth()) {
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