package com.example.example

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



class MyAdapter2(private val newsList : ArrayList<News2>) : RecyclerView.Adapter<MyAdapter2.MyViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{

        fun onItemClick(position: Int)

    }

    fun setOnItemClickListener(listener: onItemClickListener){

        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_state,parent,false)
        return MyViewHolder(itemView,mListener)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = newsList[position]
        holder.Heading.text = currentItem.jobtext
        holder.Heading2.text = currentItem.statetext
            Glide.with(holder.itemView)
                .load("https://firebasestorage.googleapis.com/v0/b/exampledb-5569a.appspot.com/o/aras.png?alt=media&token=32f893d1-2f31-4fff-bbca-3a3be1ae7d08")
                .into(holder.imageupl)







    }


    override fun getItemCount(): Int {
        return newsList.size
    }
    class MyViewHolder(itemView : View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView)
    {
        val Heading : TextView = itemView.findViewById(R.id.jobtext)
        val Heading2 : TextView = itemView.findViewById(R.id.statetext)
        val imageupl : ImageView = itemView.findViewById(R.id.image)
        init {

            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }
}
