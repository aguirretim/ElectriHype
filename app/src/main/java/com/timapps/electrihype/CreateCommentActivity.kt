package com.timapps.electrihype

import CommentDataModel
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CreateCommentActivity : AppCompatActivity() {

    private lateinit var textInputEditText: TextInputEditText
    private lateinit var user: String
    private lateinit var postId: String
    private val db = FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_comment)

        // Retrieve the data passed from the previous activity
        postId = intent.getStringExtra("selectedItemID") ?: ""

        val email_id = FirebaseAuth.getInstance().currentUser?.email

        textInputEditText = findViewById(R.id.et_comment_text_edit)




        if (email_id != null) {
            if (email_id.contains("@"))
                user = "@" + email_id.substringBefore("@")
        }

        supportActionBar?.apply {
            // Enable custom view for the action bar
            displayOptions = androidx.appcompat.app.ActionBar.DISPLAY_SHOW_CUSTOM
            setCustomView(R.layout.action_bar_header)
            setDisplayHomeAsUpEnabled(true) // Optional: Add back button

            // Get reference to the TextView in the custom action bar layout
            val appHeaderTextView: TextView = customView.findViewById(R.id.tvAppHeader)

            appHeaderTextView.text = "Rehype"
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
        val menuItem: MenuItem = menu.findItem(R.id.actionButton1)
        menuItem.title = "Post Your Comment"
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                // Handle back button click here
                onBackPressed()
                return true
            }



            R.id.actionButton1 -> {
                // Handle button 1 click
                val enteredText = textInputEditText.text?.toString()


                if (!enteredText.isNullOrEmpty()) {

                    val newComment = CommentDataModel(postId,enteredText, user)
                    db.collection("comments")
                        .add(newComment)
                        .addOnSuccessListener { documentReference ->
                            // Handle success
                            val commentId = documentReference.id
                            Toast.makeText(this, "Comment added with ID: $commentId", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener { e ->
                            // Handle error
                            Toast.makeText(this, "Failed to add comment: ${e.message}", Toast.LENGTH_SHORT).show()
                        }

                    val intent = Intent()
                    intent.putExtra("newComment", newComment) // Send the newComment object as an extra
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
}
