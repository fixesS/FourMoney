package com.fixess.fourmoney.screens.donate

import androidx.lifecycle.ViewModel
import com.fixess.fourmoney.common.EventHandler
import com.fixess.fourmoney.screens.donate.models.DonateEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DonateViewModel @Inject constructor(): ViewModel(), EventHandler<DonateEvent> {
    override fun obtainEvent(event: DonateEvent) {

    }
}