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
import com.google.firebase.auth.FirebaseUser
import com.timapps.electrihype.databinding.ActivityRegisterBinding
import com.timapps.electrihype.databinding.LoginActivityBinding

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val textView2: TextView = binding.textView2
        val tilRegisterEmail: EditText = binding.tilRegisterEmail
        val tilRegisterPassword: EditText = binding.tilRegisterPassword
        val btnRegister: Button = binding.btnRegister
        val tvLogin: TextView = binding.tvLogin



        tilRegisterEmail.setOnClickListener {
            // Handle click event for tilRegisterEmail
        }

        tilRegisterPassword.setOnClickListener {
            // Handle click event for tilRegisterPassword
        }

        btnRegister.setOnClickListener {
            // Handle click event for btnRegister
            when{

                TextUtils.isEmpty(tilRegisterEmail.text.toString().trim{it<=' '}) -> {
                    Toast.makeText(this@RegisterActivity,
                                    "please enter email",
                                    Toast.LENGTH_SHORT).show()
                }

                TextUtils.isEmpty(tilRegisterPassword.text.toString().trim{it<=' '}) -> {
                    Toast.makeText(this@RegisterActivity,
                        "please enter password",
                        Toast.LENGTH_SHORT).show()
                }

                else ->{
                    val email : String = tilRegisterEmail.text.toString().trim(){it<=' '}
                    val password : String = tilRegisterPassword.text.toString().trim(){it<=' '}

                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(OnCompleteListener<AuthResult> { task ->
                            // If the registration is successfully done
                            if (task.isSuccessful) {
                                // Firebase registered user
                                val firebaseUser: FirebaseUser = task.result!!.user!!

                                Toast.makeText(this@RegisterActivity,
                                    "You are registered successfully",
                                    Toast.LENGTH_SHORT).show()

                                val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                intent.putExtra("user_id", firebaseUser.uid)
                                intent.putExtra("email_id", email)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(this@RegisterActivity,
                                    task.exception!!.message.toString(),
                                    Toast.LENGTH_SHORT).show()
                            }
                        })


                }
            }
        }

        tvLogin.setOnClickListener {
            // Handle click event for tvLogin
            startActivity(Intent(this@RegisterActivity,LoginActivity::class.java))
            finish()

        }


    }



}