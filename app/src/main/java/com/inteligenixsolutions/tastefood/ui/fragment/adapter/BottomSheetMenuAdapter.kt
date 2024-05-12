package com.inteligenixsolutions.tastefood.ui.fragment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.inteligenixsolutions.tastefood.databinding.ItemMenuBinding
import com.inteligenixsolutions.tastefood.model.MenuItem

class BottomSheetMenuAdapter(private val list: MutableList<MenuItem>, val onItemClickListener: OnItemClick,
                             val context:Context) : RecyclerView.Adapter<BottomSheetMenuAdapter.MenuViewHolder>() {

     interface OnItemClick{
         fun itemClickListener(menuItem: MenuItem)
     }
    class MenuViewHolder(val itemMenuBinding: ItemMenuBinding) :
        RecyclerView.ViewHolder(itemMenuBinding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        return MenuViewHolder(ItemMenuBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }



    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
   list[position].let {
       holder.itemMenuBinding.apply {
           Glide.with(context).load(it.foodImage).into(menuFoodImageView);
           priceTv.text = "$"+it.foodPrice
           titelTv.text= it.foodName

       }
       holder.itemView.setOnClickListener {view->
           onItemClickListener.itemClickListener(it)
       }
   }


    }

    override fun getItemCount(): Int {
     return list.size
    }


}