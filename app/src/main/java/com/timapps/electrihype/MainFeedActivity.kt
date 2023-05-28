package com.timapps.electrihype

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.controls.actions.FloatAction
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.timapps.electrihype.databinding.ActivityMainBinding
import com.timapps.electrihype.databinding.ActivityMainFeedBinding

class MainFeedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val binding = ActivityMainFeedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val rv_main_Feed: RecyclerView = binding.rvMainFeed
        val fab_create_post: FloatingActionButton = binding.fabCreatePost
        val data = ArrayList<FeedPostDataModel>()

        // Generate sample data
        val content1 = FeedPostDataModel(122, "Why is it everytime I wake up and go to the club. There is always some drama before I go", R.drawable.pokehype, "@lilpikatest")
        val content2 = FeedPostDataModel(75, "I'm just a player, I'm not a rapper. But when it comes to gaming, I'm a master", 0, "@gamerpro")
        val content3 = FeedPostDataModel(51, "In the world of Apex Legends, I'm the champion. No one can defeat me, I reign", 0, "@apexking")

        // Add sample data to the list

        data.add(content2)
        data.add(content1)
        data.add(content3)

        val adapter = FeedPostAdapter(data)
        rv_main_Feed.adapter = adapter

        // Create a LinearLayoutManager
        val layoutManager = LinearLayoutManager(this)

        // Set the layout manager to the RecyclerView
        rv_main_Feed.layoutManager = layoutManager

        fab_create_post.setOnClickListener{
            startActivity(Intent(this@MainFeedActivity, CreatePostActivity::class.java))
        }

    }
}