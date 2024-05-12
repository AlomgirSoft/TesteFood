package com.inteligenixsolutions.tastefood.ui.fragment.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.inteligenixsolutions.tastefood.databinding.ItemCartBinding
import com.inteligenixsolutions.tastefood.databinding.ItemPopulerBinding

class CartItemAdapter(
    val context: Context,
    private val foodName: MutableList<String>,
    private val foodImage: MutableList<String>,
    private val foodPrice: MutableList<String>,
    private val foodDescription: MutableList<String>,
    private val foodIngredients: MutableList<String>,
    private val foodQuantity: MutableList<Int>

) : RecyclerView.Adapter<CartItemAdapter.ItemCartViewHolder>() {

    private val auth = FirebaseAuth.getInstance()

    // private val itemQuantitiy = IntArray(foodName.size) { 1 }
    init {
        val database = FirebaseDatabase.getInstance()
        val userId = auth.currentUser!!.uid
        val cartNumber = foodName.size
        itemQuantity = IntArray(cartNumber) { 1 }
        databaseReference = database.reference.child("users").child(userId).child("CartItem")

    }

    companion object {
        var itemQuantity: IntArray = intArrayOf()
        private lateinit var databaseReference: DatabaseReference
    }

    class ItemCartViewHolder(val itemCartBinding: ItemCartBinding) :
        RecyclerView.ViewHolder(itemCartBinding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemCartViewHolder {
        return ItemCartViewHolder(
            ItemCartBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return foodName.size
    }


    override fun onBindViewHolder(holder: ItemCartViewHolder, position: Int) {
        val item = foodName[position]

        val foodPrice = foodPrice[position]
        val foodImage = foodImage[position]
        val foodQuantity = foodQuantity[position]
        val imageUri = Uri.parse(foodImage)
        holder.itemCartBinding.priceTv.text = "$$foodPrice"
        holder.itemCartBinding.foodNameTv.text = item
        holder.itemCartBinding.countItemTv.text = foodQuantity.toString()

        Glide.with(context).load(imageUri).into(holder.itemCartBinding.popularFoodImageView)

        holder.itemCartBinding.mainsTvBtn.setOnClickListener {
            mainasItem(position)
            holder.itemCartBinding.countItemTv.text = itemQuantity[position].toString()
        }

        holder.itemCartBinding.addTvBtn.setOnClickListener {
            itemAdd(position)
            holder.itemCartBinding.countItemTv.text = itemQuantity[position].toString()

        }
        holder.itemCartBinding.deleteBtn.setOnClickListener {

            deleteItem(position)


        }


    }

    private fun mainasItem(position: Int) {
        if (itemQuantity[position] > 1) {
            itemQuantity[position]--
            foodQuantity[position]= itemQuantity[position]




        }
    }

    private fun itemAdd(position: Int) {
        if (itemQuantity[position] < 10) {
            itemQuantity[position]++
            foodQuantity[position]= itemQuantity[position]





        }
    }

    private fun deleteItem(position: Int) {

        getUniqueKeyAtPosition(position) { uniqueKey ->
            if (uniqueKey != null) {
                removeItem(position, uniqueKey)
            }
        }

    }

    private fun removeItem(position: Int, uniqueKey: String) {


        if (uniqueKey != null) {
            databaseReference.child(uniqueKey).removeValue().addOnSuccessListener {
                foodName.removeAt(position)
                foodQuantity.removeAt(position)
                foodDescription.removeAt(position)
                foodIngredients.removeAt(position)
                foodImage.removeAt(position)
                foodPrice.removeAt(position)
                Toast.makeText(context, "delete item", Toast.LENGTH_SHORT).show()

                itemQuantity =
                    itemQuantity.filterIndexed { index, i -> index != position }.toIntArray()
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, foodName.size)

            }.addOnFailureListener {
                Toast.makeText(context, "delete fialed", Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun getUniqueKeyAtPosition(position: Int, onComplete: (String?) -> Unit) {

        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var uniqueKey: String? = null

                snapshot.children.forEachIndexed { index, dataSnapshot ->

                    if (index == position) {
                        uniqueKey = dataSnapshot.key
                        return@forEachIndexed
                    }
                }
                onComplete(uniqueKey)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }


    // get update quantity
    fun getUpdateItemQuantities(): MutableList<Int> {
        val itemQuantities = mutableListOf<Int>()
        itemQuantities.addAll(foodQuantity)
        return itemQuantities

    }


}