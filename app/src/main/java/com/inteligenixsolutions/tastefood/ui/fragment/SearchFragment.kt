package com.inteligenixsolutions.tastefood.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import com.inteligenixsolutions.tastefood.databinding.FragmentSearchBinding
import com.inteligenixsolutions.tastefood.model.MenuItem
import com.inteligenixsolutions.tastefood.ui.activity.ItemDetailsActivity
import com.inteligenixsolutions.tastefood.ui.fragment.adapter.SearchMenuAdapter
import com.inteligenixsolutions.tastefood.uitils.dismissProgressDialog
import com.inteligenixsolutions.tastefood.uitils.showProgressDialog


class SearchFragment : Fragment(),SearchMenuAdapter.ItemClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    lateinit var auth: FirebaseAuth
    lateinit var database: FirebaseDatabase
    lateinit var binding: FragmentSearchBinding
    lateinit var adapter: SearchMenuAdapter

    val orginalMenuItemList = mutableListOf<MenuItem>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(layoutInflater)
        auth = FirebaseAuth.getInstance()
        database= FirebaseDatabase.getInstance()




        setSearchView()
        getMenuItem()




        return binding.root
    }

    private fun getMenuItem() {
        var dialog= showProgressDialog(requireContext(),"please wait")
        val databaseRef= database.reference.child("menu")
         databaseRef.addListenerForSingleValueEvent(object : ValueEventListener{
             override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children){
                    val menuItem= dataSnapshot.getValue(MenuItem::class.java)
                    menuItem?.let {
                      orginalMenuItemList.add(it)
                        dismissProgressDialog(dialog)
                    }
                }

                 setShowAllMenu()
             }

             override fun onCancelled(error: DatabaseError) {
                 TODO("Not yet implemented")
             }
         })
    }

    private fun setShowAllMenu() {
        val filterMenuItem= ArrayList(orginalMenuItemList)
        setAdapter(filterMenuItem)

    }

    private fun setAdapter(filterMenuItem: List<MenuItem>) {
        adapter = SearchMenuAdapter(requireContext(),filterMenuItem,this)
        binding.menuRcv.adapter = adapter
    }

    private fun setSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // Called when the user submits the query (e.g., presses the "Search" button)

                filterMenuItem(query)

                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filterMenuItem(newText)

                return true
            }
        })
    }



    private fun filterMenuItem(query: String) {
      val filterMenu= orginalMenuItemList.filter {
          it.foodName?.contains(query,ignoreCase = true)==true
      }
setAdapter(filterMenu)
        }

    override fun onItemClick(menuItem: MenuItem) {
        val intent = Intent(activity, ItemDetailsActivity::class.java)

        // Attach the Bundle to the Intent
        intent.putExtra("menuItem", menuItem)

        // Start the ItemDetailsActivity
        startActivity(intent)
    }


}