package com.timapps.electrihype

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.timapps.electrihype.databinding.ActivityMainBinding
import com.timapps.electrihype.databinding.LoginActivityBinding
import com.timapps.electrihype.databinding.ActivityBaseNavigationDrawerBinding

class MainActivity : BaseNavigationDrawerActivity()   {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        val binding = ActivityMainBinding.inflate(layoutInflater)

        val tv_user_id: TextView = binding.tvUserId
        val tv_email_id: TextView = binding.tvEmailId
        val btn_logout: Button = binding.btnLogout




        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navigationView)

        val user_id = intent.getStringExtra("user_id")
        val email_id = intent.getStringExtra("email_id")

        tv_user_id.text = "User ID : $user_id"
        tv_email_id.text = "Email : $email_id"


        btn_logout.setOnClickListener{
            // Logout from app
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this@MainActivity,LoginActivity::class.java))
            finish()
        }

        tv_user_id.setOnClickListener{
            startActivity(Intent(this@MainActivity,MainFeedActivity::class.java))
            finish()
        }

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.meenu_item1 -> {
                    // Handle menu item 1 click
                    onMenuItem1Click()
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.meenu_item2 -> {
                    // Handle menu item 2 click
                    onMenuItem2Click()
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                else -> false
            }
        }


    }


    override fun onMenuItem1Click() {
        // Handle menu item 1 click
        // Implement the desired behavior for this activity
        // Handle menu item 1 click
        val intent = Intent(this@MainActivity, MainFeedActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
      //  intent.putExtra("user_id", currentUser.uid)
       // intent.putExtra("email_id", currentUser.email)
        startActivity(intent)
        finish()
    }

     override fun onMenuItem2Click() {
        // Handle menu item 2 click
        // Implement the desired behavior for this activity
        Toast.makeText(this,
            "menu 2222222 clicked", Toast.LENGTH_SHORT).show()

        }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START)
                } else {
                    drawerLayout.openDrawer(GravityCompat.START)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }



}