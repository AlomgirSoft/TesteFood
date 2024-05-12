package com.inteligenixsolutions.tastefood.model

import android.os.Parcelable
import androidx.navigation.NavType
import kotlinx.parcelize.Parcelize

@Parcelize
data class MenuItem(
    val foodName: String = "",
    val foodPrice: String = "",
    val foodShortDescription: String = "",
    val foodIngredients: String = "",
    val foodImage: String = ""
) : Parcelable

