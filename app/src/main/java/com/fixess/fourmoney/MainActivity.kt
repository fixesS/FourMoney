package com.fixess.fourmoney

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.fixess.fourmoney.database.FourMoneyDatabase
import com.fixess.fourmoney.screens.ApplicationScreen
import com.fixess.fourmoney.ui.theme.FourMoneyTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ApplicationScreen()
        }
        appComponent.inject(this)
    }
}
