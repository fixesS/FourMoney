package com.fixess.fourmoney.screens.registerNewPurchase.models

sealed class RegisterNewPurchaseEvent{
    object toTimePickerClicked: RegisterNewPurchaseEvent()
    object toTypePickerClicked: RegisterNewPurchaseEvent()
    object toMoneyPickerClicked: RegisterNewPurchaseEvent()
    object backToDatePicker: RegisterNewPurchaseEvent()
    object backToTimePicker: RegisterNewPurchaseEvent()
    object backToTypePicker: RegisterNewPurchaseEvent()
    object nextState : RegisterNewPurchaseEvent()
    object previousState : RegisterNewPurchaseEvent()
    object savePurchase: RegisterNewPurchaseEvent()
}