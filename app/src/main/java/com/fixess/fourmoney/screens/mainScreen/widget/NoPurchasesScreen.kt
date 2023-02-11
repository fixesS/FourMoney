package com.fixess.fourmoney.screens.mainScreen.widget

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun NoPurchaseScreen(
    onNavigateToRegisterNewPurchase : () -> Unit = {}
){
    Surface{
        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize().padding(4.dp)) {
            Icon(imageVector = Icons.Filled.Info, contentDescription = "info", modifier = Modifier.height(70.dp).width(70.dp))
            Text(text = "Похоже, что у вас еще нет покупок", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(5.dp), textAlign = TextAlign.Center)
            Text(text ="Нажмите на кнопку, чтобы добавить покупку", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(5.dp), textAlign = TextAlign.Center)
            Button(onClick = { onNavigateToRegisterNewPurchase() }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "add new purchase")
            }
        }
    }
}