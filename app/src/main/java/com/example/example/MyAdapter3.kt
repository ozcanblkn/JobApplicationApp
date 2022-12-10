package com.example.example

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.init
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.storage.FirebaseStorage
import io.grpc.Contexts


class MyAdapter3(private val newsList : ArrayList<News3>) : RecyclerView.Adapter<MyAdapter3.MyViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{

        fun onItemClick(state: String,position: Int)

    }

    fun setOnItemClickListener(listener: onItemClickListener){

        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_application,parent,false)
        return MyViewHolder(itemView,mListener)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = newsList[position]
        holder.Heading.text = "JOB NAME : "+currentItem.infotext
        holder.Heading2.text = currentItem.cv
        holder.Heading3.text = currentItem.name







    }


    override fun getItemCount(): Int {
        return newsList.size
    }
    class MyViewHolder(itemView : View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView)
    {
        val Heading : TextView = itemView.findViewById(R.id.infotext2)
        val Heading2 : TextView = itemView.findViewById(R.id.cv)
        val Heading3 : TextView = itemView.findViewById(R.id.name)
        val btnclick : Button = itemView.findViewById(R.id.acceptedbtn)
        val btnclick2 : Button = itemView.findViewById(R.id.declinedbtn)
        init {

           btnclick.setOnClickListener(){
                listener.onItemClick(btnclick.text.toString(),adapterPosition)
            }
            btnclick2.setOnClickListener(){
                listener.onItemClick(btnclick2.text.toString(),adapterPosition)
            }


        }
    }
}
