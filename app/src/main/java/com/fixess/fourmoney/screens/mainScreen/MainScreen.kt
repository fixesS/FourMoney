package com.fixess.testapp


import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.fixess.fourmoney.dataclasses.charts.Category
import com.fixess.fourmoney.navigation.NavigationTree
import com.fixess.fourmoney.screens.mainScreen.MainScreenViewModel
import com.fixess.fourmoney.screens.mainScreen.models.MainScreenEvent
import com.fixess.fourmoney.screens.mainScreen.models.MainScreenState
import com.fixess.testapp.screens.MainScreen.MostPopularType
import kotlinx.coroutines.launch
import org.xml.sax.SAXParseException

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(mainScreenViewModel: MainScreenViewModel,navController: NavController){

    val viewState = mainScreenViewModel.viewState.observeAsState(MainScreenState())
    var listOfCategories: List<Category> = viewState.value.listOfCategories

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
        val scaffoldState = rememberScaffoldState()
        val scope = rememberCoroutineScope()
        Scaffold(
            scaffoldState = scaffoldState,
            bottomBar = {
                BottomAppBar(
                    cutoutShape = RoundedCornerShape(10.dp)){
                    IconButton(onClick = {
                        scope.launch {
                            scaffoldState.drawerState.apply{
                                if (isClosed) open() else close()
                            }
                        }
                    }) { Icon(Icons.Filled.Menu, contentDescription = "Меню") }
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = {
                    }) { Icon(Icons.Filled.Favorite, contentDescription = "Поддержать") }
                }
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                              navController.navigate(NavigationTree.RegisterNewPurchase.name)
                    },
                    content = {Icon(Icons.Filled.Add, contentDescription = "Добавить трату") },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .height(40.dp)
                        .width(100.dp)

                )
            },
            drawerContent = {
                Text("hey fuck you")
            },
            isFloatingActionButtonDocked = true,
            floatingActionButtonPosition = FabPosition.Center
        ) {
            Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Top) {
                Row(modifier = Modifier
                    .padding(1.dp)
                    .height(220.dp)){
                    Box(modifier = Modifier.weight(1f)){
                        mainScreenViewModel.obtainEvent(MainScreenEvent.FindMoneySpent)
                        Column {

                            Box(modifier = Modifier.weight(1f)){
                                MoneySpentCard(viewState.value.totalMoneySpent)
                            }
                            Box(modifier = Modifier.weight(1f)){
                                MostPopularType(viewState.value.listOfCategories.sortedByDescending { it.sumOfWeights })
                            }
                        }
                    }
                    Box(modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()){
                        ChartCard(listOfCategories,onChartsClicked = { navController.navigate(NavigationTree.Charts.name)})
                    }
                }
            }
        }
    }
}