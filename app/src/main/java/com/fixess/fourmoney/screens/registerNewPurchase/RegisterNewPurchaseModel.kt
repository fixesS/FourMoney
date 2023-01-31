package com.fixess.fourmoney.screens.registerNewPurchase

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fixess.fourmoney.common.EventHandler
import com.fixess.fourmoney.database.PurchaseRepository
import com.fixess.fourmoney.database.entities.PurchaseEntity
import com.fixess.fourmoney.screens.registerNewPurchase.models.RegisterNewPurchaseEvent
import com.fixess.fourmoney.screens.registerNewPurchase.models.RegisterNewPurchaseState
import com.fixess.fourmoney.screens.registerNewPurchase.models.RegisterNewPurchaseSubState
import com.google.gson.Gson
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject


@RequiresApi(Build.VERSION_CODES.O)
class RegisterNewPurchaseModel @Inject constructor(): ViewModel(), EventHandler<RegisterNewPurchaseEvent> {

    lateinit var purchaseRepository: PurchaseRepository
    lateinit var gson: Gson
    private val _viewState = MutableLiveData(RegisterNewPurchaseState())
    val viewState: LiveData<RegisterNewPurchaseState> = _viewState

    override fun obtainEvent(event: RegisterNewPurchaseEvent) {
        when(event){
            RegisterNewPurchaseEvent.toTypePickerClicked -> preformTypePicker()
            RegisterNewPurchaseEvent.toTimePickerClicked -> preformTimePicker()
            RegisterNewPurchaseEvent.toMoneyPickerClicked -> preformMoneyPicker()
            RegisterNewPurchaseEvent.backToTimePicker -> preformTimePicker()
            RegisterNewPurchaseEvent.backToTypePicker -> preformTypePicker()
            RegisterNewPurchaseEvent.backToDatePicker -> preformDatePicker()
            RegisterNewPurchaseEvent.nextState -> nextState()
            RegisterNewPurchaseEvent.previousState -> previousState()
            RegisterNewPurchaseEvent.savePurchase ->{
                purchaseRepository.addPurchase(
                    PurchaseEntity(
                        purchaseId = 0,
                        typeId = viewState.value?.type?.id,
                        money = viewState.value?.money,
                        timestamp = gson.toJson(LocalDateTime.of(viewState.value?.date,viewState.value?.time).toString())
                    )
                ).subscribeOn(Schedulers.io()).subscribe()

                preformDatePicker()
            }
        }
    }

    private fun nextState(){
        when(viewState.value?.registerNewPurchaseSubState){
            RegisterNewPurchaseSubState.DatePicker -> preformTimePicker()
            RegisterNewPurchaseSubState.TimePicker -> preformTypePicker()
            RegisterNewPurchaseSubState.TypePicker -> preformMoneyPicker()
            else -> {}
        }
    }
    private fun previousState(){
        when(viewState.value?.registerNewPurchaseSubState){
            RegisterNewPurchaseSubState.TimePicker -> preformDatePicker()
            RegisterNewPurchaseSubState.TypePicker -> preformTimePicker()
            RegisterNewPurchaseSubState.MoneyPicker -> preformTypePicker()
            else -> {}
        }
    }
    private fun preformDatePicker(){
        _viewState.postValue(_viewState.value?.copy(registerNewPurchaseSubState = RegisterNewPurchaseSubState.DatePicker))
    }
    private fun preformTimePicker(){
        _viewState.postValue(_viewState.value?.copy(registerNewPurchaseSubState = RegisterNewPurchaseSubState.TimePicker))
    }
    private fun preformTypePicker(){
        _viewState.postValue(_viewState.value?.copy(registerNewPurchaseSubState = RegisterNewPurchaseSubState.TypePicker))
    }
    private fun preformMoneyPicker(){
        _viewState.postValue(_viewState.value?.copy(registerNewPurchaseSubState = RegisterNewPurchaseSubState.MoneyPicker))
    }
}