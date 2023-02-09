package com.fixess.fourmoney.screens.charts

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fixess.fourmoney.common.EventHandler
import com.fixess.fourmoney.database.PurchaseRepository
import com.fixess.fourmoney.dataclasses.charts.Category
import com.fixess.fourmoney.dataclasses.charts.PieChartSlice
import com.fixess.fourmoney.screens.charts.models.ChartsEvent
import com.fixess.fourmoney.screens.charts.models.ChartsState
import com.fixess.fourmoney.screens.charts.models.ChartsSubState
import com.fixess.fourmoney.screens.charts.models.ChartsViewState
import com.fixess.fourmoney.tools.ListOfSlicesToListOfCategoriesConverter
import com.fixess.fourmoney.tools.PieChartSliceToPurchaseEntityConverter
import com.fixess.fourmoney.tools.PurchaseEntityToPieChartSliceConverter
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
@HiltViewModel
class ChartsViewModel @Inject constructor(): ViewModel(), EventHandler<ChartsEvent> {
    private val _viewState = MutableLiveData(ChartsState())
    val viewState: LiveData<ChartsState> = _viewState
    lateinit var purchaseRepository: PurchaseRepository
    lateinit var gson: Gson

    override fun obtainEvent(event: ChartsEvent) {
        when(event){
            ChartsEvent.initial -> {
                updateLists()
            }
            ChartsEvent.updateSlices -> updateSlices()
            ChartsEvent.updateCategories -> updateCategories()
            ChartsEvent.toCategories -> setStateToCategories()
            ChartsEvent.toSlices -> setStateToSlices()
            ChartsEvent.toPurchase -> preformPurchaseCard()
            ChartsEvent.toCharts -> preformCharts()
            ChartsEvent.toCategory -> preformCategoryCard()
        }
    }
    private fun updateLists(){
        var list : MutableList<PieChartSlice> = ArrayList()
        val dispose = purchaseRepository.getAllPurchases().subscribeOn(Schedulers.io()).observeOn(
            AndroidSchedulers.mainThread())
            .map {
                list = ArrayList()
                Log.e("list ",list.toString())
                Log.e("list it ",it.toString())
                it.forEach {
                    list.add(PurchaseEntityToPieChartSliceConverter().convert(it))
                }
                Log.e("list it list",list.toString())
            }
            .subscribe({
                setLists(list)
            },{
                Log.e("List updating Error",it.toString())
            })
    }
    private fun updateListsAndGoBack(){
        var list : MutableList<PieChartSlice> = ArrayList()
        val dispose = purchaseRepository.getAllPurchases().subscribeOn(Schedulers.io()).observeOn(
            AndroidSchedulers.mainThread())
            .map {
                list = ArrayList()
                it.forEach {
                    list.add(PurchaseEntityToPieChartSliceConverter().convert(it))
                }
            }
            .subscribe({
                setListsAndSubState(list)
            },{
                Log.e("Slices adding Error",it.toString())
            })
    }
    private fun updateSlices(){
        var list : MutableList<PieChartSlice> = ArrayList()
        val dispose = purchaseRepository.getAllPurchases().subscribeOn(Schedulers.io()).observeOn(
            AndroidSchedulers.mainThread())
            .map {
                list = ArrayList()
                it.forEach {
                    list.add(PurchaseEntityToPieChartSliceConverter().convert(it))
                }
            }
            .subscribe({
                setListOfSlices(list)
            },{
                Log.e("Slices adding Error",it.toString())
            })
    }
    private fun updateCategories(){
        var list : MutableList<PieChartSlice> = ArrayList()
        val dispose = purchaseRepository.getAllPurchases().subscribeOn(Schedulers.io()).observeOn(
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
    private fun clearLists(){
        _viewState.postValue(_viewState.value!!.copy(listOfSlices = ArrayList(), listOfCategories = ArrayList()))
    }
    private fun setLists(list: MutableList<PieChartSlice>){
        Log.e("setLists",list.toString())
        _viewState.postValue(_viewState.value!!.copy(listOfSlices = list, listOfCategories = ListOfSlicesToListOfCategoriesConverter().convert(list)))
    }
    private fun setListsAndSubState(list: MutableList<PieChartSlice>){
        Log.e("setListsAndSubState",list.toString())
        _viewState.postValue(_viewState.value!!.copy(chartsSubState = ChartsSubState.CircleChart,listOfSlices = list, listOfCategories = ListOfSlicesToListOfCategoriesConverter().convert(list)))
    }
    private fun setListOfSlices(list: MutableList<PieChartSlice>){
        Log.e("setSlices",list.toString())
        _viewState.postValue(_viewState.value!!.copy(listOfSlices = list))
    }
    private fun setListOfCategories(list: MutableList<Category>){
        Log.e("setCategories",list.toString())
        _viewState.postValue(_viewState.value!!.copy(listOfCategories = list))
    }
    private fun setStateToCategories(){
        _viewState.postValue(_viewState.value!!.copy(chartsViewState = ChartsViewState.Categories))
    }
    private fun setStateToSlices(){
        _viewState.postValue(_viewState.value!!.copy(chartsViewState = ChartsViewState.Slices))
    }
    private fun preformPurchaseCard(){
        _viewState.postValue(_viewState.value?.copy(chartsSubState = ChartsSubState.Purchase))
    }
    private fun preformCategoryCard(){
        _viewState.postValue(_viewState.value?.copy(chartsSubState = ChartsSubState.Category))
    }
    private fun preformCharts(){
        _viewState.postValue(_viewState.value?.copy(chartsSubState = ChartsSubState.CircleChart))
    }
    fun setSelecterCategory(category: Category){
        _viewState.postValue(_viewState.value?.copy(selectedCategory = category,chartsSubState = ChartsSubState.Category))
    }
    fun setSelecterPurchase(pieChartSlice: PieChartSlice){
        _viewState.postValue(_viewState.value?.copy(selectedSlice = pieChartSlice,chartsSubState = ChartsSubState.Purchase))
    }
    fun deletePurchase(pieChartSlice: PieChartSlice){
        //preformCharts()
        purchaseRepository.deletePurchase(PieChartSliceToPurchaseEntityConverter().convert(pieChartSlice))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe{
                updateListsAndGoBack()
            }
    }
}
