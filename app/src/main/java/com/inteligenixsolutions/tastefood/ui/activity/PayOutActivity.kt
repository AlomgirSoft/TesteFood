package com.inteligenixsolutions.tastefood.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.inteligenixsolutions.tastefood.R
import com.inteligenixsolutions.tastefood.databinding.ActivityPayOutBinding
import com.inteligenixsolutions.tastefood.model.OrderDetails
import com.inteligenixsolutions.tastefood.ui.fragment.BottomSheetMenuFragment
import com.inteligenixsolutions.tastefood.ui.fragment.CongratsFragment

class PayOutActivity : AppCompatActivity() {

    lateinit var binding: ActivityPayOutBinding

    lateinit var auth: FirebaseAuth
    lateinit var database: DatabaseReference
    lateinit var foodName: MutableList<String>
    lateinit var foodImage: MutableList<String>
    lateinit var foodPrice: MutableList<String>
    lateinit var foodDescription: MutableList<String>
    lateinit var foodIngredients: MutableList<String>
    lateinit var foodQuantity: MutableList<Int>
    lateinit var totalAmount: String
    lateinit var name: String
    lateinit var address: String
    lateinit var phone: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayOutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.backBtn.background = null
        binding.backBtn.setOnClickListener {
            finish()
        }
        binding.placeMyOrderBtn.background = null
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference()



        binding.placeMyOrderBtn.setOnClickListener {
            name = binding.edName.text.toString().trim()
            address = binding.edAddress.text.toString().trim()
            phone = binding.edPhone.text.toString().trim()

            if (name.isBlank() && address.isBlank() && phone.isBlank()) {
                Toast.makeText(this, "please input all details  ", Toast.LENGTH_SHORT).show()
            } else {
                placeOrder()
            }


        }
        setUserData()

        val intent = intent
        foodName = intent.getStringArrayListExtra("foodName") as ArrayList<String>
        foodPrice = intent.getStringArrayListExtra("foodPrice") as ArrayList<String>
        foodDescription = intent.getStringArrayListExtra("foodDescription") as ArrayList<String>
        foodImage = intent.getStringArrayListExtra("foodImage") as ArrayList<String>
        foodIngredients = intent.getStringArrayListExtra("foodIngredients") as ArrayList<String>
        foodQuantity = intent.getIntegerArrayListExtra("foodQuantity") as ArrayList<Int>

        totalAmount = calculetTotalAmount().toString() + "$"
        binding.totalAmountTv.text = totalAmount
//+++++++++++++++++++ on create end +++++++++++++++++++++++++++++++++++
    }


    //=========== place Order start function =================================
    private fun placeOrder() {
        val userId = auth.currentUser!!.uid
        val currentTime = System.currentTimeMillis()
        val itemPushKey = database.child("orderDetails").push().key
        val orderDetails = OrderDetails(
            userId,
            name,
            foodName,
            foodImage,
            foodPrice,
            foodQuantity,
            address,
            totalAmount,
            phone,
            itemPushKey!!,
            currentTime,
            false,
            false
        )


        val databaseRef = database.child("orderDetails").child(itemPushKey)
        databaseRef.setValue(orderDetails).addOnSuccessListener {
            cartITemRemove()
            orderHistory(orderDetails)
            val bottomSheetMenuFragment = CongratsFragment()
            bottomSheetMenuFragment.show(supportFragmentManager, "test")

        }.addOnFailureListener {
            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun orderHistory(orderDetails: OrderDetails) {

        database.child("users").child(auth.currentUser!!.uid).child("buyHistory")
            .child(orderDetails.itemPushKey)
            .setValue(orderDetails).addOnSuccessListener {

            }.addOnFailureListener {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }

    }

    private fun cartITemRemove() {
        val databaseRef = database.child("users").child(auth.currentUser!!.uid).child("CartItem")
        databaseRef.removeValue()
    }


    //=========== place Order end function =================================


    //=============== calculation total amount start function ====================
    private fun calculetTotalAmount(): Int {
        var totalAmount = 0

        for (i in 0 until foodPrice.size) {
            val price = foodPrice[i]
            val lastChar = price.last()
            val priceInValue = if (lastChar == '$') {
                price.dropLast(1).toInt()
            } else {
                price.toInt()
            }
            var quantity = foodQuantity[i]
            totalAmount += priceInValue * quantity
        }
        return totalAmount
    }
    //=============== calculation total amount end function ====================


    // ==================== set user Data star function================
    private fun setUserData() {
        val user = auth.currentUser
        if (user != null) {
            val userId = user.uid

            val databaseRef = database.child("users").child(userId)
            databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val name = snapshot.child("userName").getValue(String::class.java) ?: ""
                        val address =
                            snapshot.child("userAddress").getValue(String::class.java) ?: ""
                        val phone =
                            snapshot.child("userPhoneNumber").getValue(String::class.java) ?: ""
                        binding.apply {
                            edName.setText(name)
                            edAddress.setText(address)
                            edPhone.setText(phone)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }

    }
    // ==================== set user Data end function================

}