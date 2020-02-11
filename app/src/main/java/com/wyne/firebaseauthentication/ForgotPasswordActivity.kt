package com.wyne.firebaseauthentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : AppCompatActivity() {

    //private var email : String ?= null
    private var mAuth :FirebaseAuth ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        btnForgot.setOnClickListener {
            sendEmail()
        }


    }

    fun sendEmail(){
        val  email = edtForgotEmail.text.toString()
        mAuth!!.sendPasswordResetEmail(email).addOnCompleteListener{task ->

            if(task.isSuccessful){
                val message = "Email sent..."

                Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,"No user",Toast.LENGTH_SHORT).show()
            }

        }

    }
}
