package ru.itis.regme.presenter.forDelete

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.actiity_profile.*
import ru.itis.regme.R
import java.util.*


class ProfileActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    var databaseReference:  DatabaseReference? = null
    var database: FirebaseDatabase? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actiity_profile)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")

        loadProfile()


//
//
//        val events: MutableList<EventDay> = ArrayList()
//        val calendar: Calendar = Calendar.getInstance()
//        events.add(EventDay(calendar, R.drawable.red_indicator))
////        events.add(EventDay(calendar, R.drawable.back_but_sign))
//
////        calendarView.setDisabledDays()
//       // calendar.set(2021, 3, 3)
////        events.add(EventDay(calendar, R.drawable.back_but_sign))
//        calendarView.setEvents(events)
//
//        calendarView.setOnDayClickListener { eventDay ->
//            val clickedDayCalendar = eventDay.calendar
//
////            var d: Calendar? = null
////            val builder = DatePickerBuilder(this, OnSelectDateListener {
////                d = clickedDayCalendar
////            })
////                .pickerType(CalendarView.ONE_DAY_PICKER)
////            val datePicker = builder.build()
////            datePicker.show()
//
////            val selectedDate = calendarView.firstSelectedDate
////            Toast.makeText(applicationContext, events.contains(EventDay(clickedDayCalendar, R.drawable.red_indicator)).toString(), Toast.LENGTH_SHORT).show()
////            Toast.makeText(applicationContext, , Toast.LENGTH_SHORT).show()
//            //if (!events.contains(EventDay(clickedDayCalendar, R.drawable.red_indicator))) events[events.indexOf(clickedDayCalendar)] = EventDay(clickedDayCalendar, R.drawable.blue_indicator)
//            events.add(EventDay(clickedDayCalendar, R.drawable.red_indicator))
//            calendarView.setEvents(events)
//        }
    }

    private fun loadProfile() {

        val user = auth.currentUser
        val userreference = databaseReference?.child(user?.uid!!)

        emailText.text = "Email: " + user?.email

        userreference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                firstnameText.text = "Firstname: " + snapshot.child("firstname").value.toString()
                lastnameText.text = "Last name: " + snapshot.child("lastname").value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


        logoutButton.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this@ProfileActivity, LoginActivity::class.java))
            finish()
        }
    }
}