package com.fixess.fourmoney.screens.registerNewPurchase.models

import android.os.Build
import androidx.annotation.RequiresApi
import com.fixess.fourmoney.enums.Type
import java.sql.Timestamp
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

enum class RegisterNewPurchaseSubState{
    DatePicker, TimePicker, TypePicker, MoneyPicker;

    inline fun <reified T: Enum<T>> T.next(): T {
        val values = enumValues<T>()
        val nextOrdinal = (ordinal + 1) % values.size
        return values[nextOrdinal]
    }
}
@RequiresApi(Build.VERSION_CODES.O)
data class RegisterNewPurchaseState (
    val registerNewPurchaseSubState: RegisterNewPurchaseSubState = RegisterNewPurchaseSubState.DatePicker,
    val money: Float = 0f,
    val date: LocalDate = LocalDate.now(),
    val time: LocalTime = LocalTime.now(),
    val timestamp : Timestamp = Timestamp.valueOf(LocalDateTime.now().toString()),
    val type: Type = Type.defaultTag()
)
