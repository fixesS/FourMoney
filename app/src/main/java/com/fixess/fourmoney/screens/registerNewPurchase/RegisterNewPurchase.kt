package com.fixess.fourmoney.screens.registerNewPurchase

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.fixess.fourmoney.database.FourMoneyDatabase
import com.fixess.fourmoney.database.dao.DAO
import com.fixess.fourmoney.enums.Type
import com.fixess.fourmoney.navigation.NavigationTree
import com.fixess.fourmoney.screens.registerNewPurchase.models.RegisterNewPurchaseEvent
import com.fixess.fourmoney.screens.registerNewPurchase.models.RegisterNewPurchaseState
import com.fixess.fourmoney.screens.registerNewPurchase.models.RegisterNewPurchaseSubState
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.input
import com.vanpra.composematerialdialogs.listItemsSingleChoice
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDateTime
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RegisterNewPurchase(registerNewPurchaseModel: RegisterNewPurchaseModel, navController: NavController) {
    val viewState = registerNewPurchaseModel.viewState.observeAsState(RegisterNewPurchaseState())
    val dialogState = rememberMaterialDialogState()

    with(viewState.value) {
        Column(verticalArrangement = Arrangement.Top, modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .background(Color.LightGray)
                    .fillMaxWidth()
            ) {
                Icon(Icons.Filled.KeyboardArrowLeft,
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(40.dp)
                        .clickable {
                            when (registerNewPurchaseSubState) {
                                RegisterNewPurchaseSubState.DatePicker -> navController.navigate(
                                    NavigationTree.Main.name
                                ) { popUpTo(NavigationTree.Main.name) { inclusive = true } }
                                else -> registerNewPurchaseModel.obtainEvent(RegisterNewPurchaseEvent.previousState)
                            }
                        })
            }
            Column(
                modifier = Modifier
                    .padding(3.dp)
                    .fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column() {

                    Row(verticalAlignment = Alignment.Bottom) {
                        Crossfade(targetState = viewState.value.registerNewPurchaseSubState) { state ->
                            when (state) {
                                RegisterNewPurchaseSubState.DatePicker -> Text(
                                    text = "1",
                                    fontSize = 70.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.LightGray
                                )
                                RegisterNewPurchaseSubState.TimePicker -> Text(
                                    text = "2",
                                    fontSize = 70.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.LightGray
                                )
                                RegisterNewPurchaseSubState.TypePicker -> Text(
                                    text = "3",
                                    fontSize = 70.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.LightGray
                                )
                                RegisterNewPurchaseSubState.MoneyPicker -> Text(
                                    text = "4",
                                    fontSize = 70.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.LightGray
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(5.dp))
                        Column {
                            Text(
                                text = "Шаг",
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.LightGray
                            )
                            Spacer(modifier = Modifier.height(13.dp))
                        }
                    }

                    Crossfade(targetState = viewState.value.registerNewPurchaseSubState) { state ->
                        when (state) {
                            RegisterNewPurchaseSubState.DatePicker -> Text(
                                text = "Выберите дату, когда вы потратили деньги",
                                fontSize = 24.sp,
                                color = Color.DarkGray
                            )
                            RegisterNewPurchaseSubState.TimePicker -> Text(
                                text = "Выберите время, когда вы потратили деньги",
                                fontSize = 24.sp,
                                color = Color.DarkGray
                            )
                            RegisterNewPurchaseSubState.TypePicker -> Text(
                                text = "Выберите тип, куда вы потратили деньги",
                                fontSize = 24.sp,
                                color = Color.DarkGray
                            )
                            RegisterNewPurchaseSubState.MoneyPicker -> Text(
                                text = "Введите сумму денег, которую вы потратили",
                                fontSize = 24.sp,
                                color = Color.DarkGray
                            )
                        }
                    }
                }

                //TODO
                Text(text = "TODO Gif-инструкция", modifier = Modifier.background(Color.Green))
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.height(125.dp)
                ) {
                    Crossfade(targetState = viewState.value.registerNewPurchaseSubState) { state ->
                        when (state) {
                            RegisterNewPurchaseSubState.DatePicker -> Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(60.dp),
                                onClick = { dialogState.show() }) {
                                Text(
                                    text = "Выберите дату",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            RegisterNewPurchaseSubState.TimePicker -> Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(60.dp),
                                onClick = { dialogState.show() }) {
                                Text(
                                    text = "Выберите время",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            RegisterNewPurchaseSubState.TypePicker -> Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(60.dp),
                                onClick = { dialogState.show() }) {
                                Text(
                                    text = "Выберите тип",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            RegisterNewPurchaseSubState.MoneyPicker -> Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(60.dp),
                                onClick = { dialogState.show() }) {
                                Text(
                                    text = "Введите сумму денег",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                    Crossfade(targetState = viewState.value.registerNewPurchaseSubState) { state ->
                        when (state) {
                            RegisterNewPurchaseSubState.MoneyPicker -> Button(
                                modifier = Modifier.fillMaxWidth().height(60.dp),
                                onClick = {
                                    navController.navigate(NavigationTree.Main.name) { popUpTo(NavigationTree.Main.name) { inclusive = true } }
                                    registerNewPurchaseModel.obtainEvent(RegisterNewPurchaseEvent.savePurchase)
                                }) {
                                Row(verticalAlignment = Alignment.Bottom) {
                                    Text(
                                        text = "Завершить",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Icon(
                                        Icons.Filled.KeyboardArrowRight,
                                        contentDescription = "Next step"
                                    )
                                }
                            }
                            else -> Button(
                                modifier = Modifier.fillMaxWidth().height(60.dp),
                                onClick = { registerNewPurchaseModel.obtainEvent(RegisterNewPurchaseEvent.nextState) }) {
                                Row(verticalAlignment = Alignment.Bottom) {
                                    Text(
                                        text = "Далее",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Icon(
                                        Icons.Filled.KeyboardArrowRight,
                                        contentDescription = "Next step"
                                    )
                                }
                            }
                        }
                    }
                }
            }
            MaterialDialog(
                dialogState = dialogState,
                buttons = {
                    positiveButton("Ок")
                    negativeButton("Назад")
                }
            ) {
                when (registerNewPurchaseSubState) {
                    RegisterNewPurchaseSubState.DatePicker -> {
                        datepicker { date ->
                            viewState.value.date = date
                            Log.e("ДАТА ВЫБРАНА", viewState.value.date.toString())
                        }
                    }
                    RegisterNewPurchaseSubState.TimePicker -> {
                        timepicker { time ->
                            Log.e("ВРЕМЯ ВЫБРАНО", time.minute.toString())
                            viewState.value.time = time
                        }
                    }
                    RegisterNewPurchaseSubState.TypePicker -> {
                        listItemsSingleChoice(
                            list = Type.getListOfTypes()
                        ) { typeId ->
                            Log.e("ТИП ВЫБРАН", typeId.toString())
                            viewState.value.type = Type.findById(typeId)
                        }
                    }
                    RegisterNewPurchaseSubState.MoneyPicker -> {
                        input(label = "Сумма", placeholder = "Например: 100") { inputString ->
                            Log.e("СУММА ВЫБРАНА", inputString)
                            var money = inputString.toFloatOrNull()
                            if(money == null){
                                money = 0f
                            }
                            viewState.value.money = money
                        }
                    }
                }
            }
        }
    }
}

