package com.example.chatduo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {
    private lateinit var edtName: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnSignUp: Button

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDBRef:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        edtName=findViewById(R.id.edt_NameSign)
        edtEmail=findViewById(R.id.edt_EmailSign)
        edtPassword=findViewById(R.id.edt_PasswordSign)
        btnSignUp=findViewById(R.id.btn_signSign)
        mAuth=FirebaseAuth.getInstance()

        btnSignUp.setOnClickListener{
            val name=edtName.text.toString()
            val email=edtEmail.text.toString()
            val password=edtPassword.text.toString()

            signUp(name,email,password)

        }
    }

    private fun signUp(name:String,email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    addUserToDatabase(name,email,password,mAuth.currentUser?.uid!!)

                    val intent= Intent(this@SignUpActivity,MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    Toast.makeText(this@SignUpActivity,"Some error occurred ",Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun addUserToDatabase(name: String, email: String, password: String, uid: String?) {
            mDBRef=FirebaseDatabase.getInstance().getReference()
             mDBRef.child("user").child(uid!!).setValue(User(name, email, uid))
    }
}