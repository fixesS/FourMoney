package com.fixess.testapp


import  android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.Crossfade
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
import com.fixess.fourmoney.screens.mainScreen.models.*
import com.fixess.fourmoney.screens.mainScreen.widget.*
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

    with(viewState.value){
        Crossfade(targetState = mainScreenSubState) {state ->
            when(state){
                MainScreenSubState.Loading -> { LoadingScreen()}
                MainScreenSubState.MainSubScreen -> {
                    MainSubScreen(
                        listOfCategories = listOfCategories,
                        moneySpent = moneySpent,
                        sortedListOfCategories = sortedListOfCategories,
                        onFindMoneySpent = { mainScreenViewModel.obtainEvent(MainScreenEvent.FindMoneySpent) },
                        onNavigateToCharts = { navController.navigate(NavigationTree.Charts.name) },
                        onNavigateToRegisterNewPurchase = { navController.navigate(NavigationTree.RegisterNewPurchase.name) },
                        onNavigateToDonate = {navController.navigate(NavigationTree.Donate.name)}
                    )
                }
                MainScreenSubState.NoPurchase ->{ NoPurchaseScreen(onNavigateToRegisterNewPurchase = {navController.navigate(NavigationTree.RegisterNewPurchase.name) })}
            }
        }
    }
}