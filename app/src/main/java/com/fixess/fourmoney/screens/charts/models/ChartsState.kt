package com.fixess.fourmoney.screens.charts.models

import com.fixess.fourmoney.dataclasses.charts.Category
import com.fixess.fourmoney.dataclasses.charts.PieChartSlice

enum class ChartsSubState{
    CircleChart,Purchase,Category
}
enum class ChartsViewState{
    Slices,Categories
}

data class ChartsState(
    var selectedSlice : PieChartSlice = PieChartSlice(),
    var selectedCategory: Category = Category(),
    val chartsViewState: ChartsViewState = ChartsViewState.Categories,
    val chartsSubState : ChartsSubState = ChartsSubState.CircleChart,
    var listOfSlices : MutableList<PieChartSlice> = ArrayList(),
    var listOfCategories : MutableList<Category> = ArrayList()
)