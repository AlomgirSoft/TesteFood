package com.inteligenixsolutions.tastefood.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel

import com.inteligenixsolutions.tastefood.R
import com.inteligenixsolutions.tastefood.databinding.FragmentHomeBinding
import com.inteligenixsolutions.tastefood.ui.fragment.adapter.PopularAdapter


class HomeFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

   lateinit var adapter:PopularAdapter


  lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(layoutInflater)


        val imageList = ArrayList<SlideModel>() // Create image list

// imageList.add(SlideModel("String Url" or R.drawable)
// imageList.add(SlideModel("String Url" or R.drawable, "title") You can add title

        imageList.add(SlideModel(R.drawable.banner1, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.bannar3, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner_c, ScaleTypes.FIT))

        binding.imageSlider.setImageList(imageList)


        val foodName= listOf("Burger","sandwich","Beef Chicken","picza")
        val foodPrice= listOf("$7","$8","$15","12$")
        val foodImage= listOf(R.drawable.food1,R.drawable.food2,R.drawable.food3,R.drawable.food4)
        adapter= PopularAdapter(foodName,foodImage,foodPrice)

        binding.popularRcv.adapter = adapter






        return binding.root
    }


}