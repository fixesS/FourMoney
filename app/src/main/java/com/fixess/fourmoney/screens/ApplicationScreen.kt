package com.fixess.fourmoney.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fixess.fourmoney.database.dao.DAO
import com.fixess.fourmoney.navigation.NavigationTree
import com.fixess.fourmoney.screens.charts.Charts
import com.fixess.fourmoney.screens.charts.ChartsModel
import com.fixess.fourmoney.screens.mainScreen.MainScreenViewModel
import com.fixess.fourmoney.screens.registerNewPurchase.RegisterNewPurchase
import com.fixess.fourmoney.screens.registerNewPurchase.RegisterNewPurchaseModel
import com.fixess.testapp.MainScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ApplicationScreen(){
    val  navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavigationTree.Main.name){
        composable(NavigationTree.Main.name){
            var mainScreenViewModel = hiltViewModel<MainScreenViewModel>()
            MainScreen(mainScreenViewModel = mainScreenViewModel, navController = navController)
        }
        composable(NavigationTree.RegisterNewPurchase.name){
            var registerNewPurchaseModel = hiltViewModel<RegisterNewPurchaseModel>()
            RegisterNewPurchase(registerNewPurchaseModel = registerNewPurchaseModel, navController = navController)
        }
        composable(NavigationTree.Charts.name){
            var chartsModel = hiltViewModel<ChartsModel>()
            Charts(chartsModel = chartsModel)
        }
    }
}