package com.inteligenixsolutions.tastefood.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.inteligenixsolutions.tastefood.R
import com.inteligenixsolutions.tastefood.databinding.FragmentNotificationBottomSheetBinding
import com.inteligenixsolutions.tastefood.ui.fragment.adapter.NotificationAdapter


class NotificationBottomSheetFragment : BottomSheetDialogFragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }
lateinit var binding:FragmentNotificationBottomSheetBinding
lateinit var adapter: NotificationAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNotificationBottomSheetBinding.inflate(layoutInflater)


        val  iamgeList= listOf(
            R.drawable.ordercanceled,
            R.drawable.orderhasbeentaken,
            R.drawable.illustration
        )
        val  nameList= listOf("Your order has been Canceled Successfully","Order has been taken by the driver","Congrats Your Order Placed")
        adapter = NotificationAdapter(image = iamgeList, list = nameList)
        binding.notificationRcv.adapter = adapter





        return binding.root
    }


}