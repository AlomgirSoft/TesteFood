package com.inteligenixsolutions.tastefood.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.inteligenixsolutions.tastefood.R
import com.inteligenixsolutions.tastefood.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.createAccoutBtn.setOnClickListener {
            startActivity(Intent(this,RejisterActivity::class.java))
        }

        binding.loginBtn.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }
    }
}