package com.example.example

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.storage.images.FirebaseImageLoader
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        findViewById<TextView>(R.id.register).setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        findViewById<Button>(R.id.login_button).setOnClickListener {
            val db = FirebaseFirestore.getInstance()
            findViewById<TextView>(R.id.register).isEnabled=false
            findViewById<Button>(R.id.login_button).isEnabled = false
            username.isEnabled = false
            password.isEnabled = false
            findViewById<Button>(R.id.login_button).setBackgroundColor(Color.GRAY)
            db.collection("user")
                .get()
                .addOnSuccessListener { result ->
                    for (doc in result) {
                     if (doc.id == username.text.toString())
                       {
                         if(doc.getString("pass") == password.text.toString())
                         {

                             if (doc.getString("title") == "usage")
                             {
                                 findViewById<Button>(R.id.login_button).isEnabled = true
                                 findViewById<TextView>(R.id.register).isEnabled=true
                                 username.isEnabled = false
                                 password.isEnabled = false
                                 val intent = Intent(this, MainActivity::class.java).putExtra("username",username.text.toString())
                                 startActivity(intent)

                             }
                             else
                             {
                                 findViewById<Button>(R.id.login_button).isEnabled = true
                                 findViewById<TextView>(R.id.register).isEnabled=true
                                 username.isEnabled = false
                                 password.isEnabled = false
                                 val intent = Intent(this, DecisionActivity::class.java).putExtra("username",username.text.toString())
                                 startActivity(intent)
                             }
                         }
                         else
                         {
                             findViewById<Button>(R.id.login_button).isEnabled = true
                             findViewById<TextView>(R.id.register).isEnabled=true
                             username.isEnabled = true
                             password.isEnabled = true
                             findViewById<Button>(R.id.login_button).setBackgroundColor(Color.BLUE)
                             Toast.makeText(this,"Username or Password is wrong!",Toast.LENGTH_SHORT).show()
                             username.setText("")
                             password.setText("")

                         }
                     }


                       }





                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, exception.toString(), exception)
                }

        }

    }
}
