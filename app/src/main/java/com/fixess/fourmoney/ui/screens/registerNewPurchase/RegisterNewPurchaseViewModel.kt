package com.fixess.fourmoney.ui.screens.registerNewPurchase

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fixess.fourmoney.common.EventHandler
import com.fixess.fourmoney.database.PurchaseRepository
import com.fixess.fourmoney.database.entities.PurchaseEntity
import com.fixess.fourmoney.enums.Type
import com.fixess.fourmoney.ui.screens.registerNewPurchase.models.DialogSubState
import com.fixess.fourmoney.ui.screens.registerNewPurchase.models.RegisterNewPurchaseEvent
import com.fixess.fourmoney.ui.screens.registerNewPurchase.models.RegisterNewPurchaseState
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
            RegisterNewPurchaseEvent.savePurchase ->{
                purchaseRepository.addPurchase(
                    PurchaseEntity(
                        purchaseId = 0,
                        typeId = viewState.value?.type?.id,
                        money = viewState.value?.money,
                        timestamp = gson.toJson(viewState.value?.date,LocalDate::class.java)
                    )
                ).subscribeOn(Schedulers.io()).subscribe()
            }
            is RegisterNewPurchaseEvent.saveMoney -> setMoney(event.money)
            is RegisterNewPurchaseEvent.saveDate -> setDate(event.date)
            is RegisterNewPurchaseEvent.saveType -> setType(event.type)
            RegisterNewPurchaseEvent.setStatesToDefault -> setDefaults()
            RegisterNewPurchaseEvent.setSubStateNone -> setSubStateNone()
            RegisterNewPurchaseEvent.setSubStateDate -> setSubStateDate()
            RegisterNewPurchaseEvent.setSubStateType -> setSubStateType()
            RegisterNewPurchaseEvent.setSubStateError -> setSubStateError()
            is RegisterNewPurchaseEvent.setSelectedTypeItem -> setSelectedItemType(event.index)
            is RegisterNewPurchaseEvent.setSubStateNoneAndSaveType -> setSubStateNoneAndSaveType(event.type)
            is RegisterNewPurchaseEvent.setSubStateNoneAndSaveDate -> setSubStateNoneAndSaveDate(event.date)
        }
    }
    private fun setSelectedItemType(index: Int){
        _viewState.postValue(_viewState.value?.copy(selectedTypeIndex = index))
    }
    private fun setSubStateNone(){
        _viewState.postValue(_viewState.value?.copy(dialogSubState = DialogSubState.None))
    }
    private fun setSubStateNoneAndSaveType(type: Type){
        _viewState.postValue(_viewState.value?.copy(dialogSubState = DialogSubState.None,type = type))
    }
    private fun setSubStateNoneAndSaveDate(date: LocalDate){
        _viewState.postValue(_viewState.value?.copy(dialogSubState = DialogSubState.None,date = date))
    }
    private fun setSubStateDate(){
        _viewState.postValue(_viewState.value?.copy(dialogSubState = DialogSubState.Date))
    }
    private fun setSubStateType(){
        _viewState.postValue(_viewState.value?.copy(dialogSubState = DialogSubState.Type))
    }
    private fun setSubStateError(){
        _viewState.postValue(_viewState.value?.copy(dialogSubState = DialogSubState.Error))
    }
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
        _viewState.postValue(_viewState.value?.copy(type = Type.UNKNOWN, money = 0f, date = LocalDate.now(), selectedTypeIndex = -1, dialogSubState = DialogSubState.None))
    }
}