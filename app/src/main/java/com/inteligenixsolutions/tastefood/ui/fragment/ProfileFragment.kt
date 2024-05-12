package com.inteligenixsolutions.tastefood.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import com.inteligenixsolutions.tastefood.R
import com.inteligenixsolutions.tastefood.databinding.FragmentProfileBinding
import com.inteligenixsolutions.tastefood.model.Users


class ProfileFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
lateinit var binding: FragmentProfileBinding
lateinit var auth:FirebaseAuth
lateinit var database: FirebaseDatabase
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater)
        auth = FirebaseAuth.getInstance()
        database= FirebaseDatabase.getInstance()

        binding.saveInformationBtn.background = null

        setUserData()

        binding.saveInformationBtn.setOnClickListener {


            val name = binding.edName.text.toString()
            val address = binding.edAddress.text.toString()
            val email = binding.edEmail.text.toString()
            val phone = binding.edPhone.text.toString()

            setUpdateUserProfile(name,address,email,phone)
        }
   binding.apply {
       edName.isEnabled= false
       edAddress.isEnabled= false
       edEmail.isEnabled= false
       edPhone.isEnabled= false

       binding.editBtn.setOnClickListener {
            edName.isEnabled = !edName.isEnabled
           edAddress.isEnabled = !edAddress.isEnabled
           edEmail.isEnabled = !edEmail.isEnabled
           edPhone.isEnabled = !edPhone.isEnabled

       }
   }



        return binding.root

        //++++++++++++++++++++++++ on create end +++++++++++++++++++++
    }

    private fun setUpdateUserProfile(name: String, address: String, email: String, phone: String) {
       val userId= auth.currentUser?.uid
        if (userId != null){
            val databaseRef= database.reference.child("users").child(userId)
            val userData = hashMapOf(
                "userName" to name,
                "userAddress" to address,
                "userEmail" to email,
                "userPhoneNumber" to phone,
            )

            databaseRef.setValue(userData).addOnCompleteListener {
                if (it.isSuccessful){
                    Toast.makeText(requireContext(), "profile update successfully", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setUserData() {
        val user= auth.currentUser
        if (user !=null){
            val userId= user.uid

            val dataBaseRef=database.reference.child("users").child(userId)
             dataBaseRef.addListenerForSingleValueEvent(object :ValueEventListener{
                 override fun onDataChange(snapshot: DataSnapshot) {
                   if (snapshot.exists()){
                       val user= snapshot.getValue(Users::class.java)
                       if (user!=null){

                              binding.edName.setText(user.userName)
                           binding.edAddress.setText(user.userAddress)
                           binding.edEmail.setText(user.userEmail)
                           binding.edPhone.setText(user.userPhoneNumber)

                       }
                   }
                 }

                 override fun onCancelled(error: DatabaseError) {
                     TODO("Not yet implemented")
                 }

             })
        }
    }



}