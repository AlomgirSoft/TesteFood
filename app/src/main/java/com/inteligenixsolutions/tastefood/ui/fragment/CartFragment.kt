package com.inteligenixsolutions.tastefood.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import com.inteligenixsolutions.tastefood.R
import com.inteligenixsolutions.tastefood.databinding.FragmentCartBinding
import com.inteligenixsolutions.tastefood.model.CartItem
import com.inteligenixsolutions.tastefood.ui.activity.PayOutActivity
import com.inteligenixsolutions.tastefood.ui.fragment.adapter.CartItemAdapter


class CartFragment : Fragment() {
    lateinit var auth: FirebaseAuth
    lateinit var database: FirebaseDatabase
    lateinit var foodName: MutableList<String>
    lateinit var foodImage: MutableList<String>
    lateinit var foodPrice: MutableList<String>
    lateinit var foodDescription: MutableList<String>
    lateinit var foodIngredients: MutableList<String>
    lateinit var foodQuantity: MutableList<Int>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    lateinit var binding: FragmentCartBinding
    lateinit var adapter: CartItemAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(layoutInflater)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        getCartItem()
        binding.proccedBtn.background = null



        binding.proccedBtn.setOnClickListener {
            getOrderItemDetails()

        }
        return binding.root
        //++++++++++++++++++++++++++++++++++++ on Create view end=========================
    }


    // ================================ get order item details star function =================================
    private fun getOrderItemDetails() {
        val orderIdDatabaseRef =
            database.reference.child("users").child(auth.currentUser!!.uid).child("CartItem")
        val foodName = mutableListOf<String>()
        val foodPrice = mutableListOf<String>()
        val foodImage = mutableListOf<String>()
        val foodDescription = mutableListOf<String>()
        val foodIngredients = mutableListOf<String>()

        val foodQuantities = adapter.getUpdateItemQuantities()

        orderIdDatabaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {
                    val orderItem = dataSnapshot.getValue(CartItem::class.java)
                    orderItem?.foodName?.let { foodName.add(it) }
                    orderItem?.foodPrice?.let { foodPrice.add(it) }
                    orderItem?.foodDescription?.let { foodDescription.add(it) }
                    orderItem?.foodImage?.let { foodImage.add(it) }
                    orderItem?.foodIngredients?.let { foodIngredients.add(it) }
                    orderItem?.foodQuantity?.let { foodQuantities.add(it) }
                }

                orderNow(
                    foodName,
                    foodPrice,
                    foodDescription,
                    foodImage,
                    foodIngredients,
                    foodQuantity
                )
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
            }

        })

    }

    // ================================ get order item details end function ==========================


    // =============== order now star ==========================
    private fun orderNow(
        foodName: MutableList<String>,
        foodPrice: MutableList<String>,
        foodDescription: MutableList<String>,
        foodImage: MutableList<String>,
        foodIngredients: MutableList<String>,
        foodQuantity: MutableList<Int>
    ) {
        if (isAdded && context != null) {
            val intent = Intent(requireContext(), PayOutActivity::class.java)
            intent.putExtra("foodName", foodName as ArrayList<String>)
            intent.putExtra("foodPrice", foodPrice as ArrayList<String>)
            intent.putExtra("foodDescription", foodDescription as ArrayList<String>)
            intent.putExtra("foodImage", foodImage as ArrayList<String>)
            intent.putExtra("foodIngredients", foodIngredients as ArrayList<String>)
            intent.putExtra("foodQuantity", foodQuantity as ArrayList<String>)
            startActivity(intent)
        }
    }
    // =============== order now end ==========================


    //=============================== get cart item start ========================
    private fun getCartItem() {
        foodName = mutableListOf()
        foodImage = mutableListOf()
        foodPrice = mutableListOf()
        foodDescription = mutableListOf()
        foodIngredients = mutableListOf()
        foodQuantity = mutableListOf()

        val databaseRef =
            database.reference.child("users").child(auth.currentUser!!.uid).child("CartItem")

        databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for (dataSnapshot in snapshot.children) {
                    val cartItem = dataSnapshot.getValue(CartItem::class.java)

                    cartItem?.foodName?.let { foodName.add(it) }
                    cartItem?.foodPrice?.let { foodPrice.add(it) }
                    cartItem?.foodDescription?.let { foodDescription.add(it) }
                    cartItem?.foodIngredients?.let { foodIngredients.add(it) }
                    cartItem?.foodImage?.let { foodImage.add(it) }
                    cartItem?.foodQuantity?.let { foodQuantity.add(it) }
                }
                setAdapter()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
    //=============================== get cart item end ========================


    // set cart adapter
    private fun setAdapter() {
        adapter = CartItemAdapter(
           requireActivity(),
            foodName,
            foodImage,
            foodPrice,
            foodDescription,
            foodIngredients,
            foodQuantity
        )
        binding.cartRcv.adapter = adapter

    }


}