package com.example.example

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.provider.SyncStateContract.Helpers.update
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import java.io.InputStream
import java.text.FieldPosition
import java.util.ArrayList

lateinit var newsArrayList : ArrayList<News3>
lateinit var recyclerView: RecyclerView
lateinit var adapter: MyAdapter3

class ProfileActivity : AppCompatActivity() {

lateinit var username : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        username = intent.getStringExtra("username").toString()
        val tempnameList = ArrayList<String>()
        val jobList = ArrayList<String>()
        val cvList = ArrayList<String>()
        val db = FirebaseFirestore.getInstance()
        db.collection("jobapplication")
            .get()
            .addOnSuccessListener { result ->
                for (doc in result) {

                    if (doc.getString("business").toString() == username) {
                        if (doc.getString("state").toString() == "waiting")
                        {
                            jobList.add(doc.getString("jobinfo").toString())
                            tempnameList.add(doc.getString("name").toString())
                        }

                    }



                }
                if(jobList.isNotEmpty())
                {
                    db.collection("user")
                        .get()
                        .addOnSuccessListener { result2 ->
                            for (i in jobList.indices)
                            {
                                for (doc2 in result2) {
                                    if (doc2.id == tempnameList[i]) {
                                        if (doc2.getString("cv").toString() != ""){
                                            cvList.add(doc2.getString("cv").toString())
                                        }
                                        else
                                        {
                                            cvList.add("CV is not Found!")
                                        }

                                    }
                                }
                            }

                            findViewById<ProgressBar>(R.id.progressbar2).isVisible = false
                            newsArrayList = arrayListOf()
                            for (i in jobList.indices) {
                                val news = News3(jobList[i], cvList[i] , tempnameList[i])
                                newsArrayList.add(news)
                            }
                            val layoutManager = LinearLayoutManager(this)
                            recyclerView = findViewById(R.id.recyclerview3)
                            recyclerView.layoutManager = layoutManager
                            recyclerView.setHasFixedSize(true)
                            adapter = MyAdapter3(newsArrayList)
                            recyclerView.adapter = adapter

                            adapter.setOnItemClickListener(object : MyAdapter3.onItemClickListener {
                                override fun onItemClick(state: String, position: Int) {

                                        getid(jobList[position],state)
                                        newsArrayList.removeAt(position)
                                        jobList.removeAt(position)
                                        adapter.notifyItemRemoved(position)
                                        adapter.notifyItemRangeChanged(position, newsArrayList.size)



                                }

                            })


                        }
                }
                else {
                    findViewById<ProgressBar>(R.id.progressbar2).isVisible = true


                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Kayıt Bulunamadı!")
                    builder.setMessage("Yeni Kayıt Oluşturmak istermisiniz?")

                    builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                        val intent = Intent(this, CreatejobActivity::class.java).putExtra("username",username)
                        startActivity(intent)
                    }

                    builder.setNegativeButton(android.R.string.no) { dialog, which ->
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                    }
                    builder.show()


                }

            }


        }
    fun builddialag(text : String){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Kayıt Bulunamadı!")
        builder.setMessage(text)

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            val intent = Intent(this, DecisionActivity::class.java).putExtra("username",username)
            startActivity(intent)
        }

        builder.setNegativeButton(android.R.string.no) { dialog, which ->
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        builder.show()
    }
    fun getid(jobname: String,state : String){

        val db = FirebaseFirestore.getInstance()
        db.collection("jobapplication")
            .get()
            .addOnSuccessListener { result ->
                for (doc in result) {
                    if (doc.getString("jobinfo").toString() == jobname) {
                        updatejob(doc.id,state)
                    }
                }

            }


    }
    fun updatejob(docid: String,state :String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("jobapplication").document(docid)
            .update("state", state)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully updated!")
                if(newsArrayList.isNullOrEmpty()) {
                    builddialag("Başvurular Bitti Geri Dönmek İstermisiniz ?")

                }}
            .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }
    }
}


