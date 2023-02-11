package com.fixess.fourmoney.screens.mainScreen.models

sealed class MainScreenEvent{
    object toLoading: MainScreenEvent()
    object toMainSub: MainScreenEvent()
    object toNoPurchase: MainScreenEvent()
    object AddNewBuyClicked: MainScreenEvent()
    object SetChartsSlices: MainScreenEvent()
    object FindMoneySpent: MainScreenEvent()
    object SetSortedList: MainScreenEvent()
    object  Initial: MainScreenEvent()
}
