package com.inteligenixsolutions.tastefood.ui.fragment.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.inteligenixsolutions.tastefood.databinding.ItemMenuBinding
import com.inteligenixsolutions.tastefood.model.MenuItem

class SearchMenuAdapter(val context: Context,val  list: List<MenuItem>, val itemClickListener: ItemClickListener)  : RecyclerView.Adapter<SearchMenuAdapter.MenuViewHolder>() {

  interface  ItemClickListener{
      fun onItemClick(menuItem: MenuItem)
  }
    class MenuViewHolder(val itemMenuBinding: ItemMenuBinding) :
        RecyclerView.ViewHolder(itemMenuBinding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        return MenuViewHolder(ItemMenuBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }



    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val item= list[position]



        holder.itemMenuBinding.priceTv.text= "$"+item.foodPrice
        holder.itemMenuBinding.titelTv.text= item.foodName
        val image= item.foodImage
        val uri= Uri.parse(image)
        Glide.with(context).load(uri).into(holder.itemMenuBinding.menuFoodImageView)

        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(item)
        }
    }

    override fun getItemCount(): Int {
     return list.size
    }


}