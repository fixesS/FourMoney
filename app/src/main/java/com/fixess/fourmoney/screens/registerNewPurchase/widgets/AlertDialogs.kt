package com.fixess.fourmoney.screens.registerNewPurchase.widgets

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fixess.fourmoney.enums.Type
import com.fixess.fourmoney.screens.registerNewPurchase.RegisterNewPurchaseViewModel
import com.fixess.fourmoney.screens.registerNewPurchase.models.RegisterNewPurchaseEvent
import com.fixess.fourmoney.tools.IntMonthToStringMonthConverter
import com.fixess.fourmoney.tools.MillisecondsToDateConverter
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TypeDialog(
    registerNewPurchaseViewModel: RegisterNewPurchaseViewModel,
    selectedIndex: Int,
    onPositiveButtonClicked: (Type) -> Unit){
    AlertDialog(
        onDismissRequest = { registerNewPurchaseViewModel.obtainEvent(RegisterNewPurchaseEvent.setSubStateNone) },
        title = {
            Text(text = "Выберите категорию")
        },
        text = {
            LazyColumn{
                itemsIndexed(Type.getListOfTypes()){ index, it ->
                    TypeDialogItem(text = it,index = index, selectedIndex = selectedIndex, registerNewPurchaseViewModel = registerNewPurchaseViewModel)
                }
            }
        },
        dismissButton = {
            Button(onClick = { registerNewPurchaseViewModel.obtainEvent(RegisterNewPurchaseEvent.setSubStateNone) }) {
                Text(text = "Отмена")
            }
        },
        confirmButton = {
            Button(onClick = {
                val type = Type.getById(selectedIndex)
                Log.e("type",type.toString())
                registerNewPurchaseViewModel.obtainEvent(RegisterNewPurchaseEvent.setSubStateNoneAndSaveType(type))
                onPositiveButtonClicked(type)
            }) {
                Text(text = "Ок")
            }
        }
    )
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TypeDialogItem(
    text : String,
    selectedIndex: Int,
    index: Int,
    registerNewPurchaseViewModel: RegisterNewPurchaseViewModel
){

    var selected by remember { mutableStateOf(false) }
    selected = selectedIndex == index

    Card(shape = MaterialTheme.shapes.large,modifier = Modifier
        .fillMaxWidth()
        .padding(3.dp)
        .clip(MaterialTheme.shapes.large)
        .clickable {
            registerNewPurchaseViewModel.obtainEvent(
                RegisterNewPurchaseEvent.setSelectedTypeItem(
                    index
                )
            )
        }){
        Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
            RadioButton(selected = selected, onClick = {})
            Spacer(modifier = Modifier.width(20.dp))
            Text(text = text, fontSize = 18.sp, fontWeight = FontWeight.Normal)
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateDialog(
    registerNewPurchaseViewModel: RegisterNewPurchaseViewModel,
    onDateSelected: (LocalDate) -> Unit,
){
    var datePickerState = rememberDatePickerState(initialSelectedDateMillis = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
    AlertDialog(
        onDismissRequest = {
            registerNewPurchaseViewModel.obtainEvent(RegisterNewPurchaseEvent.setSubStateNone)
        },
        title = {
        },
        text = {
            DatePicker(
                datePickerState = datePickerState,
                headline = {
                    val date = MillisecondsToDateConverter().convert(datePickerState.selectedDateMillis)
                    Text(text = "${date.dayOfMonth} ${IntMonthToStringMonthConverter().convert(date.monthValue)} ${date.year} года", fontSize = 15.sp)
                },
                title = {
                    Text(text = "Выберите дату покупки")
                }
            )
        },
        dismissButton = {
            Button(onClick = { registerNewPurchaseViewModel.obtainEvent(RegisterNewPurchaseEvent.setSubStateNone) }) {
                Text(text = "Отмена")
            }
        },
        confirmButton = {
            Button(onClick = {
                val date = MillisecondsToDateConverter().convert(datePickerState.selectedDateMillis)
                onDateSelected(date)
                registerNewPurchaseViewModel.obtainEvent(RegisterNewPurchaseEvent.setSubStateNoneAndSaveDate(date))
            }) {
                Text(text = "Ок")
            }
        }
    )
}
