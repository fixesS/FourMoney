package com.fixess.fourmoney.screens.registerNewPurchase

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fixess.fourmoney.common.EventHandler
import com.fixess.fourmoney.database.PurchaseRepository
import com.fixess.fourmoney.database.entities.PurchaseEntity
import com.fixess.fourmoney.enums.Type
import com.fixess.fourmoney.screens.registerNewPurchase.models.RegisterNewPurchaseEvent
import com.fixess.fourmoney.screens.registerNewPurchase.models.RegisterNewPurchaseState
import com.google.gson.Gson
import io.reactivex.schedulers.Schedulers
import java.time.LocalDate
import javax.inject.Inject


@RequiresApi(Build.VERSION_CODES.O)
class RegisterNewPurchaseViewModel @Inject constructor(): ViewModel(), EventHandler<RegisterNewPurchaseEvent> {

    lateinit var purchaseRepository: PurchaseRepository
    lateinit var gson: Gson
    private val _viewState = MutableLiveData(RegisterNewPurchaseState())
    val viewState: LiveData<RegisterNewPurchaseState> = _viewState

    override fun obtainEvent(event: RegisterNewPurchaseEvent) {
        when(event){
//            RegisterNewPurchaseEvent.toTypePickerClicked -> preformTypePicker()
//            RegisterNewPurchaseEvent.toTimePickerClicked -> preformTimePicker()
//            RegisterNewPurchaseEvent.toMoneyPickerClicked -> preformMoneyPicker()
//            RegisterNewPurchaseEvent.backToTimePicker -> preformTimePicker()
//            RegisterNewPurchaseEvent.backToTypePicker -> preformTypePicker()
//            RegisterNewPurchaseEvent.backToDatePicker -> preformDatePicker()
//            RegisterNewPurchaseEvent.nextState -> nextState()
//            RegisterNewPurchaseEvent.previousState -> previousState()
            RegisterNewPurchaseEvent.savePurchase ->{
                purchaseRepository.addPurchase(
                    PurchaseEntity(
                        purchaseId = 0,
                        typeId = viewState.value?.type?.id,
                        money = viewState.value?.money,
                        timestamp = gson.toJson(viewState.value?.date,LocalDate::class.java)
                    )
                ).subscribeOn(Schedulers.io()).subscribe()
//                preformDatePickerAndNullStates()
            }
            is RegisterNewPurchaseEvent.saveMoney -> setMoney(event.money)
            is RegisterNewPurchaseEvent.saveDate -> setDate(event.date)
            is RegisterNewPurchaseEvent.saveType -> setType(event.type)
            RegisterNewPurchaseEvent.setStatesToDefault -> setDefaults()
        }
    }

//    private fun nextState(){
//        when(viewState.value?.registerNewPurchaseSubState){
//            RegisterNewPurchaseSubState.DatePicker -> preformTimePicker()
//            RegisterNewPurchaseSubState.TimePicker -> preformTypePicker()
//            RegisterNewPurchaseSubState.TypePicker -> preformMoneyPicker()
//            else -> {}
//        }
//    }
//    private fun previousState(){
//        when(viewState.value?.registerNewPurchaseSubState){
//            RegisterNewPurchaseSubState.TimePicker -> preformDatePicker()
//            RegisterNewPurchaseSubState.TypePicker -> preformTimePicker()
//            RegisterNewPurchaseSubState.MoneyPicker -> preformTypePicker()
//            else -> {}
//        }
//    }
//    private fun preformDatePicker(){
//        _viewState.postValue(_viewState.value?.copy(registerNewPurchaseSubState = RegisterNewPurchaseSubState.DatePicker))
//    }
//    private fun preformDatePickerAndNullStates(){
//        _viewState.postValue(_viewState.value?.copy(registerNewPurchaseSubState = RegisterNewPurchaseSubState.DatePicker, money = 0f,date = LocalDate.now(),time = LocalTime.now(), timestamp = "0",type = Type.UNKNOWN))
//    }
//    private fun preformTimePicker(){
//        _viewState.postValue(_viewState.value?.copy(registerNewPurchaseSubState = RegisterNewPurchaseSubState.TimePicker))
//    }
//    private fun preformTypePicker(){
//        _viewState.postValue(_viewState.value?.copy(registerNewPurchaseSubState = RegisterNewPurchaseSubState.TypePicker))
//    }
//    private fun preformMoneyPicker(){
//        _viewState.postValue(_viewState.value?.copy(registerNewPurchaseSubState = RegisterNewPurchaseSubState.MoneyPicker))
//    }
    private fun setMoney(money: Float){
    _viewState.postValue(_viewState.value?.copy(money = money))
    }
    private fun setDate(date: LocalDate){
        _viewState.postValue(_viewState.value?.copy(date = date))
    }
    private fun setType(type: Type){
        _viewState.postValue(_viewState.value?.copy(type = type))
    }
    private fun setDefaults(){
        _viewState.postValue(_viewState.value?.copy(type = Type.UNKNOWN, money = 0f, date = LocalDate.now()))
    }
}