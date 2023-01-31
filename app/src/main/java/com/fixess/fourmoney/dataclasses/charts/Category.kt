package com.fixess.fourmoney.dataclasses.charts

import androidx.compose.ui.graphics.Color
import com.fixess.fourmoney.enums.Type


data class Category(
    val type: Type = Type.UNKNOWN,
    val color: Color = type.color,
    val sumOfWeights: Float = 0f
)
