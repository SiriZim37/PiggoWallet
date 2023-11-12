package com.example.piggywallet.module.authen

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.piggywallet.MainActivity
import com.example.piggywallet.R
import com.example.piggywallet.manager.db.datamodel.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class SignUpActivity : AppCompatActivity() {
    

    private val userCollectionRef = Firebase.firestore.collection("Users")
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    lateinit var progressDialog: ProgressDialog
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        initInstances()
    }

    fun  initInstances(){

        progressDialog = ProgressDialog(this)
        textAutoCheck()
//        LoginActivity.open(this)
        signUpBtn.setOnClickListener {
            checkInput()
        }
    }
    private fun textAutoCheck() {

        fullName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (fullName.text!!.isEmpty()){
                    fullName.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)

                }
                else if (fullName.text!!.length >= 4){
                    fullName.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(applicationContext,
                        R.drawable.ic_check
                    ), null)
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
                fullName.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if (count >= 4){
                    fullName.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(applicationContext,
                        R.drawable.ic_check
                    ), null)
                }
            }
        })

        edtEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (edtEmail.text!!.isEmpty()){
                    edtEmail.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)

                }
                else if (edtEmail.text!!.matches(emailPattern.toRegex())) {
                    edtEmail.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(applicationContext,
                        R.drawable.ic_check
                    ), null)
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {

                edtEmail.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if (edtEmail.text!!.matches(emailPattern.toRegex())) {
                    edtEmail.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(applicationContext,
                        R.drawable.ic_check
                    ), null)
                }
            }
        })

        edtPassword.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                if (edtPassword.text!!.isEmpty()){
                    edtPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)

                }
                else if (edtPassword.text!!.length > 5){
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
                if (count > 5){
                    edtPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(applicationContext,
                        R.drawable.ic_check
                    ), null)
                }
            }
        })

        edtRePassword.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                if (edtRePassword.text!!.isEmpty()){
                    edtRePassword.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)

                }
                else if (edtRePassword.text.toString() == edtPassword.text.toString()){
                    edtRePassword.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(applicationContext,
                        R.drawable.ic_check
                    ), null)
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {

                edtRePassword.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if (edtRePassword.text.toString() == edtPassword.text.toString()){
                    edtRePassword.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(applicationContext,
                        R.drawable.ic_check
                    ), null)
                }
            }
        })

    }

    private fun checkInput() {
        if (fullName.text!!.isEmpty()){
            Toast.makeText(this, "Name can't empty!", Toast.LENGTH_SHORT).show()
            return
        }
        if (edtEmail.text!!.isEmpty()){
            Toast.makeText(this, "Email can't empty!", Toast.LENGTH_SHORT).show()
            return
        }

        if (!edtEmail.text!!.matches(emailPattern.toRegex())) {
            Toast.makeText(this, "Enter Valid Email", Toast.LENGTH_SHORT).show() 
            return
        }
        if(edtPassword.text!!.isEmpty()){
            Toast.makeText(this, "Password can't empty!", Toast.LENGTH_SHORT).show()
            return
        }
        if (edtPassword.text!!.toString() != edtRePassword.text.toString()){
            Toast.makeText(this, "Password not Match!", Toast.LENGTH_SHORT).show()
            return
        }

//        Undo comment to signinwith firebase
//        signIn()

        MainActivity.start(this)

    }



    private fun signIn() {

        progressDialog.setTitle("Please Wait")
        progressDialog.setMessage("Creating Account")
        progressDialog.show()

        val emailV: String = edtEmail.text.toString()
        val passV: String = edtPassword.text.toString()
        val fullname : String = fullName.text.toString()



        /*create a user*/
        firebaseAuth.createUserWithEmailAndPassword(emailV,passV)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    progressDialog.setMessage("Save User Data")


                    val user = User(fullname,"",firebaseAuth.uid.toString(),emailV,"","")

                    storeUserData(user)

                    MainActivity.start(this)
                } else {
                    progressDialog.dismiss()
                    Toast.makeText(this, "failed to Authenticate !", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun storeUserData(user: User) = CoroutineScope(Dispatchers.IO).launch {
        try {

            userCollectionRef.document(firebaseAuth.uid.toString()).set(user).await()
            withContext(Dispatchers.Main){
                Toast.makeText(this@SignUpActivity, "Data Saved", Toast.LENGTH_SHORT).show()
                progressDialog.dismiss()
            }

        }catch (e:Exception){
            withContext(Dispatchers.Main){
               e.printStackTrace()
                progressDialog.dismiss()
            }
        }
    }


    companion object{
        fun start(context: Context){
            val intent = Intent(context, SignUpActivity::class.java)
            context.startActivity(intent)
        }
    }

}