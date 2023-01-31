package com.fixess.fourmoney

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fixess.fourmoney.database.PurchaseRepository
import com.fixess.fourmoney.navigation.NavigationTree
import com.fixess.fourmoney.screens.charts.Charts
import com.fixess.fourmoney.screens.charts.ChartsModel
import com.fixess.fourmoney.screens.charts.models.ChartsEvent
import com.fixess.fourmoney.screens.mainScreen.MainScreenViewModel
import com.fixess.fourmoney.screens.registerNewPurchase.RegisterNewPurchase
import com.fixess.fourmoney.screens.registerNewPurchase.RegisterNewPurchaseModel
import com.fixess.testapp.MainScreen
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
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
//        val list = purchaseRepository.getAllPurchases().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                it.forEach {
//                    Log.e("DB", it.toString())
//                }
//            },{
//                Log.e("ERROR",it.toString())
//            })



        setContent {
            val  navController = rememberNavController()

            NavHost(navController = navController, startDestination = NavigationTree.Main.name){
                composable(NavigationTree.Main.name){
                    val mainScreenViewModel = hiltViewModel<MainScreenViewModel>()
                    MainScreen(mainScreenViewModel = mainScreenViewModel, navController = navController)
                }
                composable(NavigationTree.RegisterNewPurchase.name){
                    val registerNewPurchaseModel : RegisterNewPurchaseModel by lazy {
                        val viewModel : RegisterNewPurchaseModel by viewModels()
                        viewModel.purchaseRepository = purchaseRepository
                        viewModel.gson = gson
                        viewModel
                    }
                    RegisterNewPurchase(registerNewPurchaseModel,navController)
                }
                composable(NavigationTree.Charts.name){
                    val chartsModel : ChartsModel by lazy {
                        val viewModel : ChartsModel by viewModels()
                        viewModel.purchaseRepository = purchaseRepository
                        viewModel
                    }
                    Charts(chartsModel = chartsModel)
                    chartsModel.obtainEvent(ChartsEvent.updateSlices)
                }
            }
        }
    }
}
