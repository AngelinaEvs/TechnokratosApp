package ru.itis.regme.presenter.forDelete

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_registration.*
import ru.itis.regme.R

class RegistrationActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    var databaseReference :  DatabaseReference? = null
    var database: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        auth = FirebaseAuth.getInstance()
        Toast.makeText(applicationContext, auth.toString(), Toast.LENGTH_SHORT).show()
        database = FirebaseDatabase.getInstance()
        Toast.makeText(applicationContext, database.toString(), Toast.LENGTH_SHORT).show()
        databaseReference = database?.reference!!.child("profile")
        Toast.makeText(applicationContext, databaseReference.toString(), Toast.LENGTH_SHORT).show()
        register()
    }

    private fun register() {
        registerButton.setOnClickListener {

            if (TextUtils.isEmpty(firstnameInput.text.toString())) {
                firstnameInput.setError("Please enter first name ")
                return@setOnClickListener
            } else if (TextUtils.isEmpty(lastnameInput.text.toString())) {
                firstnameInput.setError("Please enter last name ")
                return@setOnClickListener
            } else if (TextUtils.isEmpty(usernameInput.text.toString())) {
                firstnameInput.setError("Please enter user name ")
                return@setOnClickListener
            } else if (TextUtils.isEmpty(passwordInput.text.toString())) {
                firstnameInput.setError("Please enter password ")
                return@setOnClickListener
            }

//
//            val actionCodeSettings = ActionCodeSettings.newBuilder()
//                    // URL you want to redirect back to. The domain (www.example.com) for this
//                    // URL must be whitelisted in the Firebase Console.
//                    .setUrl("https://regme-74cf2.firebaseapp.com")
//                    // This must be true
//                    .setHandleCodeInApp(true)
//                    .setIOSBundleId("com.example.ios")
//                    .setAndroidPackageName(
//                            "com.example.android",
//                            true, /* installIfNotAvailable */
//                            "12"    /* minimumVersion */)
//                    .build()
//
//
//            (
//                // URL you want to redirect back to. The domain (www.example.com) for this
//                // URL must be whitelisted in the Firebase Console.
//                url = "https://localhost/finishSignUp",//www.example.com/finishSignUp?cartId=1234"
//                // This must be true
//                handleCodeInApp = true,
//                iosBundleId = "com.example.ios",
//
//                    "com.example.android",
//                    true, /* installIfNotAvailable */
//                    "12" /* minimumVersion */
//            )
//
//
//            auth.sendSignInLinkToEmail(usernameInput.text.toString(), actionCodeSettings)
//                .addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        Toast.makeText(this, "RRRR", Toast.LENGTH_SHORT).show()
//                    }
//                }
            auth.createUserWithEmailAndPassword(usernameInput.text.toString(), passwordInput.text.toString())
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val currentUser = auth.currentUser
                        val currentUSerDb = databaseReference?.child(currentUser?.uid!!)
                        currentUSerDb?.child("firstname")?.setValue(firstnameInput.text.toString())
                        currentUSerDb?.child("lastname")?.setValue(lastnameInput.text.toString())
                        Toast.makeText(this@RegistrationActivity, "Registration Success. ", Toast.LENGTH_LONG).show()
                        finish()
                    } else {
                        Toast.makeText(this@RegistrationActivity, "Registration failed, please try again!!! ", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }

}