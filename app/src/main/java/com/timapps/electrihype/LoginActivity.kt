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
import com.timapps.electrihype.databinding.ActivityMainBinding
import com.timapps.electrihype.databinding.ActivityRegisterBinding
import com.timapps.electrihype.databinding.LoginActivityBinding

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val binding = LoginActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btn_sign_up: Button = binding.btnSignUp

        val btn_login: Button = binding.btnLogin
        val et_login_email: EditText = binding.etLoginEmail
        val et_login_password: EditText = binding.editTextTextPassword
        val tv_forgot_password: TextView = binding.tvForgotPassword

        btn_login.setOnClickListener{
            // Handle click event for btnRegister
            when{

                TextUtils.isEmpty(et_login_email.text.toString().trim{it<=' '}) -> {
                    Toast.makeText(this@LoginActivity,
                        "please enter email",
                        Toast.LENGTH_SHORT).show()
                }

                TextUtils.isEmpty(et_login_password.text.toString().trim{it<=' '}) -> {
                    Toast.makeText(this@LoginActivity,
                        "please enter password",
                        Toast.LENGTH_SHORT).show()
                }

                else ->{
                    val email : String = et_login_email.text.toString().trim(){it<=' '}
                    val password : String = et_login_password.text.toString().trim(){it<=' '}

                    //Log-In using firebase
                    FirebaseAuth.getInstance().signInWithEmailAndPassword (email, password)
                        .addOnCompleteListener(OnCompleteListener<AuthResult> { task ->
                            // If the login is successfully done
                            if (task.isSuccessful) {

                                Toast.makeText(this@LoginActivity,
                                    "You are logged in successfully",
                                    Toast.LENGTH_SHORT).show()

                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                intent.putExtra("user_id", FirebaseAuth.getInstance().currentUser!!.uid)
                                intent.putExtra("email_id", email)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(this@LoginActivity,
                                    task.exception!!.message.toString(),
                                    Toast.LENGTH_SHORT).show()
                            }
                        })


                }
            }
        }

        btn_sign_up.setOnClickListener{
             startActivity(Intent(this@LoginActivity,RegisterActivity::class.java))
             finish()
        }

        tv_forgot_password.setOnClickListener{
            startActivity(Intent(this@LoginActivity,ForgotPasswordActivity::class.java))

        }
    }
}