package com.example.edtech

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.razorpay.Checkout
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import org.json.JSONObject

class paymentactivity : AppCompatActivity() , PaymentResultWithDataListener {
    private lateinit var id:String
    private lateinit var userid:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paymentactivity)
        Checkout.preload(applicationContext)
        val co = Checkout()
        co.setKeyID("rzp_test_1MD2gY6A1enYhz")
        var price=intent.getStringExtra("price")
        if (price != null) {
            price=(price.toInt()*100).toString()
        }
        id=intent?.getStringExtra("id").toString()
        userid= intent?.getStringExtra("UID").toString()
        Log.d("id","${id}")
        price?.let { payment(it) }
    }
    private fun payment( price:String) {
        val activity: Activity = this
        val co = Checkout()

        try {
            val options = JSONObject()
            options.put("name","LearnShare")
            options.put("description","Course")
            //You can omit the image option to fetch the image from the dashboard
            options.put("image","http://example.com/image/rzp.jpg")
            options.put("theme.color", "#3399cc");
            options.put("currency","INR");
            options.put("amount",price)//pass amount in currency subunits

            val retryObj = JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            val prefill = JSONObject()
            prefill.put("email","gaurav.kumar@gmail.com")
            prefill.put("contact","9876543210")

            options.put("prefill",prefill)
            co.open(activity,options)
        }catch (e: Exception){
            Toast.makeText(activity,"Error in payment: "+ e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {
        Toast.makeText(this,"Payment Successful", Toast.LENGTH_SHORT).show()
        val db = FirebaseFirestore.getInstance()
        val docRef = db.collection(userid).document("mycourse")
        docRef.get().addOnSuccessListener { document ->
            if (document.exists()) {
                val existingCourses = document.get("courseid") as? List<String> ?: emptyList()
                val updatedCourses = existingCourses.toMutableList()
                if (!updatedCourses.contains(id)) {
                    updatedCourses.add(id)
                }
                val updatedData = hashMapOf("courseid" to updatedCourses)
                docRef.set(updatedData, SetOptions.merge())
            } else {
                val initialData = hashMapOf("courseid" to listOf(id))
                docRef.set(initialData)
            }
        }.addOnFailureListener { e ->
            e.printStackTrace()
        }

        finish()
    }


    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {
        Toast.makeText(this,"Payment Failed${p1}", Toast.LENGTH_SHORT).show()
        finish()
    }


}