package com.inteligenixsolutions.tastefood.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.inteligenixsolutions.tastefood.R
import com.inteligenixsolutions.tastefood.databinding.ActivityRejisterBinding

class RejisterActivity : AppCompatActivity() {

    lateinit var binding: ActivityRejisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRejisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.allradyHaveBtn.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }

        binding.singBtn.setOnClickListener {
            startActivity(Intent(this,ChooseLocationActivity::class.java))
        }
    }
}