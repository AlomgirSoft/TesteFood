package com.inteligenixsolutions.tastefood.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

import com.inteligenixsolutions.tastefood.R
import com.inteligenixsolutions.tastefood.databinding.FragmentCongratsBinding
import com.inteligenixsolutions.tastefood.ui.activity.MainActivity


class CongratsFragment : BottomSheetDialogFragment() {

lateinit var binding: FragmentCongratsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       binding = FragmentCongratsBinding.inflate(layoutInflater)

        binding.goToHomeBtn.setOnClickListener {
            startActivity(Intent(requireContext(),MainActivity::class.java))

        }
        return binding.root
    }


}