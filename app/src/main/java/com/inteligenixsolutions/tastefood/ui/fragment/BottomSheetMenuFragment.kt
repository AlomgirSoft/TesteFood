package com.inteligenixsolutions.tastefood.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import com.inteligenixsolutions.tastefood.R
import com.inteligenixsolutions.tastefood.databinding.FragmentBottomSheetMenuBinding
import com.inteligenixsolutions.tastefood.model.MenuItem
import com.inteligenixsolutions.tastefood.ui.activity.ItemDetailsActivity
import com.inteligenixsolutions.tastefood.ui.fragment.adapter.BottomSheetMenuAdapter


class BottomSheetMenuFragment : BottomSheetDialogFragment(),BottomSheetMenuAdapter.OnItemClick {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }
 lateinit var binding: FragmentBottomSheetMenuBinding
 lateinit var dbRef: FirebaseDatabase

 lateinit var adapter: BottomSheetMenuAdapter
 lateinit var menuList: MutableList<MenuItem>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentBottomSheetMenuBinding.inflate(layoutInflater)
        dbRef= FirebaseDatabase.getInstance()




        setMenuItem()












        return binding.root
    }

    private fun setMenuItem() {
        menuList= mutableListOf()
       val reference= dbRef.reference.child("menu")
        reference.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                menuList.clear()
                for ( dataSnapshot in snapshot.children){
                    val allMenu= dataSnapshot.getValue(MenuItem::class.java)
                    allMenu?.let {
                        menuList.add(it)
                    }

                }
                adapter= BottomSheetMenuAdapter(menuList,this@BottomSheetMenuFragment,requireContext())

                binding.memuRcv.adapter = adapter

            }

            override fun onCancelled(error: DatabaseError) {
               Log.d("menuError",error.message)
            }

        })
    }

    override fun itemClickListener(menuItem: MenuItem) {


        // Create an Intent to start the ItemDetailsActivity
        val intent = Intent(activity, ItemDetailsActivity::class.java)

        // Attach the Bundle to the Intent
        intent.putExtra("menuItem", menuItem)

        // Start the ItemDetailsActivity
        startActivity(intent)


    }


}