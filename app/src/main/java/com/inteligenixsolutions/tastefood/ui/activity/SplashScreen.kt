package com.inteligenixsolutions.tastefood.ui.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.google.firebase.auth.FirebaseAuth
import com.inteligenixsolutions.tastefood.R

class SplashScreen : AppCompatActivity() {

    lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        auth= FirebaseAuth.getInstance()
        val currentUser=auth.currentUser?.uid
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor = resources.getColor(R.color.green, null)
        }

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            // Start the second activity
            if (currentUser != null){
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }else{
                val intent = Intent(this, StartActivity::class.java)
                startActivity(intent)
                finish()
            }

            // Finish the current activity if needed

        }, 3000)

        }

}