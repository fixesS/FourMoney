package com.fixess.fourmoney.screens.charts.models

import com.fixess.fourmoney.dataclasses.charts.Category
import com.fixess.fourmoney.screens.registerNewPurchase.models.RegisterNewPurchaseEvent

sealed class ChartsEvent {
    object initial: ChartsEvent()
    object updateSlices: ChartsEvent()
    object updateCategories: ChartsEvent()
    object toCategories: ChartsEvent()
    object toSlices: ChartsEvent()
    object toPurchase: ChartsEvent()
    object toCategory: ChartsEvent()
    object toCharts: ChartsEvent()
}