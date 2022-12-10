package com.example.example

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.storage.FirebaseStorage


private lateinit var url : String

class MyAdapter(private val newsList : ArrayList<News>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{

        fun onItemClick(position: Int)

    }

    fun setOnItemClickListener(listener: onItemClickListener){

        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return MyViewHolder(itemView,mListener)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = newsList[position]
        holder.Heading.text = currentItem.heading
        holder.Heading2.text = currentItem.heading2
        Glide.with(holder.itemView).load("${currentItem.image}").error(R.drawable.ic_baseline_person_24).into(holder.imageupl)
        }











    override fun getItemCount(): Int {
        return newsList.size
    }
    class MyViewHolder(itemView : View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView)
    {
        val Heading : TextView = itemView.findViewById(R.id.tvHeading)
        val Heading2 : TextView = itemView.findViewById(R.id.infotext2)
        val imageupl : ImageView = itemView.findViewById(R.id.title_image)

        init {

            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }
}
