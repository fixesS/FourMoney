package com.fixess.fourmoney.screens.charts.models

import com.fixess.fourmoney.screens.registerNewPurchase.models.RegisterNewPurchaseEvent

sealed class ChartsEvent {
    object updateSlices: ChartsEvent()
}