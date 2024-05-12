package com.inteligenixsolutions.tastefood.ui.fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inteligenixsolutions.tastefood.databinding.ItemNotificationBinding
import com.inteligenixsolutions.tastefood.databinding.ItemPopulerBinding

class NotificationAdapter(val  list: List<String>, val image:List<Int>)  : RecyclerView.Adapter<NotificationAdapter.PopularViewHolder>() {


    class PopularViewHolder(val itemNotificationBinding: ItemNotificationBinding) :
        RecyclerView.ViewHolder(itemNotificationBinding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        return PopularViewHolder(ItemNotificationBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
     return list.size
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        val item= list[position]


       val  image=image[position]

        holder.itemNotificationBinding.notificationImageView.setImageResource(image)

        holder.itemNotificationBinding.titelTv.text= item
    }
}