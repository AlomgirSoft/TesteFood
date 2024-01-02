package com.inteligenixsolutions.tastefood.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.inteligenixsolutions.tastefood.R
import com.inteligenixsolutions.tastefood.databinding.ActivityChooseLocationBinding

class ChooseLocationActivity : AppCompatActivity() {

    lateinit var binding: ActivityChooseLocationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val location = arrayOf("Dhaka", "Rajshahi", "shahjadpur","gazipur")

        val adapter = ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,location)
        val autoCompleteTextView = binding.edListOfLocation
        autoCompleteTextView.setAdapter(adapter)
    }
}