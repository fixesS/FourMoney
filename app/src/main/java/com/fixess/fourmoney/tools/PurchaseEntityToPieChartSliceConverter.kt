package com.fixess.fourmoney.tools

import com.fixess.fourmoney.database.entities.PurchaseEntity
import com.fixess.fourmoney.dataclasses.charts.PieChartSlice
import com.fixess.fourmoney.enums.Type

class PurchaseEntityToPieChartSliceConverter {
    fun convert(entity: PurchaseEntity): PieChartSlice{
        val type : Type = Type.findById(entity.typeId!!)
        val slice = PieChartSlice(
            id = entity.purchaseId,
            timestamp = entity.timestamp!!,
            type = type,
            color = type.color,
            weight = entity.money!!
        )
        return slice
    }
}