package com.example.pharma

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.pharma.*
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    lateinit var fbAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //supportActionBar!!.hide()
        fbAuth = FirebaseAuth.getInstance()

        if(fbAuth.currentUser !=null){

            val intent=Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }


    fun signupClicked(view: View) {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }


    fun forgotPasswordClicked(view: View) {
        val intent = Intent(this, ForgotPwActivity::class.java)
        startActivity(intent)
    }


    fun signIn(email: String, password: String) {
        Toast.makeText(this, "Authenticating...", Toast.LENGTH_LONG).show()

        fbAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this,"Successfull",Toast.LENGTH_LONG).show()
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("id", fbAuth.currentUser?.email)
                    startActivity(intent)
                    finish()


                } else {
                    Toast.makeText(this, "Invalid email or password", Toast.LENGTH_LONG).show()
                }
            }
    }

    fun loginClicked(view: View) {

        val email = EmailEditTxtLogin.text.toString()
        val password = PwdEditTxtLogin.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty())
        {
            signIn(email, password)
        }
        else if (email.isEmpty() && password.isNotEmpty())
        {
            EmailEditTxtLogin.setError("Email required")
            EmailEditTxtLogin.requestFocus()
        }
        else if (password.isEmpty() && email.isNotEmpty())
        {
            PwdEditTxtLogin.setError("Password required")
            PwdEditTxtLogin.requestFocus()
        }
        else
        {
            Toast.makeText(this, "Don't leave any fields empty", Toast.LENGTH_LONG).show()
        }

    }
}









