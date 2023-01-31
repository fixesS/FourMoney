package com.fixess.fourmoney.screens.mainScreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fixess.fourmoney.common.EventHandler
import com.fixess.fourmoney.database.PurchaseRepository
import com.fixess.fourmoney.dataclasses.charts.Category
import com.fixess.fourmoney.dataclasses.charts.PieChartSlice
import com.fixess.fourmoney.screens.mainScreen.models.MainScreenEvent
import com.fixess.fourmoney.screens.mainScreen.models.MainScreenState
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
    private fun sortCategories(){
        val list = viewState.value!!.listOfCategories
        Log.e("list2.1",list.toString())
        val sortedList = list.sortedByDescending {
            it.sumOfWeights
            Log.e("it",it.toString())
        }
        Log.e("list2.2",sortedList.toString())
        setSortedListOfCategories(sortedList)
    }
    private fun updateCategories(){
        val sliceConverter  = PurchaseEntityToPieChartSliceConverter()
        val categoryConverter  = ListOfSlicesToListOfCategoriesConverter()
        val list : MutableList<PieChartSlice> = ArrayList()
        val dispode = purchaseRepository.getAllPurchases().subscribeOn(Schedulers.io()).observeOn(
            AndroidSchedulers.mainThread())
            .subscribe({
                it.forEach {
                    list.add(sliceConverter.convert(it))
                }
                setListOfCategories(categoryConverter.convert(list))
            },{
                Log.e("Categories adding Error",it.toString())
            })
    }
    private fun setListOfCategories(list: List<Category>){
        _viewState.postValue(_viewState.value?.copy(listOfCategories = list))
    }
    private fun setSortedListOfCategories(sortedList: List<Category>){
        _viewState.postValue(_viewState.value?.copy(sortedListOfCategories = sortedList))
    }
    private fun setTotalMoneySpent(moneySpent :Float){
        _viewState.postValue(_viewState.value?.copy(totalMoneySpent = moneySpent))
    }
}