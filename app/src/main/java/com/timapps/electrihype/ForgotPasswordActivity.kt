package com.timapps.electrihype

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.timapps.electrihype.databinding.ActivityForgotPasswordBinding
import com.timapps.electrihype.databinding.LoginActivityBinding

class ForgotPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tv_login: TextView = binding.tvLoginHere
        val btn_submit: Button = binding.btnResetSubmit
        val et_login_email: EditText = binding.editTextTextEmailAddress

        btn_submit.setOnClickListener {
            when {

                TextUtils.isEmpty(et_login_email.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@ForgotPasswordActivity,
                        "please enter email",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else -> {
                    val email: String = et_login_email.text.toString().trim() { it <= ' ' }

                    //Log-In using firebase
                    FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                        .addOnCompleteListener { task ->
                            // If the email account is successfull searched
                            if (task.isSuccessful) {

                                Toast.makeText(
                                    this@ForgotPasswordActivity,
                                    "Email sent successfully to reset your password!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                finish()
                            } else {
                                Toast.makeText(
                                    this@ForgotPasswordActivity,
                                    task.exception!!.message.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }


                }
            }
        }






        tv_login.setOnClickListener {
            // Handle click event for tvLogin
            startActivity(Intent(this@ForgotPasswordActivity,LoginActivity::class.java))
            finish()

        }
    }




}