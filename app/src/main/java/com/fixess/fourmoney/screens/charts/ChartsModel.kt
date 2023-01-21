package com.fixess.fourmoney.screens.charts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fixess.fourmoney.common.EventHandler
import com.fixess.fourmoney.screens.charts.models.ChartsEvent
import com.fixess.fourmoney.screens.charts.models.ChartsState
import javax.inject.Inject

class ChartsModel @Inject constructor(): ViewModel(), EventHandler<ChartsEvent> {
    private val _viewState = MutableLiveData(ChartsState())
    val viewState: LiveData<ChartsState> = _viewState

    override fun obtainEvent(event: ChartsEvent) {

    }
}