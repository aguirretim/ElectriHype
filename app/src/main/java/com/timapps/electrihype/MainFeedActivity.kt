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
import com.google.firebase.firestore.FirebaseFirestore
import com.timapps.electrihype.databinding.ActivityMainFeedBinding
import java.util.UUID
import com.google.firebase.storage.FirebaseStorage


class MainFeedActivity : AppCompatActivity() {
    private lateinit var adapter: FeedPostAdapter

    companion object {
        private const val REQUEST_CREATE_POST = 1
        private const val PICK_IMAGE_REQUEST = 1
    }

    private val db = FirebaseFirestore.getInstance()
    val storage = FirebaseStorage.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainFeedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user_id = FirebaseAuth.getInstance().currentUser?.uid
        val email_id = FirebaseAuth.getInstance().currentUser?.email

        val rvMainFeed: RecyclerView = binding.rvMainFeed
        val fabCreatePost: FloatingActionButton = binding.fabCreatePost

        val data = ArrayList<FeedPostDataModel>()

        fun getDrawableUri(context: Context, drawableResId: Int): Uri? {
            val drawableUriString = "android.resource://${context.packageName}/$drawableResId"
            return Uri.parse(drawableUriString)
        }

        val drawableResId = R.drawable.pokehype
        val drawableUri = getDrawableUri(this, drawableResId)

        val generatedId = UUID.randomUUID().toString()

       // val content1 = FeedPostDataModel(generatedId, 122, "Why is it everytime I wake up and go to the club. There is always some drama before I go", drawableUri, "@lilpikatest")
        val content2 = FeedPostDataModel(generatedId, 75, "I'm just a player, I'm not a rapper. But when it comes to gaming, I'm a master", null, "@gamerpro")
        val content3 = FeedPostDataModel(generatedId, 51, "In the world of Apex Legends, I'm the champion. No one can defeat me, I reign", null, "@apexking")

        data.add(content2)
        //data.add(content1)
        data.add(content3)

        adapter = FeedPostAdapter(ArrayList())
        rvMainFeed.adapter = adapter

        fetchPostsFromFirestore()

        val layoutManager = LinearLayoutManager(this)
        rvMainFeed.layoutManager = layoutManager

        fabCreatePost.setOnClickListener {
            intent = Intent(this@MainFeedActivity, CreatePostActivity::class.java)
            intent.putExtra("user_id", user_id)
            intent.putExtra("email_id", email_id)
            startActivityForResult(intent, REQUEST_CREATE_POST)
        }
    }

    // Handle the result of the create post activity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CREATE_POST && resultCode == Activity.RESULT_OK) {
            val newPost = data?.getParcelableExtra<FeedPostDataModel>("newPost")
            if (newPost != null) {
                val selectedImageUri = Uri.parse(newPost.imageResId.toString())

                // Check if an image was selected
                if (selectedImageUri != null) {
                    // Create a child reference in Firebase Storage with a unique filename
                    val storageRef = storage.reference.child("${System.currentTimeMillis()}.jpg")

                    // Upload the selected image to Firebase Storage
                    storageRef.putFile(selectedImageUri).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // If upload is successful, get the download URL of the uploaded image
                            storageRef.downloadUrl.addOnSuccessListener { uri ->
                                newPost.imageResId = uri // Assign the download URL to imageResId
                                addPostToFirestore(newPost) // Add the post to Firestore
                            }.addOnFailureListener { e ->
                                Toast.makeText(this, "Failed to get download URL: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(this, "Failed to upload image: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    addPostToFirestore(newPost) // Add the post to Firestore without an image
                }
            }
        }
    }


    private fun fetchPostsFromFirestore() {
        db.collection("posts")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val posts = ArrayList<FeedPostDataModel>()
                for (document in querySnapshot.documents) {
                    val id = document.id
                    val numberOfLikes = document.getLong("numberOfLikes") ?: 0
                    val mainContentText = document.getString("mainContentText") ?: ""
                    val username = document.getString("username") ?: ""
                    val date = document.getDate("date")
                    val imageResId = document.getString("imageResId")

                    // Create a FeedPostDataModel object with the retrieved data
                    val post = date?.let {
                        FeedPostDataModel(
                            id,
                            numberOfLikes.toInt(),
                            mainContentText,
                            imageResId?.toUri(),
                            username,
                            it
                        )
                    }
                    if (post != null) {
                        posts.add(post)
                    }
                }
                adapter.setData(posts)
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to fetch posts: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }



    fun ToUri(stringUri: String): Uri {
        return Uri.parse(stringUri)
    }

    private fun addPostToFirestore(newPost: FeedPostDataModel) {
        db.collection("posts")
            .add(newPost)
            .addOnSuccessListener { documentReference ->
                val postId = documentReference.id
                Toast.makeText(this, "Post added with ID: $postId", Toast.LENGTH_SHORT).show()
                fetchPostsFromFirestore() // Fetch the updated posts from Firestore
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to add post: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    
    
}


