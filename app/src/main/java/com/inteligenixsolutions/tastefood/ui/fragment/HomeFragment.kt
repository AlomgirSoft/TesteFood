package com.inteligenixsolutions.tastefood.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment

import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


import com.inteligenixsolutions.tastefood.R
import com.inteligenixsolutions.tastefood.databinding.FragmentHomeBinding
import com.inteligenixsolutions.tastefood.model.MenuItem
import com.inteligenixsolutions.tastefood.ui.activity.ItemDetailsActivity
import com.inteligenixsolutions.tastefood.ui.fragment.adapter.BottomSheetMenuAdapter
import com.inteligenixsolutions.tastefood.uitils.dismissProgressDialog
import com.inteligenixsolutions.tastefood.uitils.showProgressDialog


class HomeFragment : Fragment() ,BottomSheetMenuAdapter.OnItemClick{


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

//   lateinit var adapter:PopularAdapter
    lateinit var menuList: MutableList<MenuItem>
    lateinit var dbRef:FirebaseDatabase
    lateinit var adapterMenu:BottomSheetMenuAdapter


  lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(layoutInflater)
        dbRef = FirebaseDatabase.getInstance()
        menuList= mutableListOf()

        val imageList = ArrayList<SlideModel>() // Create image list

        imageList.add(SlideModel(R.drawable.banner5, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner6, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner7, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner8, ScaleTypes.FIT))
        binding.imageSlider.setImageList(imageList)


        setPopularItem()






        binding.viewMenuBtn.setOnClickListener {
            val  bottomSheetMenuFragment = BottomSheetMenuFragment()
            bottomSheetMenuFragment.show(parentFragmentManager,"test")

        }






        return binding.root
    }



    private fun setPopularItem() {
        var dialog= showProgressDialog(requireContext(),"please wait")
    var databaseRef =dbRef.reference.child("menu")

        databaseRef.addListenerForSingleValueEvent(object : ValueEventListener{
        override fun onDataChange(snapshot: DataSnapshot) {
            menuList.clear()
           for ( dataSnapshot in  snapshot.children){
               val menuItem = dataSnapshot.getValue(MenuItem::class.java)
               menuItem?.let {
                   menuList.add(it)
                   dismissProgressDialog(dialog)
               }
           }
            rendomPopularMenuItem()
        }

        override fun onCancelled(error: DatabaseError) {
           Log.d("popularMenuItem",error.message)
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
    private fun rendomPopularMenuItem() {
        val index= menuList.indices.toList().shuffled()
        val menuItemToShow=6
        val subsetMenuItem= index.take(menuItemToShow).map { menuList[it] }

        adapterMenu = BottomSheetMenuAdapter(subsetMenuItem.toMutableList(), this,requireContext())
        binding.popularRcv.adapter = adapterMenu
    }

}