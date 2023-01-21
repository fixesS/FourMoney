package com.fixess.testapp


import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fixess.fourmoney.dataclasses.charts.PieChartSlice
import com.fixess.fourmoney.enums.Type
import me.bytebeats.views.charts.pie.PieChart
import me.bytebeats.views.charts.pie.PieChartData
import me.bytebeats.views.charts.pie.render.SimpleSliceDrawer
import me.bytebeats.views.charts.simpleChartAnimation

@Preview(showBackground = true)
@Composable
fun ChartCard( list: List<PieChartSlice> = listOf(
    PieChartSlice(Type.CLOTHES),
    PieChartSlice(Type.FOOD),
    PieChartSlice(Type.PETROL),
    PieChartSlice(Type.HEALTH),
    PieChartSlice(Type.RENT)
),
    onChartsClicked:() -> Unit = {}) {

    var listOfSlices : MutableList<PieChartData.Slice> = ArrayList()



    Card(elevation = 20.dp, shape = RoundedCornerShape(10.dp),modifier = Modifier.padding(5.dp).clickable(indication = null, interactionSource = MutableInteractionSource(), onClick = { onChartsClicked() })){
        Column(modifier = Modifier.padding(8.dp)) {
            Box(modifier = Modifier.height(80.dp)){
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
                            Text(text = item.weight.toString(), fontSize = 15.sp)
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