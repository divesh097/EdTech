package com.example.edtech

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

class firestore(user:String) {
    val firestor = FirebaseFirestore.getInstance()
    val uss = firestor.collection(user)
    fun gethome(callback: (ArrayList<datacour>)->Unit){
        val d = firestor.collection("courses")
var course=ArrayList<datacour>()
        course= ArrayList()
        d.document("size").get().addOnSuccessListener {document->
            var size= document.data?.get("size").toString().toInt()

            for( i in 1..size){
                d.document("$i").get().addOnSuccessListener {coursedoc->

                    val dataa = coursedoc.toObject(datacour::class.java)

                    if (dataa != null) {

                        course.add(dataa)

                    }
                    if(course.size==size){
                        callback(course)
                    }


                }


            }
        }
//        Log.d("ArraySize", "Size of course: ${course.size}"
    }
    fun getmycourse(callback: (ArrayList<datacour>)->Unit){
        val d = firestor.collection("courses")
        var course=ArrayList<datacour>()
        course= ArrayList()
        uss.document("mycourse").get().addOnSuccessListener {document->
            var courseids=ArrayList<String>()
            courseids=ArrayList()
            courseids= document.get("courseid") as ArrayList<String>
            for( i in courseids) {
                d.document("$i").get().addOnSuccessListener { coursedoc ->

                    val dataa = coursedoc.toObject(datacour::class.java)

                    if (dataa != null) {

                        course.add(dataa)

                    }
                    if (course.size == courseids.size) {
                        callback(course)
                    }
                }
            }
            }
        }
}