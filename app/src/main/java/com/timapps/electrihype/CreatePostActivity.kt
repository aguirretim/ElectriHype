package com.timapps.electrihype

import android.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.timapps.electrihype.databinding.ActivityCreatePostBinding


class CreatePostActivity : AppCompatActivity() {

    private lateinit var textInputEditText: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityCreatePostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        textInputEditText = findViewById(R.id.te_inputform_for_post)


        supportActionBar?.apply {
            // Enable custom view for the action bar
            displayOptions = androidx.appcompat.app.ActionBar.DISPLAY_SHOW_CUSTOM
            setCustomView(R.layout.action_bar_header)
            setDisplayHomeAsUpEnabled(true) // Optional: Add back button

            // Get reference to the TextView in the custom action bar layout
            val appHeaderTextView: TextView = customView.findViewById(R.id.tv_app_header)

            // Customize the TextView as needed
            // appHeaderTextView.setTextColor(...)
            // appHeaderTextView.setTextSize(...)
            // ...

            // Optional: Handle click events on the action bar header
            appHeaderTextView.setOnClickListener {
                // Handle click event
            }

        }


    }

   override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.header_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                // Handle back button click here
                onBackPressed()
                return true
            }

            R.id.action_button1 -> {
                // Handle button 1 click

                val enteredText = textInputEditText .text?.toString()
                if (!enteredText.isNullOrEmpty()) {
                    val newPost = FeedPostDataModel(0, enteredText, 0, "@yourusername")

                    Toast.makeText(this, "Post created successfully", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Please enter some text", Toast.LENGTH_SHORT).show()
                }
                return true
            }

            // Handle other menu items if needed

            else -> return super.onOptionsItemSelected(item)
        }
    }
}