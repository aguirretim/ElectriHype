package com.timapps.electrihype
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import java.net.URI
import com.google.firebase.firestore.FirebaseFirestore

import com.timapps.electrihype.databinding.ActivityMainFeedBinding
import java.util.UUID

class MainFeedActivity : AppCompatActivity() {
    private lateinit var adapter: FeedPostAdapter

    companion object {
        private const val REQUEST_CREATE_POST = 1
        private val PICK_IMAGE_REQUEST = 1
    }

    // Firebase Firestore instance
    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val binding = ActivityMainFeedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get current user information
        val user_id = FirebaseAuth.getInstance().currentUser!!.uid
        val email_id = FirebaseAuth.getInstance().currentUser?.email

        // Initialize RecyclerView and FloatingActionButton
        val rv_main_Feed: RecyclerView = binding.rvMainFeed
        val fab_create_post: FloatingActionButton = binding.fabCreatePost

        // Initialize data list
        val data = ArrayList<FeedPostDataModel>()

        // Utility function to get Uri from drawable resource ID
        fun getDrawableUri(context: Context, drawableResId: Int): Uri? {
            val drawableUriString = "android.resource://${context.packageName}/$drawableResId"
            return Uri.parse(drawableUriString)
        }

        // Example drawable resource ID and Uri
        val drawableResId = R.drawable.pokehype
        val drawableUri = getDrawableUri(this, drawableResId)

        val genratedId = UUID.randomUUID().toString()

        // Generate sample data
        val content1 = FeedPostDataModel(genratedId,122, "Why is it everytime I wake up and go to the club. There is always some drama before I go", drawableUri, "@lilpikatest")
        val content2 = FeedPostDataModel(genratedId,75, "I'm just a player, I'm not a rapper. But when it comes to gaming, I'm a master", null, "@gamerpro")
        val content3 = FeedPostDataModel(genratedId,51, "In the world of Apex Legends, I'm the champion. No one can defeat me, I reign", null, "@apexking")

        // Add sample data to the list
        data.add(content2)
        data.add(content1)
        data.add(content3)

        // Initialize adapter with empty list
        adapter = FeedPostAdapter(ArrayList())
        rv_main_Feed.adapter = adapter

        // Call the function to fetch posts from Firestore
        fetchPostsFromFirestore()

        // Create a LinearLayoutManager
        val layoutManager = LinearLayoutManager(this)

        // Set the layout manager to the RecyclerView
        rv_main_Feed.layoutManager = layoutManager

        fab_create_post.setOnClickListener{
            // Start CreatePostActivity for result
            intent = Intent(this@MainFeedActivity, CreatePostActivity::class.java)
            intent.putExtra("user_id", user_id)
            intent.putExtra("email_id", email_id)
            startActivityForResult(intent, REQUEST_CREATE_POST)
        }
    }


    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CREATE_POST && resultCode == Activity.RESULT_OK) {
            val newPost = data?.getParcelableExtra<FeedPostDataModel>("newPost")
            if (newPost != null) {
                // Add the new post to Firestore
                db.collection("posts")
                    .add(newPost)
                    .addOnSuccessListener { documentReference ->
                        // Handle success
                        val postId = documentReference.id
                        Toast.makeText(this, "Post added with ID: $postId", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { e ->
                        // Handle error
                        Toast.makeText(this, "Failed to add post: ${e.message}", Toast.LENGTH_SHORT).show()
                    }

                // Add the new post to the adapter
                adapter.addData(newPost)
                adapter.notifyDataSetChanged()
                Toast.makeText(this, "Magical data added", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fetchPostsFromFirestore() {
        db.collection("posts")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val posts = ArrayList<FeedPostDataModel>()
                for (document in querySnapshot.documents) {
                    val post = document.toObject(FeedPostDataModel::class.java)
                    post?.let {
                        posts.add(it)
                    }
                }
                adapter.setData(posts)
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to fetch posts: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

}