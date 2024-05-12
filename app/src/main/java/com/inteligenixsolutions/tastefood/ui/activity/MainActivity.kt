package com.inteligenixsolutions.tastefood.ui.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.firebase.auth.FirebaseAuth
import com.inteligenixsolutions.tastefood.ui.fragment.NotificationBottomSheetFragment
import com.inteligenixsolutions.tastefood.R
import com.inteligenixsolutions.tastefood.databinding.ActivityMainBinding
import com.inteligenixsolutions.tastefood.ui.fragment.CartFragment
import com.inteligenixsolutions.tastefood.ui.fragment.HistoryFragment
import com.inteligenixsolutions.tastefood.ui.fragment.HomeFragment
import com.inteligenixsolutions.tastefood.ui.fragment.ProfileFragment
import com.inteligenixsolutions.tastefood.ui.fragment.SearchFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    lateinit var auth: FirebaseAuth
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth= FirebaseAuth.getInstance()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor = resources.getColor(R.color.green, null)
        }



binding.notificationAlert.setOnClickListener {
    val  bottomSheetMenuFragment = NotificationBottomSheetFragment()
    bottomSheetMenuFragment.show(supportFragmentManager,"test")
}


        binding.bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.homeItem -> {

                    navigateTo(HomeFragment())
                    true
                }

                R.id.cartItem -> {
                    navigateTo(CartFragment())

                    true
                }

                R.id.searchItem -> {


                    navigateTo(SearchFragment())
                    true
                }

                R.id.historyItem -> {
                    navigateTo(HistoryFragment())

                    true
                }

                R.id.profileItem -> {
                    navigateTo(ProfileFragment())

                    true
                }
                // Add more cases as needed
                else -> false
            }
        }
binding.notificationAlert.setOnClickListener {

    auth.signOut()
    startActivity(Intent(this,LoginActivity::class.java))
    finish()
}


    }





    private fun navigateTo(fragment: Fragment) {

        val transaction = (this as FragmentActivity).supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainerView, fragment)

        transaction.commit()
    }
}