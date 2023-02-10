package com.fixess.fourmoney.screens.registerNewPurchase.models

import com.fixess.fourmoney.enums.Type
import java.time.LocalDate

sealed class RegisterNewPurchaseEvent{
//    object toTimePickerClicked: RegisterNewPurchaseEvent()
//    object toTypePickerClicked: RegisterNewPurchaseEvent()
//    object toMoneyPickerClicked: RegisterNewPurchaseEvent()
//    object backToDatePicker: RegisterNewPurchaseEvent()
//    object backToTimePicker: RegisterNewPurchaseEvent()
//    object backToTypePicker: RegisterNewPurchaseEvent()
//    object nextState : RegisterNewPurchaseEvent()
//    object previousState : RegisterNewPurchaseEvent()
    data class setSelectedTypeItem(val index: Int) : RegisterNewPurchaseEvent()
    object setSubStateNone : RegisterNewPurchaseEvent()
    object setSubStateDate : RegisterNewPurchaseEvent()
    object setSubStateType : RegisterNewPurchaseEvent()

    data class setSubStateNoneAndSaveType(val type: Type): RegisterNewPurchaseEvent()
    data class setSubStateNoneAndSaveDate(val date: LocalDate): RegisterNewPurchaseEvent()

    data class saveMoney(val money: Float): RegisterNewPurchaseEvent()
    data class saveDate(val date: LocalDate): RegisterNewPurchaseEvent()
    data class saveType(val type:   Type): RegisterNewPurchaseEvent()
    object setStatesToDefault : RegisterNewPurchaseEvent()
    object savePurchase: RegisterNewPurchaseEvent()
}