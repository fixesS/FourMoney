package com.fixess.fourmoney

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.animation.Crossfade
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fixess.fourmoney.database.PurchaseRepository
import com.fixess.fourmoney.navigation.NavigationTree
import com.fixess.fourmoney.ui.screens.charts.Charts
import com.fixess.fourmoney.ui.screens.charts.ChartsViewModel
import com.fixess.fourmoney.ui.screens.donate.Donate
import com.fixess.fourmoney.ui.screens.donate.DonateViewModel
import com.fixess.fourmoney.ui.screens.mainScreen.MainScreenViewModel
import com.fixess.fourmoney.ui.screens.mainScreen.models.MainScreenEvent
import com.fixess.fourmoney.ui.screens.registerNewPurchase.RegisterNewPurchase
import com.fixess.fourmoney.ui.screens.registerNewPurchase.RegisterNewPurchaseViewModel
import com.fixess.fourmoney.ui.theme.FourMoneyTheme
import com.fixess.testapp.MainScreen
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var purchaseRepository: PurchaseRepository
    @Inject
    lateinit var gson: Gson

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val  navController = rememberNavController()
            FourMoneyTheme() {
                NavHost(navController = navController, startDestination = NavigationTree.Main.name){
                    composable(NavigationTree.Main.name){
                        val mainScreenViewModel : MainScreenViewModel by lazy {
                            val viewModel : MainScreenViewModel  by viewModels()
                            viewModel.purchaseRepository = purchaseRepository
                            viewModel
                        }
                        mainScreenViewModel.obtainEvent(MainScreenEvent.Initial)
                        MainScreen(mainScreenViewModel = mainScreenViewModel, navController = navController)

                    }
                    composable(NavigationTree.RegisterNewPurchase.name){
                        val registerNewPurchaseViewModel : RegisterNewPurchaseViewModel by lazy {
                            val viewModel : RegisterNewPurchaseViewModel by viewModels()
                            viewModel.purchaseRepository = purchaseRepository
                            viewModel.gson = gson
                            viewModel
                        }
                         RegisterNewPurchase(registerNewPurchaseViewModel,navController)
                    }
                    composable(NavigationTree.Charts.name){
                        val chartsViewModel : ChartsViewModel by lazy {
                            val viewModel : ChartsViewModel by viewModels()
                            viewModel.purchaseRepository = purchaseRepository
                            viewModel.gson = gson
                            viewModel
                        }
                        Charts(chartsViewModel = chartsViewModel, navController = navController)
                    }
                    composable(NavigationTree.Donate.name){
                        val donateViewModel  = hiltViewModel<DonateViewModel>()
                        Donate(donateViewModel = donateViewModel)
                    }
                }
            }
        }
    }
}
