package com.fixess.fourmoney.ui.screens.mainScreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fixess.fourmoney.common.EventHandler
import com.fixess.fourmoney.database.PurchaseRepository
import com.fixess.fourmoney.dataclasses.charts.Category
import com.fixess.fourmoney.dataclasses.charts.PieChartSlice
import com.fixess.fourmoney.ui.screens.mainScreen.models.MainScreenEvent
import com.fixess.fourmoney.ui.screens.mainScreen.models.MainScreenState
import com.fixess.fourmoney.ui.screens.mainScreen.models.MainScreenSubState
import com.fixess.fourmoney.tools.ListOfSlicesToListOfCategoriesConverter
import com.fixess.fourmoney.tools.PurchaseEntityToPieChartSliceConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(): ViewModel(), EventHandler<MainScreenEvent> {

    private val _viewState = MutableLiveData(MainScreenState())
    val viewState: LiveData<MainScreenState> = _viewState
    lateinit var purchaseRepository: PurchaseRepository

    override fun obtainEvent(event: MainScreenEvent) {
        when(event){
            MainScreenEvent.AddNewBuyClicked -> navigateRegisterNewBuy()
            MainScreenEvent.SetChartsSlices -> updateCategories()
            MainScreenEvent.FindMoneySpent -> sumMoney()
            MainScreenEvent.SetSortedList -> updateSortedCategories()
            MainScreenEvent.toLoading -> preformLoading()
            MainScreenEvent.toMainSub -> preformMainSub()
            MainScreenEvent.toNoPurchase -> preformNoPurchase()
            MainScreenEvent.Initial -> {
                updateListsAndSubState()
            }
        }
    }

    private fun navigateRegisterNewBuy(){

    }
    private fun sumMoney(){
        val list = viewState.value!!.listOfCategories
        var sum = 0f
        for(category in list){
            sum += category.sumOfWeights
        }
        setTotalMoneySpent(sum)
    }
    private fun updateListsAndSubState(){
        var list : MutableList<PieChartSlice> = ArrayList()
        val dispode = purchaseRepository.getAllPurchases().subscribeOn(Schedulers.io()).observeOn(
            AndroidSchedulers.mainThread())
            .map {
                list = ArrayList()
                it.forEach {
                    list.add(PurchaseEntityToPieChartSliceConverter().convert(it))
                }
            }
            .subscribe({
                val listOfCategories = ListOfSlicesToListOfCategoriesConverter().convert(list)
                val sum = 0f
                var state = MainScreenSubState.Loading
                if(listOfCategories.isEmpty()){
                    state = MainScreenSubState.NoPurchase
                }else{
                    state = MainScreenSubState.MainSubScreen
                }
                setListsAndSubState(listOfCategories,state)
            },{
                Log.e("Categories adding Error",it.toString())
            })
    }
    private fun updateCategories(){
        var list : MutableList<PieChartSlice> = ArrayList()
        val dispode = purchaseRepository.getAllPurchases().subscribeOn(Schedulers.io()).observeOn(
            AndroidSchedulers.mainThread())
            .map {
                list = ArrayList()
                it.forEach {
                    list.add(PurchaseEntityToPieChartSliceConverter().convert(it))
                }
            }
            .subscribe({
                setListOfCategories(ListOfSlicesToListOfCategoriesConverter().convert(list))
            },{
                Log.e("Categories adding Error",it.toString())
            })
    }
    private fun updateSortedCategories(){
        var list : MutableList<PieChartSlice> = ArrayList()
        val dispode = purchaseRepository.getAllPurchases().subscribeOn(Schedulers.io()).observeOn(
            AndroidSchedulers.mainThread())
            .map {
                list = ArrayList()
                it.forEach {
                    list.add(PurchaseEntityToPieChartSliceConverter().convert(it))
                }
            }
            .subscribe({
                setSortedListOfCategories(ListOfSlicesToListOfCategoriesConverter().convert(list))
            },{
                Log.e("Categories adding Error",it.toString())
            })
    }
    private fun setListsAndSubState(list: List<Category>,state: MainScreenSubState){
        _viewState.postValue(_viewState.value?.copy(listOfCategories = list, sortedListOfCategories = list.sortedByDescending { it.sumOfWeights }, mainScreenSubState = state))
    }
    private fun setListOfCategories(list: List<Category>){
        _viewState.postValue(_viewState.value?.copy(listOfCategories = list))
    }
    private fun setSortedListOfCategories(list: List<Category>){
        _viewState.postValue(_viewState.value?.copy(sortedListOfCategories = list.sortedByDescending { it.sumOfWeights }))
    }
    private fun setTotalMoneySpent(moneySpent :Float){
        _viewState.postValue(_viewState.value?.copy(totalMoneySpent = moneySpent))
    }
    private fun preformLoading(){
        _viewState.postValue(_viewState.value?.copy(mainScreenSubState = MainScreenSubState.Loading))
    }
    private fun preformMainSub(){
        _viewState.postValue(_viewState.value?.copy(mainScreenSubState = MainScreenSubState.MainSubScreen))
    }
    private fun preformNoPurchase(){
        _viewState.postValue(_viewState.value?.copy(mainScreenSubState = MainScreenSubState.NoPurchase))
    }
}