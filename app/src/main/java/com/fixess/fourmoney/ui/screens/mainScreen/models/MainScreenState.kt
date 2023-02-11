package com.fixess.fourmoney.ui.screens.mainScreen.models

import com.fixess.fourmoney.dataclasses.charts.Category
import com.fixess.fourmoney.enums.Type

enum class MainScreenSubState{
    NoPurchase,MainSubScreen,Loading
}
data class MainScreenState (
    val mainScreenSubState: MainScreenSubState = MainScreenSubState.Loading,
    var listOfCategories : List<Category> = listOf(),
    var sortedListOfCategories : List<Category> = listOf(Category(type = Type.FOOD)),
    var totalMoneySpent: Float = 0f

)
