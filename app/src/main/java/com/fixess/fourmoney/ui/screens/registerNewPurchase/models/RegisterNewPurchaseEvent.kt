package com.fixess.fourmoney.ui.screens.registerNewPurchase.models

import com.fixess.fourmoney.enums.Type
import java.time.LocalDate

sealed class RegisterNewPurchaseEvent{
    data class setSelectedTypeItem(val index: Int) : RegisterNewPurchaseEvent()
    object setSubStateNone : RegisterNewPurchaseEvent()
    object setSubStateDate : RegisterNewPurchaseEvent()
    object setSubStateType : RegisterNewPurchaseEvent()
    object setSubStateError : RegisterNewPurchaseEvent()
    data class setSubStateNoneAndSaveType(val type: Type): RegisterNewPurchaseEvent()
    data class setSubStateNoneAndSaveDate(val date: LocalDate): RegisterNewPurchaseEvent()
    data class saveMoney(val money: Float): RegisterNewPurchaseEvent()
    data class saveDate(val date: LocalDate): RegisterNewPurchaseEvent()
    data class saveType(val type:   Type): RegisterNewPurchaseEvent()
    object setStatesToDefault : RegisterNewPurchaseEvent()
    object savePurchase: RegisterNewPurchaseEvent()
}