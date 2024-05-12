package com.inteligenixsolutions.tastefood.ui.fragment.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.inteligenixsolutions.tastefood.databinding.ItemHistoryBinding
import com.inteligenixsolutions.tastefood.databinding.ItemPopulerBinding

class HistoryAdapter(val context:Context,val  list: MutableList<String>, val image:MutableList<String>, val price:MutableList<String>)  : RecyclerView.Adapter<HistoryAdapter.PopularViewHolder>() {


    class PopularViewHolder(val itemHistoryBinding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(itemHistoryBinding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        return PopularViewHolder(ItemHistoryBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
     return list.size
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        val item= list[position]

        val price= price[position]
       val  image=image[position]
        val uri = Uri.parse(image)
        Glide.with(context).load(uri).into(holder.itemHistoryBinding.historyFoodImageView)


        holder.itemHistoryBinding.recentPriceTv.text= "$$price"
        holder.itemHistoryBinding.recentBuyFoodNameTV.text= item
    }
}