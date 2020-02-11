package com.wyne.firebaseauthentication

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var firstName: String? = null
    private var lastName: String? = null
    private var email: String? = null
    private var password: String? = null
    private var mprogress: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initFirebase()
    }

    private fun initFirebase() {
        mprogress = ProgressDialog(this)
        mDatabase = FirebaseDatabase.getInstance()
        mAuth = FirebaseAuth.getInstance()
        mDatabaseReference = mDatabase!!.reference!!.child("Users")

        btnRegister.setOnClickListener {
            firstName = edtFirstName.text.toString()
            lastName = edtLastName.text.toString()
            email = edtEmail.text.toString()
            password = edtPassword.text.toString()

            createAccount()
        }

    }

    private fun createAccount() {
        mprogress!!.setMessage("Register user...")//if error has, must show error
        mprogress!!.show()

        mAuth!!.createUserWithEmailAndPassword(email!!, password!!)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Success Singup", Toast.LENGTH_SHORT).show()

                    val userID = mAuth!!.currentUser!!.uid
                    verifyEmail()
                    val currentUserDB = mDatabaseReference!!.child(userID)
                    currentUserDB.child("firstName").setValue(firstName)
                    currentUserDB.child("lastName").setValue(lastName)
                    updateUI()

                } else {
                    Toast.makeText(this, "Fail SignUp", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun updateUI() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun verifyEmail() {
        val mUser = mAuth!!.currentUser
        mUser!!.sendEmailVerification()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Verification Email", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this,"Verification Fail",Toast.LENGTH_SHORT).show()
                }

            }
    }

}
