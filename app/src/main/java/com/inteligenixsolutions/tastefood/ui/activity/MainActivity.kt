package com.inteligenixsolutions.tastefood.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.inteligenixsolutions.tastefood.R
import com.inteligenixsolutions.tastefood.databinding.ActivityMainBinding
import com.inteligenixsolutions.tastefood.ui.fragment.CartFragment
import com.inteligenixsolutions.tastefood.ui.fragment.HistoryFragment
import com.inteligenixsolutions.tastefood.ui.fragment.HomeFragment
import com.inteligenixsolutions.tastefood.ui.fragment.ProfileFragment
import com.inteligenixsolutions.tastefood.ui.fragment.SearchFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)





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



    }

    private fun navigateTo(fragment: Fragment) {

        val transaction = (this as FragmentActivity).supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainerView, fragment)

        transaction.commit()
    }
}