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
import androidx.appcompat.app.AlertDialog
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
 * Use the [Profile.newInstance] factory method to
 * create an instance of this fragment.
 */


class Profile : Fragment() {


    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var adapter: MyAdapter2
    private lateinit var recyclerView: RecyclerView
    private lateinit var newsArrayList : ArrayList<News2>
    private lateinit var process : ProgressBar






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
        return inflater.inflate(R.layout.fragment_profile, container, false)


    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Profile.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Profile().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)

                }
            }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        process = view.findViewById(R.id.progressbar2)
        val username : String = arguments?.getString("username").toString()


        val jobList = ArrayList<String>()
        val stateList = ArrayList<String>()


        val db = Firebase.firestore
        db.collection("jobapplication")
            .get()
            .addOnSuccessListener { result ->

                for (doc in result) {
                    if(doc.getString("name") == username)
                    {
                        doc.getString("jobinfo")?.let {
                            jobList.add(it)
                        }
                        doc.getString("state")?.let {
                            stateList.add(it)
                        }
                    }

                }
                if(jobList.size>0)
                {
                    process.isVisible = false
                    newsArrayList = arrayListOf<News2>()
                    for (i in jobList.indices) {
                        val news = News2(jobList[i],stateList[i])
                        newsArrayList.add(news)
                    }
                    val layoutManager = LinearLayoutManager(context)
                    recyclerView = view.findViewById(R.id.recyclerview2)
                    recyclerView.layoutManager = layoutManager
                    recyclerView.setHasFixedSize(true)
                    adapter = MyAdapter2(newsArrayList)
                    recyclerView.adapter = adapter

                    adapter.setOnItemClickListener(object : MyAdapter2.onItemClickListener{
                        override fun onItemClick(position: Int) {

                            activity?.let{
                                val intent = Intent (it, JobInfo::class.java)
                                it.startActivity(intent)
                            }

                        }
                    })


                }
                else
                {
                  /*  val builder = AlertDialog.Builder(activity?.let {  })
                    builder.setTitle("Kayıt Bulunamadı!")
                    builder.setMessage("Yeni Kayıt Oluşturmak istermisiniz?")

                    builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                         activity?.let{
                                val intent = Intent (it, JobInfo::class.java)
                                it.startActivity(intent)
                            }
                    }

                    builder.setNegativeButton(android.R.string.no) { dialog, which ->
                         activity?.let{
                                val intent = Intent (it, JobInfo::class.java)
                                it.startActivity(intent)
                            }
                    }
                    builder.show()*/
                }


            }




    }



}


