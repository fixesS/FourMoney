package com.fixess.fourmoney.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fixess.fourmoney.navigation.NavigationTree
import com.fixess.fourmoney.screens.charts.Charts
import com.fixess.fourmoney.screens.charts.ChartsViewModel
import com.fixess.fourmoney.screens.mainScreen.MainScreenViewModel
import com.fixess.fourmoney.screens.registerNewPurchase.RegisterNewPurchase
import com.fixess.fourmoney.screens.registerNewPurchase.RegisterNewPurchaseViewModel
import com.fixess.testapp.MainScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable

fun ApplicationScreen(){
    val  navController = rememberNavController()

//    NavHost(navController = navController, startDestination = NavigationTree.Main.name){
//        composable(NavigationTree.Main.name){
//            var mainScreenViewModel = hiltViewModel<MainScreenViewModel>()
//            MainScreen(mainScreenViewModel = mainScreenViewModel, navController = navController)
//        }
//        composable(NavigationTree.RegisterNewPurchase.name){
//            var registerNewPurchaseViewModel = hiltViewModel<RegisterNewPurchaseViewModel>()
//            RegisterNewPurchase(registerNewPurchaseViewModel = registerNewPurchaseViewModel, navController = navController)
//        }
//        composable(NavigationTree.Charts.name){
//            var chartsViewModel = hiltViewModel<ChartsViewModel>()
//            Charts(chartsViewModel = chartsViewModel)
//        }
//    }
}