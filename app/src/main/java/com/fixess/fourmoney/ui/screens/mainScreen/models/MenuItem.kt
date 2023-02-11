package com.fixess.fourmoney.ui.screens.mainScreen.models

import androidx.compose.ui.graphics.vector.ImageVector

data class MenuItem(
    val type: MenuItemType,
    val title: String,
    val contentDescription: String = type.contentDesription,
    val icon: ImageVector = type.icon
)
