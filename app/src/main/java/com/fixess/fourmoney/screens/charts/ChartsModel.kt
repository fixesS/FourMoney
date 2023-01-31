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
import com.fixess.fourmoney.screens.charts.models.ChartsViewState
import com.fixess.fourmoney.tools.ListOfSlicesToListOfCategoriesConverter
import com.fixess.fourmoney.tools.PurchaseEntityToPieChartSliceConverter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ChartsModel @Inject constructor(): ViewModel(), EventHandler<ChartsEvent> {
    private val _viewState = MutableLiveData(ChartsState())
    val viewState: LiveData<ChartsState> = _viewState
    lateinit var purchaseRepository: PurchaseRepository

    override fun obtainEvent(event: ChartsEvent) {
        when(event){
            ChartsEvent.initial -> {
                setSlices()
                setCategories()
            }
            ChartsEvent.updateSlices -> setSlices()
            ChartsEvent.updateCategories -> setCategories()
            ChartsEvent.toCategories -> setStateToCategories()
            ChartsEvent.toSlices -> setStateToSlices()
        }
    }
    private fun setSlices(){
        val sliceConverter  = PurchaseEntityToPieChartSliceConverter()
        val list : MutableList<PieChartSlice> = ArrayList()
        val dispode = purchaseRepository.getAllPurchases().subscribeOn(Schedulers.io()).observeOn(
            AndroidSchedulers.mainThread())
            .subscribe({
                it.forEach {
                    list.add(sliceConverter.convert(it))
                }
                setListOfSlices(list)
            },{
                Log.e("Slices adding Error",it.toString())
            })
    }
    private fun setCategories(){
        val sliceConverter  = PurchaseEntityToPieChartSliceConverter()
        val categoryConverter  = ListOfSlicesToListOfCategoriesConverter()
        val list : MutableList<PieChartSlice> = ArrayList()
        val dispode = purchaseRepository.getAllPurchases().subscribeOn(Schedulers.io()).observeOn(
            AndroidSchedulers.mainThread())
            .subscribe({
                it.forEach {
                    list.add(sliceConverter.convert(it))
                }
                setListOfSlices(list)
                setListOfCategories(categoryConverter.convert(list))
            },{
                Log.e("Categories adding Error",it.toString())
            })
    }
    private fun setListOfSlices(list: List<PieChartSlice>){
        _viewState.postValue(_viewState.value?.copy(listOfSlices = list))
    }
    private fun setListOfCategories(list: List<Category>){
        _viewState.postValue(_viewState.value?.copy(listOfCategories = list))
    }
    private fun setStateToCategories(){
        _viewState.postValue(_viewState.value?.copy(chartsViewState = ChartsViewState.Categories))
    }
    private fun setStateToSlices(){
        _viewState.postValue(_viewState.value?.copy(chartsViewState = ChartsViewState.Slices))
    }
}
