package com.inteligenixsolutions.tastefood.ui.fragment

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import com.inteligenixsolutions.tastefood.R
import com.inteligenixsolutions.tastefood.RecentOrderBuyItemActivity
import com.inteligenixsolutions.tastefood.databinding.FragmentHistoryBinding
import com.inteligenixsolutions.tastefood.model.OrderDetails
import com.inteligenixsolutions.tastefood.ui.fragment.adapter.HistoryAdapter

class HistoryFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    lateinit var binding: FragmentHistoryBinding

    lateinit var adapter: HistoryAdapter
    lateinit var auth: FirebaseAuth
    lateinit var database: FirebaseDatabase
    lateinit var userId: String
    var listOfOrderItem: MutableList<OrderDetails> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHistoryBinding.inflate(layoutInflater)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        userId = auth.currentUser!!.uid

        getOrderBuyHistory()






        binding.recentByOrder.setOnClickListener {
            seeRecentBuyItem()

        }
     binding.receivedBtn.setOnClickListener {

         receivedOrder()

     }


        return binding.root

        //+++++++++++++++++++ on create view end ++++++++++++++++++++++++++++++++++++
    }

    private fun receivedOrder() {
           val receivedItemPushKey= listOfOrderItem[0].itemPushKey
          val completedOrderRef= database.reference.child("completedOrder").child(receivedItemPushKey)
        completedOrderRef.child("paymentReceived").setValue(true)
    }

    private fun seeRecentBuyItem() {
        val intent = Intent(requireContext(), RecentOrderBuyItemActivity::class.java)
        intent.putExtra("recentBuyItem", ArrayList(listOfOrderItem))
        startActivity(intent)

    }


    //=================== get order Buy history function start======================
    private fun getOrderBuyHistory() {
        binding.recentByOrder.visibility = View.INVISIBLE

        var byItemDatabaseRef = database.reference.child("users").child(userId).child("buyHistory")
        val shortingQuery = byItemDatabaseRef.orderByChild("currentTime")
        shortingQuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (buySnapshot in snapshot.children) {
                    val orderDetails = buySnapshot.getValue(OrderDetails::class.java)
                    orderDetails?.let {
                        listOfOrderItem.add(it)
                    }

                }
                listOfOrderItem.reverse()
                if (listOfOrderItem.isNotEmpty()) {
                    setDataRecentBuyItem()
                    setPriveosBuyItemRecyclerView()
                }
            }


            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }

    //=================== get order Buy history function end ======================


    //=================== set data recent buy item function start ======================
    private fun setDataRecentBuyItem() {
        binding.recentByOrder.visibility = View.VISIBLE
        var recentBuyItem = listOfOrderItem.firstOrNull()
        recentBuyItem?.let {
            binding.apply {

                recentBuyFoodNameTV.text = it.foodNames?.firstOrNull() ?: ""
                recentPriceTv.text = "$"+it.foodPrice?.firstOrNull() ?: ""
                val image = it.foodImage?.firstOrNull() ?: ""


                if (isAdded){
                    val uri = Uri.parse(image)
                    Glide.with(this@HistoryFragment).load(uri).into(recentBuyFoodImageView)
                }



                 val isOrderAccepted= listOfOrderItem[0].orderAccepted
                if (isOrderAccepted){
                    binding.orderStatus.background.setTint(Color.GREEN)
                    binding.receivedBtn.visibility=View.VISIBLE
                }

            }
        }

    }
    //=================== set data recent buy item function end ======================


    //=================== set privies buy item recyclerView function start ======================
    private fun setPriveosBuyItemRecyclerView() {
        val foodName = mutableListOf<String>()
        val foodPrice = mutableListOf<String>()
        val foodImage = mutableListOf<String>()
        for (i in 0 until listOfOrderItem.size) {
            listOfOrderItem[i].foodNames?.firstOrNull()?.let { foodName.add(it) }
            listOfOrderItem[i].foodPrice?.firstOrNull()?.let { foodPrice.add(it) }
            listOfOrderItem[i].foodImage?.firstOrNull()?.let { foodImage.add(it) }
        }
        adapter = HistoryAdapter(requireContext(), foodName, foodImage, foodPrice)
        binding.historyRcv.adapter = adapter

    }
    //=================== set privies buy item recyclerView function start ======================

}