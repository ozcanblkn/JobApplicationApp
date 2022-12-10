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

class JobInfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_info)
        val string: String? = intent.getStringExtra("info")
        val string2: String? = intent.getStringExtra("infotext")
        val username: String? = intent.getStringExtra("username")
        val business: String? = intent.getStringExtra("business")
        val textView: TextView = findViewById(R.id.info)
        val textView2: TextView = findViewById(R.id.infotext)
        textView.text = string
        textView2.text = string2
        findViewById<Button>(R.id.apply_button).setOnClickListener {
            findViewById<Button>(R.id.apply_button).isEnabled=false
            findViewById<Button>(R.id.apply_button).setBackgroundColor(Color.GRAY)
            val db = FirebaseFirestore.getInstance()
            val data = hashMapOf(
                "jobinfo" to string.toString(),
                "business" to business.toString(),
                "username" to username.toString(),
                "state" to "waiting"
            )
            db.collection("jobapplication")
                .add(data)
                .addOnSuccessListener { documentReference ->
                    Log.d(ContentValues.TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
                    findViewById<Button>(R.id.apply_button).isEnabled=true
                    findViewById<Button>(R.id.login_button).setBackgroundColor(Color.BLUE)
                    Toast.makeText(this,"Application Successful",Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java).putExtra("username",username.toString())
                    startActivity(intent)
                }
                .addOnFailureListener { e ->
                    Log.w(ContentValues.TAG, "Error adding document", e)
                    Toast.makeText(this, e.message.toString(),Toast.LENGTH_SHORT).show()
                }
        }



    }
}