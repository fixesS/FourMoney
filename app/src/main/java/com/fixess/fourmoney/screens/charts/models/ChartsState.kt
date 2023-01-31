package com.fixess.fourmoney.screens.charts.models

import com.fixess.fourmoney.dataclasses.charts.Category
import com.fixess.fourmoney.dataclasses.charts.PieChartSlice

enum class ChartsSubState{
    CircleChart,GraphChart,List
}
enum class ChartsViewState{
    Slices,Categories
}

data class ChartsState(
    val chartsViewState: ChartsViewState = ChartsViewState.Categories,
    val chartsSubState : ChartsSubState = ChartsSubState.CircleChart,
    var listOfSlices : List<PieChartSlice> = listOf(PieChartSlice(),PieChartSlice()),
    var listOfCategories : List<Category> = listOf(Category())
)