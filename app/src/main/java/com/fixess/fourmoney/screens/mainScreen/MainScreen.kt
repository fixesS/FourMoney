package com.fixess.testapp


import  android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.fixess.fourmoney.R
import com.fixess.fourmoney.dataclasses.charts.Category
import com.fixess.fourmoney.navigation.NavigationTree
import com.fixess.fourmoney.screens.mainScreen.MainScreenViewModel
import com.fixess.fourmoney.screens.mainScreen.models.MainScreenEvent
import com.fixess.fourmoney.screens.mainScreen.models.MainScreenState
import com.fixess.fourmoney.screens.mainScreen.models.MenuItem
import com.fixess.fourmoney.screens.mainScreen.models.MenuItemType
import com.fixess.testapp.screens.MainScreen.MostPopularType
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(mainScreenViewModel: MainScreenViewModel,navController: NavController){

    val viewState = mainScreenViewModel.viewState.observeAsState(MainScreenState())

    val listOfCategories: List<Category> = viewState.value.listOfCategories
    val moneySpent : Float = viewState.value.totalMoneySpent
    val sortedListOfCategories : List<Category> = viewState.value.sortedListOfCategories

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val items = listOf(MenuItem(type = MenuItemType.Charts, title = "Список покупок"))
    val selectedItem = remember { mutableStateOf(items[0]) }



    Surface(modifier = Modifier.fillMaxSize()) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet(){
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()){
                        IconButton(onClick = {
                            scope.launch { drawerState.close() }
                        },modifier = Modifier.weight(1f)) {
                            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "back")
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Text(text= "Меню", fontWeight = FontWeight.Bold, fontSize = 20.sp,modifier = Modifier.weight(2f))
                        Spacer(modifier = Modifier.weight(1f))

                    }
                    Divider(modifier = Modifier.padding(horizontal = 10.dp))
                    items.forEach{menuItem ->
                        NavigationDrawerItem(
                            modifier = Modifier.padding(7.dp),
                            shape = MaterialTheme.shapes.large,
                            label = { Text(text = menuItem.title) },
                            icon = {Icon(menuItem.icon, contentDescription = menuItem.contentDescription)},
                            selected = menuItem == selectedItem.value,
                            onClick = {
                                scope.launch {
                                    drawerState.close()
                                }
                                when(menuItem.type){
                                    MenuItemType.Charts -> {
                                        navController.navigate(NavigationTree.Charts.name)
                                    }
                                    MenuItemType.Other -> {}
                                }
                            })
                    }

                }
            }
        ){
            Scaffold(
                snackbarHost = { SnackbarHost(snackbarHostState)},
                topBar = {
                     TopAppBar(
                         title = {
                             Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                 Row(modifier = Modifier.fillMaxWidth()){
                                     Spacer(modifier = Modifier.width(10.dp))
                                     Text(text = "FourMoney", fontWeight = FontWeight.Bold)
                                 }
                                 Divider(modifier = Modifier.padding(horizontal = 10.dp))
                             }
                         }
                     )
                },
                bottomBar = {
                    BottomAppBar(
                        content = {
                            IconButton(onClick = {
                                scope.launch { drawerState.open() }
                            },modifier = Modifier.weight(1f)) { Icon(Icons.Filled.Menu, contentDescription = "Меню") }
                            Spacer(modifier = Modifier.weight(1f))
                            FloatingActionButton(
                                shape = MaterialTheme.shapes.extraLarge,
                                onClick = {
                                    navController.navigate(NavigationTree.RegisterNewPurchase.name)
                                },
                                content = {Icon(Icons.Filled.Add, contentDescription = "Добавить трату") },
                                modifier = Modifier.weight(2f)
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            IconButton(onClick = {
                                navController.navigate(NavigationTree.Donate.name)
                            },modifier = Modifier.weight(1f)) { Icon(Icons.Filled.Favorite, contentDescription = "Контакты") }
                        }
                        //cutoutShape = RoundedCornerShape(10.dp)
                    )
                },
                floatingActionButtonPosition = FabPosition.Center,
                content = { innerPadding ->
                    Surface(modifier = Modifier.padding(innerPadding)){
                        Column(modifier = Modifier
                            .padding(7.dp)
                            .fillMaxWidth(), verticalArrangement = Arrangement.Top) {
                            Box(modifier = Modifier
                                .padding(7.dp)
                                .weight(3f)){
                                ChartCard(listOfCategories)
                            }
                            mainScreenViewModel.obtainEvent(MainScreenEvent.FindMoneySpent)
                            Box(modifier = Modifier
                                .padding(7.dp)
                                .weight(2f)){
                                MoneySpentCard(moneySpent)
                            }
                            Box(modifier = Modifier
                                .padding(7.dp)
                                .weight(2f)){
                                MostPopularType(sortedListOfCategories)
                            }
                            Spacer(modifier = Modifier.weight(4f))
                        }
                    }

                }
            )
        }
    }
}