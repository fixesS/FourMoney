package com.fixess.fourmoney.screens.mainScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.fixess.fourmoney.common.EventHandler
import com.fixess.fourmoney.screens.mainScreen.models.MainScreenEvent
import com.fixess.fourmoney.screens.mainScreen.models.MainScreenViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(): ViewModel(), EventHandler<MainScreenEvent> {

    private val _viewState = MutableLiveData(MainScreenViewState())
    val viewState: LiveData<MainScreenViewState> = _viewState

    override fun obtainEvent(event: MainScreenEvent) {
        when(event){
             MainScreenEvent.AddNewBuyClicked -> navigateRegisterNewBuy()
        }
    }

    private fun navigateRegisterNewBuy(){

    }
}