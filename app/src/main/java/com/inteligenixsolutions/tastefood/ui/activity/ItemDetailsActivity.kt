package com.inteligenixsolutions.tastefood.ui.activity

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.inteligenixsolutions.tastefood.R
import com.inteligenixsolutions.tastefood.databinding.ActivityItemDetailsBinding
import com.inteligenixsolutions.tastefood.model.CartItem

import com.inteligenixsolutions.tastefood.model.MenuItem

class ItemDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityItemDetailsBinding


    lateinit var auth: FirebaseAuth
    var foodName: String? = null
    var foodDescription: String? = null
    var foodIngredients: String? = null
    var foodImage: String? = null
    var foodPrice: String? = null
    var foodCountry: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //   val bundle = intent.getBundleExtra("bundle")
        auth = FirebaseAuth.getInstance()


        val menuItem = intent.getParcelableExtra<MenuItem>("menuItem")

        menuItem?.let { menuItem ->
            foodName = menuItem.foodName
            foodPrice = menuItem.foodPrice
            foodImage = menuItem.foodImage
            foodIngredients =  menuItem.foodIngredients
            foodDescription = menuItem.foodShortDescription


            binding.foodNameTV.text = foodName
            binding.tvFoodPrice.text = "Price : $foodPrice$"

            binding.Tvingredients.text = "Ingredients :\n $foodIngredients"

            binding.stortDescriptionTv.text = "Short Description : \n $foodDescription"

            Glide.with(this).load(Uri.parse(foodImage)).into(binding.popularFoodImageView)

            // You can similarly bind other properties of MenuItem to corresponding UI elements
        }


        binding.addToCartBt.setOnClickListener {
            addItemToCarT()
        }

    }

    private fun addItemToCarT() {
        val dataRef = FirebaseDatabase.getInstance().reference

        val cartItem = CartItem(
            foodName.toString(),
            foodDescription.toString(),
            foodIngredients.toString(),
            foodImage.toString(),
            foodPrice.toString(),
            1
        )

        dataRef.child("users").child(auth.currentUser!!.uid).child("CartItem").push()
            .setValue(cartItem).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(this, "successfully", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Log.d("cartItem", it.message.toString())

        }
    }
}