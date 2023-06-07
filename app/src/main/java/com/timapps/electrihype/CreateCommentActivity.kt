package com.timapps.electrihype

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView

class CreateCommentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_comment)






        supportActionBar?.apply {
            // Enable custom view for the action bar
            displayOptions = androidx.appcompat.app.ActionBar.DISPLAY_SHOW_CUSTOM
            setCustomView(R.layout.action_bar_header)
            setDisplayHomeAsUpEnabled(true) // Optional: Add back button

            // Get reference to the TextView in the custom action bar layout
            val appHeaderTextView: TextView = customView.findViewById(R.id.tv_app_header)

            appHeaderTextView.setText("Rehype")
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
        val menuItem: MenuItem = menu.findItem(R.id.action_button1)
        menuItem.setTitle("Post Your Comment")
        return true
    }

}