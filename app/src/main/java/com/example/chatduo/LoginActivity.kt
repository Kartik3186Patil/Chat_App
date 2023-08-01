package com.example.chatduo

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var edtEmail:EditText
    private lateinit var edtPassword:EditText
    private lateinit var btnLogin:Button
    private lateinit var btnSignUp:Button

    private lateinit var mAuth:FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        edtEmail=findViewById(R.id.edt_Email)
        edtPassword=findViewById(R.id.edt_Password)
        btnLogin=findViewById(R.id.btn_login)
        btnSignUp=findViewById(R.id.btn_signup)
        mAuth=FirebaseAuth.getInstance()

        btnSignUp.setOnClickListener{
            val intent= Intent(this,SignUpActivity::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener{
            val email=edtEmail.text.toString()
            val password=edtPassword.text.toString()

            login(email,password)
        }
    }

    private fun login(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent= Intent(this@LoginActivity,MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                   Toast.makeText(this@LoginActivity,"User does not exist",Toast.LENGTH_SHORT).show()
                }
            }
    }
}