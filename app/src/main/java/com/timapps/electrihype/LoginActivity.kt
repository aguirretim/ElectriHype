package com.timapps.electrihype

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import com.timapps.electrihype.databinding.ActivityMainBinding
import com.timapps.electrihype.databinding.ActivityRegisterBinding
import com.timapps.electrihype.databinding.LoginActivityBinding

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val binding = LoginActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btn_sign_up: Button = binding.btnSignUp

        btn_sign_up.setOnClickListener{
             startActivity(Intent(this@LoginActivity,RegisterActivity::class.java))
             finish()
        }
    }
}