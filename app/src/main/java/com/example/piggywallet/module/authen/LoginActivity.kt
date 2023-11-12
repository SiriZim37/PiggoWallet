package com.example.piggywallet.module.authen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.piggywallet.MainActivity
import com.example.piggywallet.R
import com.example.piggywallet.utils.FirebaseUtils.firebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    lateinit var signInEmail: String
    lateinit var signInPassword: String
    lateinit var emailError: TextView
    lateinit var passwordError: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        textAutoCheck()

        signUpTv.setOnClickListener {
              SignUpActivity.start(this)
        }

        btnLogin.setOnClickListener {
//        Undo comment to signinwith firebase
//        checkInput()
            MainActivity.start(this)
        }

    }
    private fun textAutoCheck() {
        edtEmail.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                if (edtEmail.text!!.isEmpty()){
                    edtEmail.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)

                }
                else if (Patterns.EMAIL_ADDRESS.matcher(edtEmail.text).matches()) {
                    edtEmail.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(applicationContext,
                        R.drawable.ic_check
                    ), null)
                    emailError.visibility = View.GONE
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {

                edtEmail.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if (Patterns.EMAIL_ADDRESS.matcher(edtEmail.text).matches()) {
                    edtEmail.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(applicationContext,
                        R.drawable.ic_check
                    ), null)
                    emailError.visibility = View.GONE
                }
            }
        })

        edtPassword.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                if (edtPassword.text!!.isEmpty()){
                    edtPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)

                }
                else if (edtPassword.text!!.length > 4){
                    edtPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(applicationContext,
                        R.drawable.ic_check
                    ), null)

                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {

                edtPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                passwordError.visibility = View.GONE
                if (count > 4){
                    edtPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(applicationContext,
                        R.drawable.ic_check
                    ), null)

                }
            }
        })



    }

    private fun checkInput() {

        if (edtEmail.text!!.isEmpty()){
            emailError.visibility = View.VISIBLE
            emailError.text = "Email Can't be Empty"
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(edtEmail.text).matches()) {
            emailError.visibility = View.VISIBLE
            emailError.text = "Enter Valid Email"
            return
        }
        if(edtPassword.text!!.isEmpty()){
            passwordError.visibility = View.VISIBLE
            passwordError.text = "Password Can't be Empty"
            return
        }

        if ( edtPassword.text!!.isNotEmpty() && edtEmail.text!!.isNotEmpty()){
            emailError.visibility = View.GONE
            passwordError.visibility = View.GONE
            signInUser()
        }
    }


    private fun signInUser() {

        signInEmail = edtEmail.text!!.toString().trim()
        signInPassword = edtPassword.text!!.toString().trim()
        firebaseAuth.signInWithEmailAndPassword(signInEmail, signInPassword)
            .addOnCompleteListener { signIn ->
                if (signIn.isSuccessful) {
                    startActivity(Intent(this, MainActivity::class.java))
                    Toast.makeText(this, "Signed In", Toast.LENGTH_SHORT).show()
                    finish()

                } else {
                    Toast.makeText(this, "Sign in failed", Toast.LENGTH_SHORT).show()
                }
            }
    }

    companion object {
        
       fun open( context : Context){
           val intent = Intent(context, LoginActivity::class.java)
           context?.startActivity(intent)
       }
    }


}