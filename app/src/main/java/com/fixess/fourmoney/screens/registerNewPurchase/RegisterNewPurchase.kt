package com.fixess.fourmoney.screens.registerNewPurchase

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.fixess.fourmoney.enums.Type
import com.fixess.fourmoney.navigation.NavigationTree
import com.fixess.fourmoney.screens.registerNewPurchase.models.DialogSubState
import com.fixess.fourmoney.screens.registerNewPurchase.models.RegisterNewPurchaseEvent
import com.fixess.fourmoney.screens.registerNewPurchase.models.RegisterNewPurchaseState
import com.fixess.fourmoney.screens.registerNewPurchase.widgets.DateDialog
import com.fixess.fourmoney.screens.registerNewPurchase.widgets.ErrorDialog
import com.fixess.fourmoney.screens.registerNewPurchase.widgets.TypeDialog
import com.fixess.fourmoney.tools.IntMonthToStringMonthConverter
import java.util.*


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RegisterNewPurchase(registerNewPurchaseViewModel: RegisterNewPurchaseViewModel, navController: NavController) {
    val viewState = registerNewPurchaseViewModel.viewState.observeAsState(RegisterNewPurchaseState())
    var textFiledDate by remember { mutableStateOf(TextFieldValue("")) }
    var textFiledType by remember { mutableStateOf(TextFieldValue("")) }
    var textFiledMoney by remember { mutableStateOf(TextFieldValue("")) }

    LaunchedEffect(key1 = registerNewPurchaseViewModel){
        registerNewPurchaseViewModel.obtainEvent(RegisterNewPurchaseEvent.setStatesToDefault)
    }

    Surface{
        with(viewState.value) {
            Column(verticalArrangement = Arrangement.Top, modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .padding(3.dp)
                        .fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween, horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Введите данные о покупке", fontSize = 27.sp, fontWeight = FontWeight.Bold)
                    Column(modifier = Modifier.fillMaxWidth()){
                        OutlinedTextField(value = textFiledDate,
                            placeholder = {Text("Нажмите, чтобы выбрать дату")},
                            onValueChange = {date -> textFiledDate = date},
                            enabled = false,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                                .clickable {
                                    registerNewPurchaseViewModel.obtainEvent(
                                        RegisterNewPurchaseEvent.setSubStateDate
                                    )
                                }
                        )
                        OutlinedTextField(value = textFiledType,
                            placeholder = {Text("Нажмите, чтобы выбрать категорию")},
                            onValueChange = {type -> textFiledType = type},
                            enabled = false,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                                .clickable {
                                    registerNewPurchaseViewModel.obtainEvent(
                                        RegisterNewPurchaseEvent.setSubStateType
                                    )
                                }
                        )
                        OutlinedTextField(value = textFiledMoney,
                            label = {Text("Нажмите, чтобы вписать сумму")},
                            onValueChange = {money ->
                                textFiledMoney = money
                                val parsedMoney = money.text.toFloatOrNull()
                                Log.e("textfilemoney",money.text)
                                Log.e("parsedMoney",parsedMoney.toString())
                                if(parsedMoney == null){
                                    registerNewPurchaseViewModel.obtainEvent(RegisterNewPurchaseEvent.saveMoney(0f))
                                }else{
                                    registerNewPurchaseViewModel.obtainEvent(RegisterNewPurchaseEvent.saveMoney(parsedMoney))
                                }
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                        )
                    }
                    when(dialogSubState){
                        DialogSubState.None -> {}
                        DialogSubState.Date -> {
                            DateDialog(
                                onDismiss = {registerNewPurchaseViewModel.obtainEvent(RegisterNewPurchaseEvent.setSubStateNone)},
                                onDateSelected = {
                                    registerNewPurchaseViewModel.obtainEvent(RegisterNewPurchaseEvent.setSubStateNoneAndSaveDate(it))
                                    textFiledDate = TextFieldValue("${it.dayOfMonth} ${IntMonthToStringMonthConverter().convert(it.monthValue)} ${it.year} года")
                                }
                            )
                        }
                        DialogSubState.Type -> {
                            TypeDialog(
                                selectedIndex =  selectedTypeIndex,
                                onPositiveButtonClicked = {
                                    textFiledType = TextFieldValue(it.tag)
                                    registerNewPurchaseViewModel.obtainEvent(RegisterNewPurchaseEvent.setSubStateNoneAndSaveType(it))},
                                onSelectItem = {registerNewPurchaseViewModel.obtainEvent(RegisterNewPurchaseEvent.setSelectedTypeItem(it))},
                                onDismiss = {registerNewPurchaseViewModel.obtainEvent(RegisterNewPurchaseEvent.setSubStateNone)}
                            )
                        }
                        DialogSubState.Error ->{
                            ErrorDialog(
                                onDismiss = {registerNewPurchaseViewModel.obtainEvent(RegisterNewPurchaseEvent.setSubStateNone)}
                            )
                        }
                    }
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(3.dp)
                            .height(60.dp),
                        onClick = {
                            if(registerNewPurchaseViewModel.viewState.value!!.type == Type.UNKNOWN){
                                registerNewPurchaseViewModel.obtainEvent(RegisterNewPurchaseEvent.setSubStateError)
                            }else{
                                navController.navigate(NavigationTree.Main.name) { popUpTo(NavigationTree.Main.name) { inclusive = true } }
                                registerNewPurchaseViewModel.obtainEvent(RegisterNewPurchaseEvent.savePurchase)
                            }
                        },
                        content = {
                            Row(verticalAlignment = Alignment.CenterVertically){
                                Text(
                                    text = "Завершить",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                Icon(
                                    Icons.Filled.Done,
                                    contentDescription = "Next step"
                                )
                            }
                        }
                    )
                }
            }
        }
    }
    
}

