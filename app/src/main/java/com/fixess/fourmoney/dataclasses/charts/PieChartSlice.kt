package com.fixess.fourmoney.dataclasses.charts

import androidx.compose.ui.graphics.Color
import com.fixess.fourmoney.enums.Type
import java.time.LocalDateTime
import kotlin.random.Random

data class PieChartSlice(
    val id: Long = 0,
    val timestamp: String = "0",
    val type: Type = Type.UNKNOWN,
    val color: Color = type.color,
    val weight: Float = 100f
)
