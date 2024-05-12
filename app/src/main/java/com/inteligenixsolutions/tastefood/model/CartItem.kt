package com.inteligenixsolutions.tastefood.model

data class CartItem(
    val foodName: String = "",
    val foodDescription: String = "",
    val foodIngredients: String = "",
    val foodImage: String = "",
    val foodPrice: String = "",
    val foodQuantity: Int = 0
)
