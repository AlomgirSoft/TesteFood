package com.inteligenixsolutions.tastefood.ui.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.inteligenixsolutions.tastefood.R

import com.inteligenixsolutions.tastefood.databinding.ActivityRejisterBinding
import com.inteligenixsolutions.tastefood.model.Users

class RejisterActivity : AppCompatActivity() {

    lateinit var binding: ActivityRejisterBinding
    lateinit var database: DatabaseReference
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRejisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.navigationBarColor = resources.getColor(R.color.green, null)
            }
        }
        binding.allradyHaveBtn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.singBtn.setOnClickListener {
            val name = binding.edName.text.toString()
            val email = binding.edEmail.text.toString().trim()
            val password = binding.edPassword.text.toString().trim()
            if (name.isEmpty()) {
                binding.edName.error = "input your name"
            } else if (email.isEmpty()) {
                binding.edEmail.error = "input your email"
            } else if (password.isEmpty()) {
                binding.edPassword.error = "input your password"
            } else {
                registerUser(name, email, password)
            }

        }
    }

    //=======================On create end========================================


    //+++++++++++++++++ setup user register ++++++++++++++++++++++
    private fun registerUser(name: String, email: String, password: String) {

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {

            if (it.isSuccessful) {

                val userRegister = Users(auth.currentUser!!.uid, name, email, password)

                database.child("users").child(auth.currentUser!!.uid).setValue(userRegister)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {

                            startActivity(Intent(this, LoginActivity::class.java))
                            Toast.makeText(this, "successfully", Toast.LENGTH_SHORT).show()
                        }
                    }

            } else {
                Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Log.d("TAG", it.message.toString())

        }

    }
    //+++++++++++++++++ setup user register +++++++++++++++++++++++


}