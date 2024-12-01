package com.example.edtech

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class adaptermycourse(
    var context: Context,
    var songs: ArrayList<datacour>
) : RecyclerView.Adapter<adaptermycourse.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tie: TextView = itemView.findViewById(R.id.mycoursename)
        val im: ImageButton = itemView.findViewById(R.id.mycourseimage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adaptermycourse.ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.blueprintmycourse, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val p: datacour= songs[position]
        holder.tie.text = p.name
        Glide.with(holder.itemView.context)
            .load(p.image)
            // Optional: add a placeholder while the image is loading
//            .error(R.drawable.p4)              // Optional: add an error image in case loading fails
            .into(holder.im)

    }

    override fun getItemCount(): Int {
//        Log.d("ArraySize", "Size of songs: ${songs.size}")
        return songs.size
    }
}