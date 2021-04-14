package ru.itis.regme.data

import android.content.Context
import android.provider.CalendarContract
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.coroutines.suspendCancellableCoroutine
import ru.itis.regme.presenter.calendar.customcalendar.Client
import ru.itis.regme.presenter.calendar.customcalendar.Events
import ru.itis.regme.presenter.calendar.customcalendar.Record

class AppRepository(
    var context: Context
) {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var databaseReference: DatabaseReference? = null
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()

    fun isLogin(): Boolean {
        val currentUser = auth.currentUser
        return currentUser != null && currentUser.isEmailVerified
    }

    fun signOut() = auth.signOut()

    fun getMonthRecords(month: String): List<String>/*: List<Record>*/ {
//        var auth: FirebaseAuth = FirebaseAuth.getInstance()
        val user = auth.currentUser
//        var database = FirebaseDatabase.getInstance()
        var databaseReference: DatabaseReference? = database.reference.child("profile")
        val userReference = databaseReference?.child(user?.uid!!)
        var list = ArrayList<String>()
        userReference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
//                for (i in snapshot.children) {
                    var el = snapshot.child("year").child("month").child(month).child("records").child("date").value.toString()
                    Log.e("VALUE", el)
                    list.add(el)
//                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        //return List<Record>(userReference?.child("year")?.child(month)?.child("records").)
        list.add("2021-04-29")
        return list
    }

    fun saveRecord(month: String, date: String, time: String, client: Client) {
        databaseReference = database.reference.child("profile")
        val currentUser = auth.currentUser
        val currentUserDb = databaseReference?.child((currentUser?.uid!!))
//        currentUserDb?.child("year")?.child("month")?.child("monthTitle")?.setValue("April")
        currentUserDb?.child("year")?.child("month")?.child(month)?.child("records")?.setValue(Record(date, time, client))//Events("descr", "13:00", "23", "April", "2021", Client("JJJ", "1234567890")))
        //databaseReference = database.reference.child("profile").child("year").child("records")
        /*databaseReference!!*/
    }

    fun registerUser(email: String, password: String, firstname: String, lastname: String) {
        databaseReference = database.reference.child("profile")
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val currentUser = auth.currentUser
                    val currentUserDb = databaseReference?.child((currentUser?.uid!!))
                    currentUserDb?.child("firstname")?.setValue(firstname)
                    currentUserDb?.child("lastname")?.setValue(lastname)
                    Toast.makeText(context, "Registration Success!", Toast.LENGTH_LONG).show()
                } else Toast.makeText(context, "Registration failed, please try again!", Toast.LENGTH_LONG).show()
            }
    }

    fun loginUser(email: String, password: String): Boolean {
        var flag = false
        //suspendCancellableCoroutine<Boolean> {
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            var user = auth.currentUser
                            if (user!!.isEmailVerified) flag = true
                            else {
                                user.sendEmailVerification()
                                Toast.makeText(context, "Check email", Toast.LENGTH_LONG).show()
                            }
                        } else {
                            Toast.makeText(context, "Fail! ", Toast.LENGTH_LONG).show()
                        }
                    }
        //}
            return flag
        }

}