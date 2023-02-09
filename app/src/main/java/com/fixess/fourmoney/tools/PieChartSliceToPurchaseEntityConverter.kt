package com.fixess.fourmoney.tools

import com.fixess.fourmoney.database.entities.PurchaseEntity
import com.fixess.fourmoney.dataclasses.charts.PieChartSlice

class PieChartSliceToPurchaseEntityConverter {
    fun convert(pieChartSlice: PieChartSlice): PurchaseEntity{
        return PurchaseEntity(
            purchaseId = pieChartSlice.id,
            typeId = pieChartSlice.type.id,
            money = pieChartSlice.weight,
            timestamp = pieChartSlice.timestamp
        )
    }
}