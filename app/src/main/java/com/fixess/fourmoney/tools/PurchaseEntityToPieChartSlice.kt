package com.fixess.fourmoney.tools

import com.fixess.fourmoney.database.entities.PurchaseEntity
import com.fixess.fourmoney.dataclasses.charts.PieChartSlice
import com.fixess.fourmoney.enums.Type

class PurchaseEntityToPieChartSlice {
    fun convert(entity: PurchaseEntity): PieChartSlice{
        val type : Type = Type.findById(entity.typeId!!)
        val slice = PieChartSlice(
            type = type,
            color = type.color,
            weight = entity.money!!
        )
        return slice
    }
}