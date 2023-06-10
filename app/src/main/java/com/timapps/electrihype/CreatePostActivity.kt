package com.timapps.electrihype

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.timapps.electrihype.databinding.ActivityCreatePostBinding


class CreatePostActivity : AppCompatActivity() {

    private lateinit var textInputEditText: TextInputEditText
    private lateinit var user: String
    private val PICK_IMAGE_REQUEST = 1
    private lateinit var attachedImage: ImageView
    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityCreatePostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        attachedImage = findViewById(R.id.iv_attachedImage)

        val user_id = intent.getStringExtra("user_id")


        textInputEditText = findViewById(R.id.te_inputform_for_post)
        val attachmentButton: Button = findViewById(R.id.btn_for_attachemnt)

        val tvUsername = findViewById<TextView>(R.id.tv_user_name)

        val email_id = intent.getStringExtra("email_id")
        if (email_id != null) {

            tvUsername.text=(
                    if (email_id.contains("@"))
                "@"+email_id.substringBefore("@")
            else email_id
                    )
            user = tvUsername.text as String

        }



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


        // Set a click listener for the attachment button
        attachmentButton.setOnClickListener {
            // Create an intent to pick an image from the gallery
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
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

                val enteredText = textInputEditText.text?.toString()

                if (!enteredText.isNullOrEmpty()) {
                    val newPost = FeedPostDataModel(0, enteredText, selectedImageUri, user)

                    Toast.makeText(this, "$user Post created successfully", Toast.LENGTH_SHORT).show()

                    val intent = Intent()
                    intent.putExtra("newPost", newPost) // Send the newPost object as an extra
                    setResult(Activity.RESULT_OK, intent)

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

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST &&
            resultCode == Activity.RESULT_OK && data != null) {
            // Get the selected image URI
            selectedImageUri = data.data

            // Set the selected image URI to the ImageView
            attachedImage.setImageURI(selectedImageUri)
        }
    }



}