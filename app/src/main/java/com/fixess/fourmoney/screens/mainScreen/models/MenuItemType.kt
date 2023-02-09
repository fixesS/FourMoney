package com.fixess.fourmoney.screens.mainScreen.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector

enum class MenuItemType(val id:Int,val contentDesription: String, val icon : ImageVector) {
    Charts(0,"charts", Icons.Filled.List),
    Other(0,"other", Icons.Filled.Info)
}