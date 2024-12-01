package com.example.edtech

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class MainActivity : AppCompatActivity() {
    private lateinit var userid: String
    private lateinit var nav: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        nav = findViewById(R.id.`as`)
        userid = intent.getStringExtra("UID").toString()
        val db = FirebaseFirestore.getInstance()
        val docRef = db.collection(userid).document("mycourse")
        docRef.get().addOnSuccessListener { document ->
            if (document.exists()) {
                // Document exists, do nothing or handle updates here if needed
                Log.d("Firestore", "Document for user $userid already exists.")
            } else {
                // Document doesn't exist, create with initial data
                val initialData = hashMapOf("courseid" to ArrayList<String>())
                docRef.set(initialData)
                    .addOnSuccessListener {
                        Log.d("Firestore", "Collection for user $userid created with initial data.")
                    }
                    .addOnFailureListener { exception ->
                        Log.e(
                            "Firestore",
                            "Error creating collection for user $userid: ${exception.message}"
                        )
                    }
            }
        }.addOnFailureListener { exception ->
            Log.e(
                "Firestore",
                "Error checking if document exists for user $userid: ${exception.message}"
            )
        }
       make(fragmenthome())
        nav.setOnItemSelectedListener{item: MenuItem ->
            when(item.itemId){
                R.id.homepage->{
                    make(fragmenthome())
                    true
                }
                R.id.series->{
                    make(fragmenttest())
                    true
                }
                R.id.mycourse->{
                    make(fragmentmycourse())
                    true
                }

                else -> {
                    make(fragementsettings())
                    true
                }
            }

        }
    }
    fun make(Fragment: Fragment){
//        if (userid !="") {
            val bundle = Bundle()
            bundle.putString("UID", userid)
            Fragment.arguments = bundle
//        }
        val home:FragmentManager =supportFragmentManager
        val tran:FragmentTransaction=home.beginTransaction()
        tran.replace(R.id.front,Fragment)
        tran.commit()
    }
}