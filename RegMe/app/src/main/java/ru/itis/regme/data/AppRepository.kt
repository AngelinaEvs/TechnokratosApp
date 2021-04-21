package ru.itis.regme.data

import android.content.Context
import android.provider.CalendarContract
import android.provider.ContactsContract
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.suspendCancellableCoroutine
import ru.itis.regme.presenter.ContactModel
import ru.itis.regme.presenter.calendar.customcalendar.Client
import ru.itis.regme.presenter.calendar.customcalendar.Events
import ru.itis.regme.presenter.calendar.customcalendar.FirebaseCallback
import ru.itis.regme.presenter.calendar.customcalendar.Record

class AppRepository(
    var context: Context
) {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var databaseReference: DatabaseReference? = null
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()

    fun getPhoneNumbers(): ArrayList<ContactModel> {
        var arrayContacts = arrayListOf<ContactModel>()
        val cursor = context.contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null,
                null, null
        )
        cursor?.let {
            while (cursor.moveToNext()) {
                val fullName = it.getString(it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val phone = it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                val newModel = ContactModel()
                newModel.fullname = fullName
                newModel.phone = phone
                arrayContacts.add(newModel)
            }
        }
        cursor?.close()
        Log.e("REPOSIT: ", arrayContacts.toString())
        return arrayContacts
    } //можно доставать те, которые помечены Клиент словом в  нэйме или каких-то там атрибутах/группах

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

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

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
                            Log.e("DAY", list.toString())
                        }
                        callback.onCallbackForDay(list)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })
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

    fun loginUser(email: String, password: String) : Boolean {
        var flag = false
//            var user = auth.currentUser
//            if (user!!.isEmailVerified) return true
//            else {
//                //Toast.makeText(context, "Check email", Toast.LENGTH_LONG).show()
//                return false
//            }
        auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            //TODO callback надо
                            var user = auth.currentUser
                            if (user!!.isEmailVerified) flag = true
                            Log.e("FLAGGGGGGGGg", flag.toString())
//                        } else {
//                            user?.sendEmailVerification()
//                            Toast.makeText(context, "Check email", Toast.LENGTH_LONG).show()
//                        }
                        } else {
//                            Toast.makeText(context, "Fail! ", Toast.LENGTH_LONG).show()
//                            return false
                        }
                    }
//        }
        Log.e("FLAG", flag.toString())
            return flag
        }

}