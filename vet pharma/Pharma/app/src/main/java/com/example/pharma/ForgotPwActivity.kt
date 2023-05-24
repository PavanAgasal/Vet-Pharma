package com.example.pharma

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forgot_pw.*

class ForgotPwActivity : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_pw)

        mAuth = FirebaseAuth.getInstance()
    }

    fun sendRecoveryEmailClicked(view: View){

        val email = emailEditTextForgotpw.text.toString()

        if (email.isNotEmpty())
        {

            sendRecoveryEmail(email)

        }else{

            showFailure("Enter valid email address to recover account")

        }

    }


    private fun sendRecoveryEmail(email: String){

        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener {task ->

                    if (task.isSuccessful) showSuccess()

                }
                .addOnFailureListener {err ->

                    showFailure(err.localizedMessage!!)

                }

    }


    private fun showFailure(message: String){

        val builder = AlertDialog.Builder(this)
        //set title for alert dialog
        builder.setTitle("Vet Pharma")
        //set message for alert dialog
        builder.setMessage(message)
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        //performing positive action
        builder.setPositiveButton("OK"){ _,_ ->

        }

        builder.show()

    }


    private fun showSuccess(){

        val builder = AlertDialog.Builder(this)
        //set title for alert dialog
        builder.setTitle("Vet Pharma")
        //set message for alert dialog
        builder.setMessage("Recovery email sent successfully")
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        //performing positive action
        builder.setPositiveButton("OK"){ _,_ ->

        }

        builder.show()

    }
}
