package com.inteligenixsolutions.tastefood.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.inteligenixsolutions.tastefood.R
import com.inteligenixsolutions.tastefood.databinding.ActivityStartBinding

class StartActivity : AppCompatActivity() {
    lateinit var binding:ActivityStartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.nextBtn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}