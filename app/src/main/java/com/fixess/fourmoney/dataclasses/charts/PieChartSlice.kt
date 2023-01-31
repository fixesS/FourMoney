package com.fixess.fourmoney.dataclasses.charts

import androidx.compose.ui.graphics.Color
import com.fixess.fourmoney.enums.Type
import kotlin.random.Random

data class PieChartSlice(
    val type: Type = Type.UNKNOWN,
    val color: Color = type.color,
    val weight: Float = 100f
)
