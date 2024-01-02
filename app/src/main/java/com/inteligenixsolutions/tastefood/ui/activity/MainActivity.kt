package com.inteligenixsolutions.tastefood.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.inteligenixsolutions.tastefood.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


      binding.nextBtn.setOnClickListener {
          startActivity(Intent(this, LoginActivity::class.java))
          finish()
      }



    }
}