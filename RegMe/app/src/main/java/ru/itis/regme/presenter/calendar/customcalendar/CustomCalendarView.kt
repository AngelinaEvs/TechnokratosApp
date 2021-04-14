package ru.itis.regme.presenter.calendar.customcalendar

import android.app.AlertDialog
import android.app.AlertDialog.Builder
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.my_custom_calendar_layout.view.*
import ru.itis.regme.R
import ru.itis.regme.data.AppRepository
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CustomCalendarView(
    context: Context,
    attributes: AttributeSet?
) : LinearLayout(context) {
    private val MAX_DAYS_IN_MONTH = 42
    var calendar = Calendar.getInstance(Locale.getDefault())
    var simpleDataFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())//dddd mmm yyy
    var simpleMonthFormat = SimpleDateFormat("MMMM", Locale.getDefault())
    var simpleYearFormat = SimpleDateFormat("yyyy", Locale.getDefault())
    var simpleDayFormat = SimpleDateFormat("dddd", Locale.getDefault())
    var simpleEventDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    lateinit var myGridAdapter: MyGridAdapter
    lateinit var alertDialog: AlertDialog
    var dates = ArrayList<Date>()
    var eventsList = ArrayList<Events>()

    var appRepository = AppRepository(context)
    var datesListRecords = ArrayList<String>()

    fun setOnDayClickedListener(listener: (i:Int) -> Unit): Unit
    {
        daysGrid.setOnItemClickListener { p0, p1, p2, p3 -> listener.invoke(p2) }
    }

    init {
        var inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var view = inflater.inflate(R.layout.my_custom_calendar_layout, this)
        setUpCalendar()
        previousButton.setOnClickListener {
            calendar.add(Calendar.MONTH, -1)
            setUpCalendar()
        }
        nextButton.setOnClickListener {
            calendar.add(Calendar.MONTH, 1)
            setUpCalendar()
        }
        daysGrid?.setOnItemClickListener { adapterView, view, i, l ->
            var builder = Builder(context)
            builder.setCancelable(true)
            var addView = LayoutInflater.from(context).inflate(R.layout.write_down_alert, null)
//            addView.findViewById<Button>(R.id.addEventButton).setOnClickListener {
//                saveEvent(event_info.text.toString(), tv_eventTime.text.toString(), date, month, year, Client("AAA", null))
//                alertDialog.dismiss()
//            }

//            addView.findViewById<ImageButton>(R.id.setTimeBut).setOnClickListener {
//                var cal = Calendar.getInstance()
//                var hours = cal.get(Calendar.HOUR_OF_DAY)
//                var minutes = cal.get(Calendar.MINUTE)
//                var timePickerDialog =
//                    TimePickerDialog(addView.context, R.style.Theme_AppCompat_Dialog,
//                        TimePickerDialog.OnTimeSetListener { view, hoursOfDay, minute ->
//                            var c = Calendar.getInstance()
//                            c.set(Calendar.HOUR_OF_DAY, hoursOfDay)
//                            c.set(Calendar.MINUTE, minute)
//                            c.timeZone = TimeZone.getDefault()
//                            var hformat = SimpleDateFormat("H:mm a", Locale.ENGLISH)//K
//                            var eventTime = hformat.format(c.time)
//                            tv_eventTime.text = eventTime
//                        }, hours, minutes, true
//                    )
//                timePickerDialog.show()
//            }

            var date = simpleEventDateFormat.format(dates.get(i))
            var month = simpleMonthFormat.format(dates.get(i))
            var year = simpleYearFormat.format(dates.get(i))

//            var b = findViewById<Button>(R.id.addEventButton)
//            findViewById<Button>(R.id.addEventButton).setOnClickListener {
//                saveEvent(event_info.text.toString(), tv_eventTime.text.toString(), "Abcdef", date, month, year)
//                alertDialog.dismiss()
//            }
            addView.findViewById<EditText>(R.id.tv_eventTime).setText("14:00")
            addView.findViewById<EditText>(R.id.et_eventDate).setText(date)
            addView.findViewById<Button>(R.id.addEventButton).setOnClickListener {
//                addView.setBackgroundColor(Color.CYAN)
                //saveEvent(addView.findViewById<EditText>(R.id.event_info).text.toString(), addView.findViewById<TextView>(R.id.tv_eventTime).text.toString(), date, month, year)
                saveRecord(month, addView.findViewById<EditText>(R.id.et_eventDate).text.toString(),
                        addView.findViewById<EditText>(R.id.tv_eventTime).text.toString(),
                        Client(addView.findViewById<EditText>(R.id.clientName).text.toString(), "8912345678"))
                setUpCalendar()
                alertDialog.dismiss()
            }
            builder.setView(addView)
            alertDialog = builder.create()
            alertDialog.show()
        }
    }

    private fun saveRecord(month: String, date: String, time: String, client: Client) {
        appRepository.saveRecord(month, date, time, client)
        //appRepository.getMonthRecords(month)
    }

    private fun saveEvent(desc: String, time: String, day: String, month: String, year: String/*, client: String*/) {
//        var e = Events(date, time, Client(client, null))
//        e.saveEvent()
        ObjectEvents.add(Events(desc, time, day, month, year, Client("AAA", null)))
        Toast.makeText(context, "SAVED", Toast.LENGTH_SHORT).show()
    }

    private fun setUpCalendar() {
        var currentDate = simpleDataFormat.format(calendar.time)
        currentMonth.text = currentDate
        dates.clear()
        var monthCalendar = calendar.clone() as Calendar
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1)
        var firstDayOfMonth = monthCalendar.get(Calendar.DAY_OF_WEEK) - 2
        monthCalendar.add(Calendar.DAY_OF_MONTH, -firstDayOfMonth)
//        collectEventsPerMonth(simpleMonthFormat.format(calendar.time), simpleYearFormat.format(calendar.time))
        datesListRecords = ArrayList()
        var ev = appRepository.getMonthRecords("April")
        //TODO данные не успевают прийти, что такое?
        datesListRecords.add("2021-04-18")
        datesListRecords.add("2021-04-18")
        datesListRecords.add("2021-04-21")
//        Log.e("VVVVVVVV", ev[0])
//        datesListRecords.add(ev[0])
        while (dates.size < MAX_DAYS_IN_MONTH) {
            dates.add(monthCalendar.time)
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1)
        }
//        myGridAdapter = MyGridAdapter(context, dates, calendar, eventsList)
        myGridAdapter = MyGridAdapter(context, dates, calendar, datesListRecords)
        daysGrid.adapter = myGridAdapter
    }

    private fun collectEventsPerMonth(month: String, year: String) {
        eventsList.clear()
//        val al = appRepository.getMonthRecords("April")
        val al = ObjectEvents.arrayList
        for (i in al.indices) {
            val descEvent = al[i].description
            val timeEvent = al[i].time
            val dayEvent = al[i].day
            val monthEvent = al[i].month
            val yearEvent = al[i].year
            val eventInstance = Events(descEvent, timeEvent, dayEvent, monthEvent, yearEvent, Client("AAA", null))
            eventsList.add(eventInstance)
        }
    }

}
