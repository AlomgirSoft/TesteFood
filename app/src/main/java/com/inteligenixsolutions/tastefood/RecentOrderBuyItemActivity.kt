package com.inteligenixsolutions.tastefood

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.inteligenixsolutions.tastefood.databinding.ActivityRecentOrderBuyItemBinding
import com.inteligenixsolutions.tastefood.model.OrderDetails
import com.inteligenixsolutions.tastefood.ui.fragment.adapter.RecentBuyOrderAdapter

class RecentOrderBuyItemActivity : AppCompatActivity() {


     lateinit var  binding: ActivityRecentOrderBuyItemBinding

    var foodNames: MutableList<String> = mutableListOf()
    var foodImage: MutableList<String> = mutableListOf()
    var foodPrice: MutableList<String> = mutableListOf()
    var foodQuantities: MutableList<Int> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRecentOrderBuyItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.backBtn.setOnClickListener {
            finish()
        }
        binding.backBtn.background = null





        val recentBuyItemList =
            intent.getSerializableExtra("recentBuyItem") as ArrayList<OrderDetails>
        // Now you can use the 'recentBuyItemList' in your activity
        recentBuyItemList?.let { orderDetails ->
            if (orderDetails.isNotEmpty()) {
                val recentOrderItem = orderDetails[0]

                foodNames = recentOrderItem.foodNames ?: mutableListOf()
                foodPrice = recentOrderItem.foodPrice ?: mutableListOf()
                foodImage = recentOrderItem.foodImage ?: mutableListOf()
                foodQuantities = recentOrderItem.foodQuantities ?: mutableListOf()

//
//                foodNames = recentOrderItem.foodNames as ArrayList<String>
//                foodPrice = recentOrderItem.foodPrice as ArrayList<String>
//                foodImage = recentOrderItem.foodImage as ArrayList<String>
//                foodQuantities = recentOrderItem.foodQuantities as ArrayList<Int>
                Log.d("foodNames",foodNames.toString())

            }
            setAdapter()
        }

//++++++++++++++++++++++++ on create view end++++++++++++++++++++++++++++
    }

    private fun setAdapter() {
        val adapter = RecentBuyOrderAdapter(
            this@RecentOrderBuyItemActivity,
            foodNames,
            foodImage,
            foodPrice,
            foodQuantities
        )
        binding.recentBuyRcv.adapter = adapter
        adapter.notifyDataSetChanged()
    }


}