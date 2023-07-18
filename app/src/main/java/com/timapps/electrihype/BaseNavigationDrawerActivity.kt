package com.timapps.electrihype

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.timapps.electrihype.R
import com.timapps.electrihype.databinding.ActivityBaseNavigationDrawerBinding
import com.timapps.electrihype.databinding.ActivityMainFeedBinding

abstract class BaseNavigationDrawerActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityBaseNavigationDrawerBinding.inflate(layoutInflater)
        setContentView(binding.root)



        drawerLayout = binding.drawerLayoutAll
        navigationView = binding.navigationViewAll


        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.baseline_flash_on_24)

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

    // Abstract methods to handle menu item clicks in child activities
    abstract fun onMenuItem1Click()
    abstract fun onMenuItem2Click()
}
