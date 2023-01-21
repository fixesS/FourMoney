package com.fixess.fourmoney.dataclasses.charts

import androidx.compose.ui.graphics.Color
import com.fixess.fourmoney.enums.Type
import kotlin.random.Random

data class PieChartSlice(
    val type: Type = Type.UNKNOWN,
    val color: Color = Color(Random.nextInt(0,255),Random.nextInt(0,255),Random.nextInt(0,255),255),
    val weight: Float = 100f
)
