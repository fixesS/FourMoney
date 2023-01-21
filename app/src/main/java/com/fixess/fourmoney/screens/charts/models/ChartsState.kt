package com.fixess.fourmoney.screens.charts.models

enum class ChartsSubState{
    CircleChart,GraphChart,List
}

data class ChartsState(
    val chartsSubState : ChartsSubState = ChartsSubState.CircleChart
)