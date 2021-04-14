package ru.itis.regme.presenter.forDelete

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import ru.itis.regme.R

class LoginActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()
        val currentuser = auth.currentUser
        if (currentuser != null && currentuser.isEmailVerified) {
            startActivity(Intent(this@LoginActivity, ProfileActivity::class.java))
            finish()
        }
        login()
    }

    private fun login() {
        loginButton.setOnClickListener {
            if (TextUtils.isEmpty(usernameInput.text.toString())){
                usernameInput.error = "Please enter username"
                return@setOnClickListener
            }
            else if (TextUtils.isEmpty(passwordInput.text.toString())){
                usernameInput.error = "Please enter password"
                return@setOnClickListener
            }
            auth.signInWithEmailAndPassword(usernameInput.text.toString(), passwordInput.text.toString())
                .addOnCompleteListener {
                    if(it.isSuccessful) {
                        var user = FirebaseAuth.getInstance().currentUser
                        if (user!!.isEmailVerified) {
                            startActivity(Intent(this@LoginActivity, ProfileActivity::class.java))
                            finish()
                        } else {
                            user.sendEmailVerification()
                            Toast.makeText(this, "Check email", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        Toast.makeText(this@LoginActivity, "Login failed, please try again! ", Toast.LENGTH_LONG).show()
                    }
                }
        }
        registerText.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegistrationActivity::class.java))
        }
    }
}