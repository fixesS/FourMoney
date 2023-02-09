package com.fixess.fourmoney.screens.registerNewPurchase

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
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
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.listItemsSingleChoice
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RegisterNewPurchase(registerNewPurchaseViewModel: RegisterNewPurchaseViewModel, navController: NavController) {
    val viewState = registerNewPurchaseViewModel.viewState.observeAsState(RegisterNewPurchaseState())
    val dialogStateDate = rememberMaterialDialogState()
    val dialogStateType = rememberMaterialDialogState()
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
                        TextField(value = textFiledDate,
                            placeholder = {Text("Нажмите, чтобы выбрать дату")},
                            onValueChange = {date -> textFiledDate = date},
                            enabled = false,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                                .clickable { dialogStateDate.show() }
                        )
                        TextField(value = textFiledType,
                            placeholder = {Text("Нажмите, чтобы выбрать категорию")},
                            onValueChange = {type -> textFiledType = type},
                            enabled = false,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                                .clickable { dialogStateType.show() }
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
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                        )
                    }
                    MaterialDialog(
                        backgroundColor = MaterialTheme.colorScheme.background,
                        shape = MaterialTheme.shapes.extraLarge,
                        properties= DialogProperties(),
                        dialogState = dialogStateDate,
                        buttons = {
                            positiveButton("Ок")
                            negativeButton("Назад")
                        },
                        content = {
                            datepicker(colors = DatePickerDefaults.colors(
                                headerBackgroundColor = MaterialTheme.colorScheme.background,
                                headerTextColor = MaterialTheme.colorScheme.primaryContainer,
                                calendarHeaderTextColor = MaterialTheme.colorScheme.onBackground,
                                dateActiveBackgroundColor = MaterialTheme.colorScheme.primaryContainer,
                                dateActiveTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                dateInactiveBackgroundColor = MaterialTheme.colorScheme.background,
                                dateInactiveTextColor = MaterialTheme.colorScheme.onBackground
                            )){ date ->
                                registerNewPurchaseViewModel.obtainEvent(RegisterNewPurchaseEvent.saveDate(date))
                                textFiledDate = TextFieldValue(date.toString())
                            }
                        })
                    MaterialDialog(
                        backgroundColor = MaterialTheme.colorScheme.background,
                        shape = MaterialTheme.shapes.extraLarge,
                        properties= DialogProperties(),
                        dialogState = dialogStateType,
                        buttons = {
                            positiveButton("Ок")
                            negativeButton("Назад")
                        },
                        content = {
                            listItemsSingleChoice(
                                list = Type.getListOfTypes()
                            ) { typeId ->
                                val type = Type.findById(typeId)
                                registerNewPurchaseViewModel.obtainEvent(RegisterNewPurchaseEvent.saveType(type))
                                textFiledType = TextFieldValue(type.tag)
                            }
                        }
                    )
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

