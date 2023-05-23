package com.timapps.electrihype

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.timapps.electrihype.databinding.ActivityMainBinding
import com.timapps.electrihype.databinding.LoginActivityBinding


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val binding = ActivityMainBinding.inflate(layoutInflater)

        val tv_user_id: TextView = binding.tvUserId
        val tv_email_id: TextView = binding.tvEmailId
        val btn_logout: Button = binding.btnLogout


        super.onCreate(savedInstanceState)
        setContentView(binding.root)

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



    }
}