package com.fixess.fourmoney.screens.mainScreen.models

sealed class MainScreenEvent{
    object AddNewBuyClicked: MainScreenEvent()
    object SetChartsSlices: MainScreenEvent()
    object FindMoneySpent: MainScreenEvent()
}
