package com.inteligenixsolutions.tastefood.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.inteligenixsolutions.tastefood.R
import com.inteligenixsolutions.tastefood.databinding.ActivityLoginBinding
import com.inteligenixsolutions.tastefood.ui.fragment.HomeFragment

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var googleSignInClient: GoogleSignInClient

    lateinit var auth: FirebaseAuth
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth=FirebaseAuth.getInstance()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor = resources.getColor(R.color.green, null)
        }

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.deflate_web_client_id)).requestEmail().build()


        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)

        binding.createAccoutBtn.setOnClickListener {
            startActivity(Intent(this,RejisterActivity::class.java))
        }



        binding.loginBtn.setOnClickListener {
            val email= binding.edEmail.text.toString().trim()
            val password= binding.edPassword.text.toString().trim()

            if (email.isEmpty()){
                binding.edEmail.error="input your email"
            }else if (password.isEmpty()){
                binding.edPassword.error="input your password"
            }else{
                userLogin(email,password)
            }
        }

        binding.googleLoginBtn.setOnClickListener {
            val googleIntent = googleSignInClient.signInIntent
            launcher.launch(googleIntent)
        }

        //============ on create end ================================
    }



    private fun userLogin(email: String, password: String) {
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
            if (it.isSuccessful){
                startActivity(Intent(this,MainActivity::class.java))
                Toast.makeText(this, "successfully", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Log.d("TAG",it.message.toString())
        }



    }
    //===================launcher google login start===========================
    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                if (task.isSuccessful) {
                    val account: GoogleSignInAccount = task.result
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    auth.signInWithCredential(credential).addOnCompleteListener {
                        if (it.isSuccessful) {
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                            Toast.makeText(this, "google login successfully", Toast.LENGTH_SHORT)
                                .show()
                        } else
                            Toast.makeText(this, "google login filed", Toast.LENGTH_SHORT).show()


                    }.addOnFailureListener {
                        Log.d("loginFileGoogle", it.message.toString())
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }
                } else Toast.makeText(this, "google login filed", Toast.LENGTH_SHORT).show()
            }

        }
//===================launcher google login end===========================



}