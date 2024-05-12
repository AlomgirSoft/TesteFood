package com.inteligenixsolutions.tastefood.ui.fragment.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.inteligenixsolutions.tastefood.databinding.ItemRecentBuyBinding

class RecentBuyOrderAdapter(
    var context: Context,
    var foodNames: MutableList<String>,
    var foodImage: MutableList<String>,
    var foodPrice: MutableList<String>,
    var foodQuantities: MutableList<Int>
) : RecyclerView.Adapter<RecentBuyOrderAdapter.RecentBuyOrderVH>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentBuyOrderVH {
       return RecentBuyOrderVH(ItemRecentBuyBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int =foodNames.size
    override fun onBindViewHolder(holder: RecentBuyOrderVH, position: Int) {

          val foodName= foodNames[position]
          val foodImage= foodImage[position]
          val foodPrice= foodPrice[position]
          val foodQuantities= foodQuantities[position]
        val image= Uri.parse(foodImage)


        holder.binding.foodNameTv.text=foodName
        holder.binding.priceTv.text= "$$foodPrice"
        holder.binding.quantitiesTv.text=foodQuantities.toString()
        Glide.with(context).load(image).into(holder.binding.popularFoodImageView)



    }


class RecentBuyOrderVH(val binding: ItemRecentBuyBinding) :
        RecyclerView.ViewHolder(binding.root)

    }

