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
import com.fixess.fourmoney.navigation.NavigationTree
import com.fixess.fourmoney.screens.registerNewPurchase.models.DialogSubState
import com.fixess.fourmoney.screens.registerNewPurchase.models.RegisterNewPurchaseEvent
import com.fixess.fourmoney.screens.registerNewPurchase.models.RegisterNewPurchaseState
import com.fixess.fourmoney.screens.registerNewPurchase.widgets.DateDialog
import com.fixess.fourmoney.screens.registerNewPurchase.widgets.TypeDialog
import com.fixess.fourmoney.tools.IntMonthToStringMonthConverter
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.listItemsSingleChoice
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.util.*


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RegisterNewPurchase(registerNewPurchaseViewModel: RegisterNewPurchaseViewModel, navController: NavController) {
    val viewState = registerNewPurchaseViewModel.viewState.observeAsState(RegisterNewPurchaseState())
    val dialogStateDate = rememberMaterialDialogState()
    //val dialogStateType = rememberMaterialDialogState()
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
//                    val dialogDate = MaterialDialog(LocalContext.current).datePicker{ dialog, datetime ->
//                        val date11 = datetime.time.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
//                        Log.e("Date",date11.toString())
//
//                    }
//                    val dialogType = MaterialDialog(LocalContext.current).listItemsSingleChoice(items = Type.getListOfTypes()) { dialog, index, text ->
//                        Log.e("type", text.toString())
//                    }
                    Text(text = "Введите данные о покупке", fontSize = 27.sp, fontWeight = FontWeight.Bold)
                    Column(modifier = Modifier.fillMaxWidth()){
                        TextField(value = textFiledDate,
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
                        TextField(value = textFiledType,
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
                        TextField(value = textFiledMoney,
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
                            dialogStateDate.show()
                            DateDialog(
                                registerNewPurchaseViewModel = registerNewPurchaseViewModel,
                                onDateSelected = {
                                    textFiledDate = TextFieldValue("${it.dayOfMonth} ${IntMonthToStringMonthConverter().convert(it.monthValue)} ${it.year} года")
                                }
                            )
                        }
                        DialogSubState.Type -> {
                            dialogStateDate.hide()
                            TypeDialog(
                            registerNewPurchaseViewModel = registerNewPurchaseViewModel,
                            selectedIndex =  selectedTypeIndex,
                            onPositiveButtonClicked = {
                            textFiledType = TextFieldValue(it.tag)
                            })
                        }
                    }
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(3.dp)
                            .height(60.dp),
                        onClick = {
                            navController.navigate(NavigationTree.Main.name) { popUpTo(NavigationTree.Main.name) { inclusive = true } }
                            registerNewPurchaseViewModel.obtainEvent(RegisterNewPurchaseEvent.savePurchase)
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

