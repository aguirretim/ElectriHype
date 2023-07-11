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
import java.util.UUID

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

        attachedImage = binding.ivAttachedImage

        val user_id = intent.getStringExtra("user_id")

        textInputEditText = binding.teInputformForPost
        val attachmentButton: Button = binding.btnForAttachemnt
        val tvUsername: TextView = binding.tvUserName

        val email_id = intent.getStringExtra("email_id")
        if (email_id != null) {
            tvUsername.text = if (email_id.contains("@")) {
                "@" + email_id.substringBefore("@")
            } else {
                email_id
            }
            user = tvUsername.text.toString()
        }

        supportActionBar?.apply {
            displayOptions = androidx.appcompat.app.ActionBar.DISPLAY_SHOW_CUSTOM
            setCustomView(R.layout.action_bar_header)
            setDisplayHomeAsUpEnabled(true)
            val appHeaderTextView: TextView = customView.findViewById(R.id.tvAppHeader)
            appHeaderTextView.setOnClickListener {
                // Handle click event
            }
        }

        attachmentButton.setOnClickListener {
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
                onBackPressed()
                return true
            }
            R.id.actionButton1 -> {
                val enteredText = textInputEditText.text?.toString()
                if (!enteredText.isNullOrEmpty()) {
                    val generatedId = UUID.randomUUID().toString()
                    val newPost = FeedPostDataModel(generatedId, 0, enteredText, selectedImageUri, user)
                    Toast.makeText(this, "$user Post created successfully", Toast.LENGTH_SHORT).show()
                    val intent = Intent()
                    intent.putExtra("newPost", newPost)
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                } else {
                    Toast.makeText(this, "Please enter some text", Toast.LENGTH_SHORT).show()
                }
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageUri = data.data
            attachedImage.setImageURI(selectedImageUri)
        }
    }
}
