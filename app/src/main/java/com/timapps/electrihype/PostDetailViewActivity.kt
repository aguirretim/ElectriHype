package com.timapps.electrihype

import CommentDataModel
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PostDetailViewActivity : AppCompatActivity() {

    private lateinit var adapter: CommentAdapter
    private lateinit var postId: String

    companion object {
        private const val REQUEST_CREATE_COMMENT = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_detail_view)

        // Retrieve the data passed from the previous activity
        postId = intent.getStringExtra("selectedItemID") ?: ""

        // Retrieve the data passed from the previous activity
        val selectedItemText = intent.getStringExtra("selectedItemText")
        val selectedItemDate = intent.getSerializableExtra("selectedItemDate") as Date?
        val selectedItemUsername = intent.getStringExtra("selectedItemUsername")
        val selectedItemLikes = intent.getIntExtra("selectedItemLikes", 0)
        val selectedItemImageId = intent.getIntExtra("selectedItemImageId", 0)
        val fabCreateComment: FloatingActionButton = findViewById(R.id.fab_create_comment)
        // Retrieve the image URI from the extras
        val selectedItemImageUriString = intent.getStringExtra("selectedItemImageUri")
        val selectedItemImageUri = selectedItemImageUriString?.toUri()

        // Find the views in the layout
        val postTextView: TextView = findViewById(R.id.tv_post_text)
        val authorTextView: TextView = findViewById(R.id.tv_post_author)
        val likeCountTextView: TextView = findViewById(R.id.tv_like_count)
        val dateTextView: TextView = findViewById(R.id.tv_dateDetailview)
        val imageView: ImageView = findViewById(R.id.iv_image_for_post)
        val commentRecyclerView: RecyclerView = findViewById(R.id.rv_comment_replies)

        // Set the data in the views
        postTextView.text = selectedItemText
        authorTextView.text = selectedItemUsername
        likeCountTextView.text = selectedItemLikes.toString()

        if (selectedItemDate != null) {
            dateTextView.text = formatDate(selectedItemDate).toString()
        } else {
            // Handle the case when the date is null
            dateTextView.text = "Invalid Date"
        }

        if (selectedItemImageUri != null) {
            // Display the image using the URI
            imageView.visibility = View.VISIBLE
            // Load the image using Glide
            Glide.with(this)
                .load(selectedItemImageUri)
                .into(imageView)



            //imageView.setImageURI(selectedItemImageUri)
        } else {
            imageView.visibility = View.GONE
        }

        // Create a sample comment list (replace with your actual data)
        val commentList = mutableListOf<CommentDataModel>(
             )

        // Create the comment adapter and set it to the RecyclerView
        adapter = CommentAdapter(commentList as MutableList<CommentDataModel>)
        commentRecyclerView.adapter = adapter

        // Set the layout manager for the RecyclerView
        commentRecyclerView.layoutManager = LinearLayoutManager(this)

        fabCreateComment.setOnClickListener {
            // Handle the click event
            val intent = Intent(this, CreateCommentActivity::class.java)

            intent.putExtra("selectedItemID", postId)

            startActivityForResult(intent, REQUEST_CREATE_COMMENT)

        }

        // Fetch comments from Firestore and update the adapter
        fetchCommentsFromFirestore()

    }


    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CREATE_COMMENT && resultCode == Activity.RESULT_OK) {
            val newComment = data?.getParcelableExtra<CommentDataModel>("newComment")
            if (newComment != null) {
                // Handle the new comment object as needed
                // For example, you can update the UI or perform any necessary operations
                // Add the new comment to the data list
                adapter.addData(newComment)
                adapter.notifyDataSetChanged()
                Toast.makeText(this,
                    "Comment created successfully", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun fetchCommentsFromFirestore() {
        val commentsRef = FirebaseFirestore.getInstance().collection("comments")
        val query = commentsRef.whereEqualTo("id", postId)

        query.get()
            .addOnSuccessListener { querySnapshot ->
                val comments = mutableListOf<CommentDataModel>()
                for (document in querySnapshot.documents) {
                    val comment = document.toObject(CommentDataModel::class.java)
                    comment?.let {
                        comments.add(it)
                    }
                }
                adapter.setComments(comments)
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to fetch comments: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
    private fun formatDate(date: Date?): String {
        if (date == null) return "" // Handle null date case
        val dateFormat = SimpleDateFormat("MMMM d h:mm a", Locale.getDefault())
        return dateFormat.format(date)
    }

}