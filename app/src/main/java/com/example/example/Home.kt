package com.example.example

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [Home.newInstance] factory method to
 * create an instance of this fragment.
 */


class Home : Fragment() {


    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var adapter: MyAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var newsArrayList : ArrayList<News>
    private lateinit var imageupl : ImageView
    private lateinit var imageId : Array<Int>
    private lateinit var process : ProgressBar
    private lateinit var url : String






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)


    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Home.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Home().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)

                }
            }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        process = view.findViewById(R.id.progressbar1)
        val username : String = arguments?.getString("username").toString()
        val jobList = ArrayList<String>()
        val imageList = ArrayList<String>()
        val infoList = ArrayList<String>()
        val businessList = ArrayList<String>()
        val db = Firebase.firestore
        db.collection("content")
            .get()
            .addOnSuccessListener { result ->

                for (doc in result) {

                    jobList.add(doc.id)
                    doc.getString("info")?.let {
                        infoList.add(it)
                    }

                    doc.getString("imgurl")?.let {
                        imageList.add(it)

                    }
                    doc.getString("business")?.let {
                        businessList.add(it)
                    }


                }

                process.isVisible = false
                newsArrayList = arrayListOf<News>()
                imageId = arrayOf(R.drawable.ic_baseline_person_24,R.drawable.ic_baseline_home_24,R.drawable.ic_baseline_settings_24)
                for (i in jobList.indices) {
                    val news = News(jobList[i],infoList[i],imageList[i])
                    newsArrayList.add(news)
                }
                val layoutManager = LinearLayoutManager(context)
                recyclerView = view.findViewById(R.id.recyclerview)
                recyclerView.layoutManager = layoutManager
                recyclerView.setHasFixedSize(true)
                adapter = MyAdapter(newsArrayList)
                recyclerView.adapter = adapter

                adapter.setOnItemClickListener(object : MyAdapter.onItemClickListener{
                    override fun onItemClick(position: Int) {

                        activity?.let{
                            val intent = Intent (it, JobInfo::class.java).putExtra("info",jobList[position]).putExtra("infotext",infoList[position]).putExtra("business",businessList[position]).putExtra("username",username)
                            it.startActivity(intent)
                        }

                    }
                })



            }


            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }





    }



}


