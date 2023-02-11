package com.fixess.fourmoney.ui.screens.mainScreen.widget

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fixess.fourmoney.dataclasses.charts.Category
import com.fixess.fourmoney.ui.screens.mainScreen.models.MenuItem
import com.fixess.fourmoney.ui.screens.mainScreen.models.MenuItemType
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainSubScreen(
    listOfCategories : List<Category>,
    moneySpent : Float,
    sortedListOfCategories : List<Category>,
    onFindMoneySpent :() -> Unit,
    onNavigateToCharts :() -> Unit,
    onNavigateToRegisterNewPurchase: () -> Unit,
    onNavigateToDonate: () -> Unit
){

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
                            icon = { Icon(menuItem.icon, contentDescription = menuItem.contentDescription) },
                            selected = menuItem == selectedItem.value,
                            onClick = {
                                scope.launch {
                                    drawerState.close()
                                }
                                when(menuItem.type){
                                    MenuItemType.Charts -> {
                                        onNavigateToCharts()
                                    }
                                    MenuItemType.Other -> {}
                                }
                            })
                    }

                }
            }
        ){
            Scaffold(
                snackbarHost = { SnackbarHost(snackbarHostState) },
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
                                          onNavigateToRegisterNewPurchase()
                                },
                                content = { Icon(Icons.Filled.Add, contentDescription = "Добавить трату") },
                                modifier = Modifier.weight(2f)
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            IconButton(onClick = {
                                onNavigateToDonate()
                                //navController.navigate(NavigationTree.Donate.name)
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
                            onFindMoneySpent()
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