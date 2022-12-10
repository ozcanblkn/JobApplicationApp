package com.example.example

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.type.Color

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        var check : String
        val checkbox : CheckBox = findViewById(R.id.businesschecked)
        val username : EditText = findViewById(R.id.idEdtUserName)
        val pass : EditText = findViewById(R.id.idEdtPassword)

        findViewById<Button>(R.id.idBtnRegister).setOnClickListener {
            check = if (checkbox.isChecked) {
                "business"
            } else {
                "usage"
            }
            findViewById<Button>(R.id.idBtnRegister).isEnabled=false
            findViewById<Button>(R.id.idBtnRegister).setBackgroundColor(android.graphics.Color.GRAY)
            val db = FirebaseFirestore.getInstance()
            val businessdata = hashMapOf(
                "pass" to pass.text.toString(),
                "title" to check
            )
            val usagedata = hashMapOf(
                "pass" to pass.text.toString(),
                "title" to check
            )
            if (check == "business")
            {
                db.collection("user").document(username.text.toString())
                    .set(businessdata)
                    .addOnSuccessListener { documentReference ->
                        findViewById<Button>(R.id.idBtnRegister).isEnabled=false
                        findViewById<Button>(R.id.idBtnRegister).setBackgroundColor(android.graphics.Color.GRAY)
                        Log.d(TAG, "DocumentSnapshot written with ID: $documentReference")
                        Toast.makeText(this,"success",Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, DecisionActivity::class.java).putExtra("username",username.text.toString())
                        startActivity(intent)


                    }.addOnFailureListener { e ->
                        Log.w(TAG, "Error adding document", e)
                        Toast.makeText(this, e.message.toString(),Toast.LENGTH_SHORT).show()
                    }

            }
            else
            {

                db.collection("user").document(username.text.toString())
                    .set(usagedata)
                    .addOnSuccessListener { documentReference ->
                        findViewById<Button>(R.id.idBtnRegister).isEnabled=false
                        findViewById<Button>(R.id.idBtnRegister).setBackgroundColor(android.graphics.Color.GRAY)
                        Log.d(TAG, "DocumentSnapshot written with ID: $documentReference")
                        Toast.makeText(this,"success",Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java).putExtra("username",username.text.toString())
                        startActivity(intent)


                    }.addOnFailureListener { e ->
                        Log.w(TAG, "Error adding document", e)
                        Toast.makeText(this, e.message.toString(),Toast.LENGTH_SHORT).show()
                    }
            }


        }

    }
}