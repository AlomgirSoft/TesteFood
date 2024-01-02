package com.inteligenixsolutions.tastefood.ui.fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inteligenixsolutions.tastefood.databinding.ItemPopulerBinding

class PopularAdapter(val  list: List<String>,val image:List<Int>,val price:List<String>)  : RecyclerView.Adapter<PopularAdapter.PopularViewHolder>() {


    class PopularViewHolder(val itemPopularBinding: ItemPopulerBinding) :
        RecyclerView.ViewHolder(itemPopularBinding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        return PopularViewHolder(ItemPopulerBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
     return list.size
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        val item= list[position]

        val price= price[position]
       val  image=image[position]

        holder.itemPopularBinding.popularFoodImageView.setImageResource(image)
        holder.itemPopularBinding.priceTv.text= price
        holder.itemPopularBinding.titelTv.text= item
    }
}