package ru.itis.regme

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import ru.itis.regme.presenter.ContactModel

class MainActivity : AppCompatActivity() {
    private val READ_CONTACTS = Manifest.permission.READ_CONTACTS
    private val PERMISSION_REQUEST = 200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initContacts()
    }

    private fun initContacts() {
        if (checkPermission(READ_CONTACTS)) {
//            Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show()
//            var arrayContacts = arrayListOf<ContactModel>()
//            val cursor = this.contentResolver.query(
//                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//                    null, null,
//                    null, null
//            )
//            cursor?.let {
//                while (cursor.moveToNext()) {
//                    val fullName = it.getString(it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
//                    val phone = it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
//                    val newModel = ContactModel()
//                    newModel.fullname = fullName
//                    newModel.phone = phone
//                    arrayContacts.add(newModel)
//                }
//            }
//            cursor?.close()
//            Log.e("PHONE", arrayContacts.toString())
//            Toast.makeText(this, arrayContacts.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (ContextCompat.checkSelfPermission(this, READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            //initContacts()
        }
    }

    private fun checkPermission(permission: String): Boolean {
        return if (Build.VERSION.SDK_INT >= 23
                && ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, arrayOf(permission), PERMISSION_REQUEST)
                    false
                } else true
    }

}