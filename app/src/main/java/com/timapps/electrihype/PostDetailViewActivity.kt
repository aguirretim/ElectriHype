package com.timapps.electrihype

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView

class PostDetailViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_detail_view)


        // Retrieve the data passed from the previous activity
        val selectedItemText = intent.getStringExtra("selectedItemText")
        val selectedItemUsername = intent.getStringExtra("selectedItemUsername")
        val selectedItemLikes = intent.getIntExtra("selectedItemLikes", 0)
        val selectedItemImageId = intent.getIntExtra("selectedItemImageId", 0)

        // Find the views in the layout
        val postTextView: TextView = findViewById(R.id.tv_post_text)
        val authorTextView: TextView = findViewById(R.id.tv_post_author)
        val likeCountTextView: TextView = findViewById(R.id.tv_like_count)
        val imageView: ImageView = findViewById(R.id.iv_image_for_post)

        // Set the data in the views
        postTextView.text = selectedItemText
        authorTextView.text = selectedItemUsername
        likeCountTextView.text = selectedItemLikes.toString()

        if (selectedItemImageId != 0) {
            imageView.visibility = View.VISIBLE
            imageView.setImageResource(selectedItemImageId)
        } else {
            imageView.visibility = View.GONE
        }



    }
}