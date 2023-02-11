package com.fixess.fourmoney.screens.registerNewPurchase.models

import android.os.Build
import androidx.annotation.RequiresApi
import com.fixess.fourmoney.enums.Type
import java.sql.Timestamp
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

enum class DialogSubState{
    Date,Type,None,Error
}
@RequiresApi(Build.VERSION_CODES.O)
data class RegisterNewPurchaseState (
    var dialogSubState: DialogSubState = DialogSubState.None,
    var selectedTypeIndex : Int = -2,
    var money: Float = 0f,
    var date: LocalDate = LocalDate.now(),
    var time: LocalTime = LocalTime.now(),
    var timestamp : String = "0",
    var type: Type = Type.defaultTag()
)
