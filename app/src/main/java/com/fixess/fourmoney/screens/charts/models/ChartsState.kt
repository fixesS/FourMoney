package com.fixess.fourmoney.screens.charts.models

import com.fixess.fourmoney.dataclasses.charts.PieChartSlice

enum class ChartsSubState{
    CircleChart,GraphChart,List
}

data class ChartsState(
    val chartsSubState : ChartsSubState = ChartsSubState.CircleChart,
    var listOfSlices : List<PieChartSlice> = listOf(PieChartSlice())
)