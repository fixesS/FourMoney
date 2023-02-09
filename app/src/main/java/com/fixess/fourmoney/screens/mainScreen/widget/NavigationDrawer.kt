package com.fixess.fourmoney.screens.mainScreen.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.fixess.fourmoney.screens.mainScreen.models.MenuItem

@Composable
fun DrawerHeader(){
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
        Text(text ="")
    }
}
@Composable
fun DrawerBody(
    item : MenuItem,
    modifier: Modifier = Modifier,
    onItemClick: (MenuItem) -> Unit
){
}