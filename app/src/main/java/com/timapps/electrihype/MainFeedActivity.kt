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

import com.timapps.electrihype.databinding.ActivityMainFeedBinding
class MainFeedActivity : AppCompatActivity() {
    private lateinit var adapter: FeedPostAdapter

    companion object {
        private const val REQUEST_CREATE_POST = 1
        private val PICK_IMAGE_REQUEST = 1
    }



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val binding = ActivityMainFeedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user_id = FirebaseAuth.getInstance().currentUser!!.uid
        val email_id = FirebaseAuth.getInstance().currentUser?.email

        val rv_main_Feed: RecyclerView = binding.rvMainFeed
        val fab_create_post: FloatingActionButton = binding.fabCreatePost

        val data = ArrayList<FeedPostDataModel>()

        fun getDrawableUri(context: Context, drawableResId: Int): Uri? {
            val drawableUriString = "android.resource://${context.packageName}/$drawableResId"
            return Uri.parse(drawableUriString)
        }

        val drawableResId = R.drawable.pokehype
        val drawableUri = getDrawableUri(this, drawableResId)


        // Generate sample data
        val content1 = FeedPostDataModel(122, "Why is it everytime I wake up and go to the club. There is always some drama before I go", drawableUri, "@lilpikatest")
        val content2 = FeedPostDataModel(75, "I'm just a player, I'm not a rapper. But when it comes to gaming, I'm a master", null, "@gamerpro")
        val content3 = FeedPostDataModel(51, "In the world of Apex Legends, I'm the champion. No one can defeat me, I reign", null, "@apexking")

        // Add sample data to the list
        data.add(content2)
        data.add(content1)
        data.add(content3)

        adapter = FeedPostAdapter(data)
        rv_main_Feed.adapter = adapter

        // Create a LinearLayoutManager
        val layoutManager = LinearLayoutManager(this)

        // Set the layout manager to the RecyclerView
        rv_main_Feed.layoutManager = layoutManager

        fab_create_post.setOnClickListener{
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
                // Handle the new post object as needed
                // For example, you can update the UI or perform any necessary operations
                // Add the new post to the data list
                adapter.addData(newPost)
                adapter.notifyDataSetChanged()
                Toast.makeText(this, "Magical data added", Toast.LENGTH_SHORT).show()

            }
        }
    }





}