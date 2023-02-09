package com.fixess.fourmoney.screens.mainScreen.models

import com.fixess.fourmoney.dataclasses.charts.Category
import com.fixess.fourmoney.enums.Type

data class MainScreenState (
    val status : String = "",
    var listOfCategories : List<Category> = listOf(),
    var sortedListOfCategories : List<Category> = listOf(Category(type = Type.FOOD)),
    var totalMoneySpent: Float = 0f

)
