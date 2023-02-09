package com.fixess.fourmoney.screens.registerNewPurchase

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
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

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RegisterNewPurchase(registerNewPurchaseViewModel: RegisterNewPurchaseViewModel, navController: NavController) {
    val viewState = registerNewPurchaseViewModel.viewState.observeAsState(RegisterNewPurchaseState())
    val dialogState = rememberMaterialDialogState()

    Surface{
        with(viewState.value) {
            Column(verticalArrangement = Arrangement.Top, modifier = Modifier.fillMaxSize()) {
                Button(modifier = Modifier
                    .fillMaxWidth()
                    .padding(3.dp)
                    .height(50.dp),onClick = {
                    when (registerNewPurchaseSubState) {
                        RegisterNewPurchaseSubState.DatePicker -> navController.navigate(
                            NavigationTree.Main.name
                        ) { popUpTo(NavigationTree.Main.name) { inclusive = true } }
                        else -> registerNewPurchaseViewModel.obtainEvent(
                            RegisterNewPurchaseEvent.previousState
                        )
                    }
                },
                content = {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    Text(text = "Назад", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                })
//                Row(modifier = Modifier
//                    .background(Color.LightGray)
//                    .fillMaxWidth()) {
//                    Card (shape = RoundedCornerShape(10.dp), modifier = Modifier
//                        .padding(5.dp)
//                        .clip(RoundedCornerShape(10.dp))
//                        .clickable {
//                            when (registerNewPurchaseSubState) {
//                                RegisterNewPurchaseSubState.DatePicker -> navController.navigate(
//                                    NavigationTree.Main.name
//                                ) { popUpTo(NavigationTree.Main.name) { inclusive = true } }
//                                else -> registerNewPurchaseViewModel.obtainEvent(
//                                    RegisterNewPurchaseEvent.previousState
//                                )
//                            }
//                        }){
//                        Row( modifier = Modifier
//                            .background(Color.White)
//                            .padding(3.dp)){
//                            Icon(Icons.Filled.KeyboardArrowLeft, contentDescription = "Back", modifier = Modifier.size(40.dp))
//                            Text(text = "Назад",fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.Black)
//                            Spacer(modifier = Modifier.width(5.dp))
//                        }
//                    }
//                }
                Column(
                    modifier = Modifier
                        .padding(3.dp)
                        .fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column{
                        Row(verticalAlignment = Alignment.Bottom) {
                            Crossfade(targetState = viewState.value.registerNewPurchaseSubState) { state ->
                                when (state) {
                                    RegisterNewPurchaseSubState.DatePicker -> Text(
                                        text = "1",
                                        fontSize = 70.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    RegisterNewPurchaseSubState.TimePicker -> Text(
                                        text = "2",
                                        fontSize = 70.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    RegisterNewPurchaseSubState.TypePicker -> Text(
                                        text = "3",
                                        fontSize = 70.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    RegisterNewPurchaseSubState.MoneyPicker -> Text(
                                        text = "4",
                                        fontSize = 70.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(5.dp))
                            Column {
                                Text(
                                    text = "Шаг",
                                    fontSize = 25.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(13.dp))
                            }
                        }

                        Crossfade(targetState = viewState.value.registerNewPurchaseSubState) { state ->
                            when (state) {
                                RegisterNewPurchaseSubState.DatePicker -> Text(
                                    text = "Выберите дату, когда вы потратили деньги",
                                    fontSize = 24.sp
                                )
                                RegisterNewPurchaseSubState.TimePicker -> Text(
                                    text = "Выберите время, когда вы потратили деньги",
                                    fontSize = 24.sp
                                )
                                RegisterNewPurchaseSubState.TypePicker -> Text(
                                    text = "Выберите тип, куда вы потратили деньги",
                                    fontSize = 24.sp
                                )
                                RegisterNewPurchaseSubState.MoneyPicker -> Text(
                                    text = "Введите сумму денег, которую вы потратили",
                                    fontSize = 24.sp
                                )
                            }
                        }
                    }
                    MaterialDialog(

                        properties= DialogProperties(),
                        dialogState = dialogState,
                        buttons = {
                            positiveButton("Ок")
                            negativeButton("Назад")
                        }
                    ) {
                        when (registerNewPurchaseSubState) {
                            RegisterNewPurchaseSubState.DatePicker -> {
                                datepicker{ date ->
                                    viewState.value.date = date
                                }
                            }
                            RegisterNewPurchaseSubState.TimePicker -> {
                                timepicker(is24HourClock = true) { time ->
                                    viewState.value.time = time
                                }
                            }
                            RegisterNewPurchaseSubState.TypePicker -> {
                                listItemsSingleChoice(
                                    list = Type.getListOfTypes()
                                ) { typeId ->
                                    viewState.value.type = Type.findById(typeId)
                                }
                            }
                            RegisterNewPurchaseSubState.MoneyPicker -> {
                                input(keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),label = "Сумма", placeholder = "Например: 100") { inputString ->
                                    var money = inputString.toFloatOrNull()
                                    if(money == null){
                                        money = 0f
                                    }
                                    viewState.value.money = money
                                }
                            }
                        }
                    }
                    Column(
                        verticalArrangement = Arrangement.Bottom,
                        modifier = Modifier.height(125.dp)
                    ) {
                        Crossfade(targetState = viewState.value.registerNewPurchaseSubState) { state ->
                            when (state) {
                                RegisterNewPurchaseSubState.DatePicker -> Button(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(3.dp)
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
                                        .padding(3.dp)
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
                                        .padding(3.dp)
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
                                        .padding(3.dp)
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
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(3.dp)
                                        .height(60.dp),
                                    onClick = {
                                        navController.navigate(NavigationTree.Main.name) { popUpTo(NavigationTree.Main.name) { inclusive = true } }
                                        registerNewPurchaseViewModel.obtainEvent(RegisterNewPurchaseEvent.savePurchase)
                                    },
                                content = {
                                    Text(
                                        text = "Завершить",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Icon(
                                        Icons.Filled.KeyboardArrowRight,
                                        contentDescription = "Next step"
                                    )
                                })
                                else -> Button(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(3.dp)
                                        .height(60.dp),
                                    onClick = { registerNewPurchaseViewModel.obtainEvent(RegisterNewPurchaseEvent.nextState) },
                                    content = {
                                        Text(
                                            text = "Далее",
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Icon(
                                            Icons.Filled.KeyboardArrowRight,
                                            contentDescription = "Next step"
                                        )
                                    })

                            }
                        }
                    }
                }
            }
        }
    }
    
}

