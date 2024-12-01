package com.example.edtech

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.razorpay.Checkout

class adaptercourse(
var context: Context,
var songs: ArrayList<datacour>,
    var userid:String
) : RecyclerView.Adapter<adaptercourse.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tie: TextView = itemView.findViewById(R.id.price)
        val im: ImageView = itemView.findViewById(R.id.cousreimage)
        val buy:Button=itemView.findViewById(R.id.buycourse)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adaptercourse.ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.courseblueprint, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val p: datacour= songs[position]
        holder.tie.text = "₹"+p.price
        Glide.with(holder.itemView.context)
            .load(p.image)
            .into(holder.im)
        holder.buy.setOnClickListener{
            Intent(context,paymentactivity::class.java).also {
                it.putExtra("price",p.price)
                it.putExtra("id",p.id)
                it.putExtra("UID",userid)
                context.startActivity(it)
            }
        }
    }

    override fun getItemCount(): Int {
        return songs.size
    }
}