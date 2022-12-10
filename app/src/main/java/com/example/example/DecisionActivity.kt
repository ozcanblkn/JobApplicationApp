package com.example.example

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class DecisionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_decision)
        val username:String = intent.getStringExtra("username").toString()
        findViewById<Button>(R.id.createjob_button).setOnClickListener(){
            val intent = Intent(this, CreatejobActivity::class.java).putExtra("username",username)
            startActivity(intent)
        }
        findViewById<Button>(R.id.applications_btn).setOnClickListener(){
            val intent = Intent(this, ProfileActivity::class.java).putExtra("username",username)
            startActivity(intent)
        }
    }
}