package ru.itis.regme.data.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import ru.itis.regme.presenter.calendar.ContactModel
import ru.itis.regme.presenter.calendar.customcalendar.Client
import ru.itis.regme.presenter.calendar.customcalendar.FirebaseCallback
import ru.itis.regme.presenter.calendar.customcalendar.Record

class FirebaseRepository(
        var context: Context
) {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var databaseReference: DatabaseReference? = null
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()

//    fun getPhoneNumbers(): ArrayList<ContactModel> {
//        var arrayContacts = arrayListOf<ContactModel>()
//        val cursor = context.contentResolver.query(
//                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//                null, null,
//                null, null
//        )
//        cursor?.let {
//            while (cursor.moveToNext()) {
//                val fullName = it.getString(it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
//                val phone = it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
//                val newModel = ContactModel()
//                newModel.fullname = fullName
//                newModel.phone = phone
//                arrayContacts.add(newModel)
//            }
//        }
//        cursor?.close()
//        return arrayContacts
//    }

    fun isLogin(): Boolean {
        val currentUser = auth.currentUser
        return currentUser != null && currentUser.isEmailVerified
    }

    fun signOut() = auth.signOut()

    fun getCountRecordsForMonth(year: String, month: String, callback: FirebaseCallback) {
        databaseReference = database.reference.child("profile")
        val currentUserDb = databaseReference?.child(auth.currentUser?.uid!!)
        var list = ArrayList<Pair<String, Int>>()
        currentUserDb?.child(year)
                ?.child("months")
                ?.child(month)
                ?.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        var i = snapshot.children.iterator()
                        while (i.hasNext()) {
                            var item = i.next()
                            list.add(Pair(item.key!!, item.childrenCount.toInt()))
                        }
                        callback.onCallback(list)
                    }

                    override fun onCancelled(error: DatabaseError) {}
                })
    }

    fun getDetailsRecordsForDay(year: String, month: String, day: String, callback: FirebaseCallback) {
        databaseReference = database.reference.child("profile")
        val currentUserDb = databaseReference?.child(auth.currentUser?.uid!!)
        val list = ArrayList<Pair<String, String>>()
        currentUserDb?.child(year)
                ?.child("months")
                ?.child(month)
                ?.child(day)
                ?.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        var i = snapshot.children.iterator()
                        while (i.hasNext()) {
                            var item = i.next()
                            var t = item.child("time").value.toString()
                            var name = item.child("client").child("name").value.toString()
                            list.add(Pair(t, name))
                        }
                        callback.onCallbackForDay(list)
                    }

                    override fun onCancelled(error: DatabaseError) {}

                })
    }

    fun getPhoneNumbers(year: String, month: String, date: String, callback: FirebaseCallback): ArrayList<ContactModel> {
        Log.e("DATA", year + " " + month + " " + date)
        databaseReference = database.reference.child("profile")
        val currentUserDb = databaseReference?.child(auth.currentUser?.uid!!)
        var arrayContacts = arrayListOf<ContactModel>()
        currentUserDb?.child(year)
            ?.child("months")
            ?.child(month)
            ?.child(date)
            ?.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var i = snapshot.children.iterator()
                    while (i.hasNext()) {
                        var item = i.next()
                        var t = item.child("time").value.toString()
                        var name = item.child("client").child("name").value.toString()
                        var number = item.child("client").child("phoneNumber").value.toString()
                        arrayContacts.add(ContactModel(t, name, number))
                    }
                    callback.onCallbackForNotifications(arrayContacts)
                }
                override fun onCancelled(error: DatabaseError) {}

            })
        return arrayContacts
    }

    fun saveRecord(year: String, month: String, date: String, time: String, client: Client) {
        databaseReference = database.reference.child("profile")
        val currentUserDb = databaseReference?.child(auth.currentUser?.uid!!)
        currentUserDb?.child(year)
                ?.child("months")
                ?.child(month)
                ?.child(date)
                ?.push()?.setValue(Record(time, client))
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
                        currentUser?.sendEmailVerification()
                        Toast.makeText(context, "Registration Success!", Toast.LENGTH_LONG).show()
                    } else Toast.makeText(context, "Registration failed, please try again!", Toast.LENGTH_LONG).show()
                }
    }

    fun loginUser(email: String, password: String, callback: FirebaseCallback) {
        var flag = false
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        var user = auth.currentUser
                        if (user!!.isEmailVerified) flag = true
                        else Toast.makeText(context, "Check email", Toast.LENGTH_LONG).show()
                        Log.e("FLAGGGGGGGGg", flag.toString())
                        callback.onCallbackForLogin(flag)
                    } else Toast.makeText(context, "Fail! ", Toast.LENGTH_LONG).show()
                }
    }

}