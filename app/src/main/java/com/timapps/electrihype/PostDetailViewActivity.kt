package com.timapps.electrihype

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class PostDetailViewActivity : AppCompatActivity() {

    private lateinit var adapter: CommentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_detail_view)

        // Retrieve the data passed from the previous activity
        val selectedItemText = intent.getStringExtra("selectedItemText")
        val selectedItemUsername = intent.getStringExtra("selectedItemUsername")
        val selectedItemLikes = intent.getIntExtra("selectedItemLikes", 0)
        val selectedItemImageId = intent.getIntExtra("selectedItemImageId", 0)
        val fabCreateComment: FloatingActionButton = findViewById(R.id.fab_create_comment)
        // Find the views in the layout
        val postTextView: TextView = findViewById(R.id.tv_post_text)
        val authorTextView: TextView = findViewById(R.id.tv_post_author)
        val likeCountTextView: TextView = findViewById(R.id.tv_like_count)
        val imageView: ImageView = findViewById(R.id.iv_image_for_post)
        val commentRecyclerView: RecyclerView = findViewById(R.id.rv_comment_replies)

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

        // Create a sample comment list (replace with your actual data)
        val commentList = listOf(
            CommentDataModel("Oh yup i know what you mean da club be popin but itws always a struggle", "@PikaProTest"),
            CommentDataModel("Stop complaining at least you be getting to hit the club up.", "@PikaWilson"),
            CommentDataModel("You guys are all nerds you should know the club is for suckers", "@Jigglypuff23")
        )
        // Create the comment adapter and set it to the RecyclerView
        adapter = CommentAdapter(commentList as MutableList<CommentDataModel>)
        commentRecyclerView.adapter = adapter

        // Set the layout manager for the RecyclerView
        commentRecyclerView.layoutManager = LinearLayoutManager(this)


        fabCreateComment.setOnClickListener {
            // Handle the click event
            val intent = Intent(this, CreateCommentActivity::class.java)
            startActivity(intent)
        }


    }


}
