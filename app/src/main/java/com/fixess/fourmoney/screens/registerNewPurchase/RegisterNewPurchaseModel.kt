package com.fixess.fourmoney.screens.registerNewPurchase

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fixess.fourmoney.common.EventHandler
import com.fixess.fourmoney.database.dao.DAO
import com.fixess.fourmoney.database.entities.PurchaseEntity
import com.fixess.fourmoney.screens.registerNewPurchase.models.RegisterNewPurchaseEvent
import com.fixess.fourmoney.screens.registerNewPurchase.models.RegisterNewPurchaseState
import com.fixess.fourmoney.screens.registerNewPurchase.models.RegisterNewPurchaseSubState
import com.google.gson.Gson
import java.sql.Timestamp
import java.time.LocalDateTime
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
class RegisterNewPurchaseModel @Inject constructor(): ViewModel(), EventHandler<RegisterNewPurchaseEvent> {
    @Inject
    lateinit var dao : DAO
    @Inject
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
                dao.addPurchase(
                    PurchaseEntity(
                        purchaseId = 0,
                        typeId = viewState.value?.type?.id,
                        money = viewState.value?.money,
                        timestamp = gson.toJson(Timestamp.valueOf(LocalDateTime.of(viewState.value?.date,viewState.value?.time).toString()))
                    )
                )
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