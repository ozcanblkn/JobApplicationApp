package com.example.example

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.type.Color

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Settings.newInstance] factory method to
 * create an instance of this fragment.
 */
class Settings : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var cv: EditText
    private lateinit var pass: EditText
    private lateinit var up_button: Button
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
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cv = view.findViewById(R.id.up_cv)
        pass = view.findViewById(R.id.up_pass)
        val username : String = arguments?.getString("username").toString()
        up_button = view.findViewById(R.id.up_button)
        up_button.setOnClickListener(){
            updateprofile(username)
        }


    }
    private fun updateprofile(username: String) {
        up_button.setBackgroundColor(android.graphics.Color.GRAY)
        up_button.isEnabled=false
        val db = FirebaseFirestore.getInstance()
        db.collection("user").document(username)
            .update("cv", this.cv.text.toString(),"pass",this.pass.text.toString())
            .addOnSuccessListener { Log.d(ContentValues.TAG, "DocumentSnapshot successfully updated!")
            Toast.makeText(activity,"Update Successfully",Toast.LENGTH_SHORT).show()
                this.cv.text = null
                this.pass.text = null
                this.up_button.setBackgroundColor(android.graphics.Color.BLUE)
                this.up_button.isEnabled=false
            }
            .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error updating document", e) }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Settings.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Settings().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}