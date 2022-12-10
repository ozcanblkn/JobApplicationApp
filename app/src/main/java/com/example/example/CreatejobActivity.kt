package com.example.example

import android.content.ContentValues
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

class CreatejobActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_createjob)
        val username:String = intent.getStringExtra("username").toString()
        val jobinfo: TextView = findViewById(R.id.jobinfo)
        val jobname: TextView = findViewById(R.id.jobname)
        findViewById<Button>(R.id.createjobbtn).setOnClickListener(){
            findViewById<Button>(R.id.createjobbtn).setBackgroundColor(android.graphics.Color.GRAY)
            findViewById<Button>(R.id.createjobbtn).isEnabled=false
            val db = FirebaseFirestore.getInstance()
            val data = hashMapOf(
                "business" to username,
                "info" to jobinfo.text.toString(),
                "imgurl" to "https://firebasestorage.googleapis.com/v0/b/exampledb-5569a.appspot.com/o/yapikredi.png?alt=media&token=378eab8a-ceda-49aa-81ee-38456cf834f6"
            )
            db.collection("content").document(jobname.text.toString())
                .set(data)
                .addOnSuccessListener { documentReference ->
                    findViewById<Button>(R.id.createjobbtn).isEnabled=true
                    findViewById<Button>(R.id.createjobbtn).setBackgroundColor(android.graphics.Color.BLUE)
                    Log.d(ContentValues.TAG, "DocumentSnapshot written with ID: $documentReference")
                    Toast.makeText(this,"success",Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, DecisionActivity::class.java).putExtra("username",username)
                    startActivity(intent)


                }.addOnFailureListener { e ->
                    Log.w(ContentValues.TAG, "Error adding document", e)
                    Toast.makeText(this, e.message.toString(),Toast.LENGTH_SHORT).show()
                }
        }

    }
}