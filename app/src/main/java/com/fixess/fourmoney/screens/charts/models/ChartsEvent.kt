package com.fixess.fourmoney.screens.charts.models

import com.fixess.fourmoney.dataclasses.charts.Category
import com.fixess.fourmoney.dataclasses.charts.PieChartSlice
import com.fixess.fourmoney.screens.registerNewPurchase.models.RegisterNewPurchaseEvent

sealed class ChartsEvent {
    data class setSelectedCategory(val category: Category): ChartsEvent()
    data class setSelectedPurchase(val purchase: PieChartSlice): ChartsEvent()

    data class deletePurchase(val purchase: PieChartSlice): ChartsEvent()
    object initial: ChartsEvent()
    object updateSlices: ChartsEvent()
    object updateCategories: ChartsEvent()
    object toCategories: ChartsEvent()
    object toSlices: ChartsEvent()
    object toPurchase: ChartsEvent()
    object toCategory: ChartsEvent()
    object toCharts: ChartsEvent()
}