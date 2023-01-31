package com.fixess.fourmoney.screens.charts

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fixess.fourmoney.common.EventHandler
import com.fixess.fourmoney.database.PurchaseRepository
import com.fixess.fourmoney.dataclasses.charts.PieChartSlice
import com.fixess.fourmoney.screens.charts.models.ChartsEvent
import com.fixess.fourmoney.screens.charts.models.ChartsState
import com.fixess.fourmoney.screens.registerNewPurchase.models.RegisterNewPurchaseSubState
import com.fixess.fourmoney.tools.PurchaseEntityToPieChartSlice
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ChartsModel @Inject constructor(): ViewModel(), EventHandler<ChartsEvent> {
    private val _viewState = MutableLiveData(ChartsState())
    val viewState: LiveData<ChartsState> = _viewState
    lateinit var purchaseRepository: PurchaseRepository

    override fun obtainEvent(event: ChartsEvent) {
        when(event){
            ChartsEvent.updateSlices -> getSlices()
        }
    }
    fun getSlices():MutableList<PieChartSlice>{
        val converter : PurchaseEntityToPieChartSlice = PurchaseEntityToPieChartSlice()
        val list : MutableList<PieChartSlice> = ArrayList()
        val dispode = purchaseRepository.getAllPurchases().subscribeOn(Schedulers.io()).observeOn(
            AndroidSchedulers.mainThread())
            .subscribe({
                it.forEach {
                    list.add(converter.convert(it))
                }
                setListOfSlices(list)
                Log.e("ДОБАВЛЕНИЕ", list.toString())
            },{
                Log.e("ERROR",it.toString())
            })

        return list
    }
    fun setListOfSlices(list: List<PieChartSlice>){
        _viewState.postValue(_viewState.value?.copy(listOfSlices = list))
    }
}
