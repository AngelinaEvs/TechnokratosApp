package ru.itis.regme.presenter.calendar.customcalendar

import android.app.AlertDialog
import android.app.AlertDialog.Builder
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
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
) : LinearLayout(context, attributes) {
    private val MAX_DAYS_IN_MONTH = 42
    var calendar = Calendar.getInstance(Locale.ENGLISH)//(Locale.getDefault())
    var simpleDataFormat = SimpleDateFormat("MMMM yyyy", Locale.ENGLISH)//dddd mmm yyy
    var simpleMonthFormat = SimpleDateFormat("MMMM", Locale.ENGLISH)
    var simpleMonthNumberFormat = SimpleDateFormat("M", Locale.ENGLISH)
    var simpleYearFormat = SimpleDateFormat("yyyy", Locale.ENGLISH)
    var simpleDayFormat = SimpleDateFormat("dd", Locale.ENGLISH)
    var simpleEventDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    var myGridAdapter: MyGridAdapter? = null
    lateinit var alertDialog: AlertDialog
    var currentDate = simpleDataFormat.format(calendar.time)
    var dates = ArrayList<Date>()
//    var eventsList = ArrayList<Events>()

//    var appRepository = AppRepository(context)
    var datesListRecords = ArrayList<String>()
    var a = 0

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var view = inflater.inflate(R.layout.my_custom_calendar_layout, this)
//        var addView = LayoutInflater.from(context).inflate(R.layout.write_down_alert, null)
        setUpCalendar()
//        daysGrid?.setOnItemClickListener { adapterView, view, i, l ->
//            var addView = LayoutInflater.from(context).inflate(R.layout.write_down_alert, null)
//            var builder = Builder(context)
//            builder.setCancelable(true)
//            var date = simpleEventDateFormat.format(dates[i])
//            var month = simpleMonthFormat.format(dates[i])
//            var year = simpleYearFormat.format(dates[i])
//            addView.findViewById<EditText>(R.id.tv_eventTime).setText("14:00")
//            addView.findViewById<EditText>(R.id.et_eventDate).setText(date)
//            addView.findViewById<Button>(R.id.addEventButton).setOnClickListener {
//                saveRecord(year, month, addView.findViewById<EditText>(R.id.et_eventDate).text.toString(),
//                        addView.findViewById<EditText>(R.id.tv_eventTime).text.toString(),
//                        Client(addView.findViewById<EditText>(R.id.clientName).text.toString(), "8912345678"))
//                setUpCalendar()
//                alertDialog.dismiss()
//            }
//            builder.setView(addView)
//            alertDialog = builder.create()
//            alertDialog.show()
//        }
        previousButton.setOnClickListener {
            calendar.add(Calendar.MONTH, -1)
            setUpCalendar()
        }
        nextButton.setOnClickListener {
            calendar.add(Calendar.MONTH, 1)
            setUpCalendar()
        }
    }

//    private fun saveRecord(year: String, month: String, date: String, time: String, client: Client) =
//            appRepository.saveRecord(year, month, date, time, client)

    fun setUpCalendar() {
        currentDate = simpleDataFormat.format(calendar.time)
        currentMonth.text = currentDate
        dates.clear()
        val monthCalendar = calendar.clone() as Calendar
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1)
        val firstDayOfMonth = monthCalendar.get(Calendar.DAY_OF_WEEK) - 2
        monthCalendar.add(Calendar.DAY_OF_MONTH, -firstDayOfMonth)
//        datesListRecords = ArrayList()
//        val recordList = ArrayList<Pair<String, Int>>()
        while (dates.size < MAX_DAYS_IN_MONTH) {
            dates.add(monthCalendar.time)
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1)
        }
//        appRepository.getCountRecordsForMonth(currentDate.split(" ")[1], currentDate.split(" ")[0], object : FirebaseCallback {
//            override fun onCallback(list: List<Pair<String, Int>>) {
//                recordList.addAll(list)
//                if (myGridAdapter != null && !myGridAdapter!!.listEvents.isEmpty()) myGridAdapter?.updateData(list)
//                else {
//                    myGridAdapter = MyGridAdapter(context, dates, calendar, list as MutableList<Pair<String, Int>>, simpleDayFormat.format(calendar.time).toInt(), simpleMonthNumberFormat.format(calendar.time).toInt())
//                    daysGrid.adapter = myGridAdapter
//                }
//            }
//
//            override fun onCallbackForDay(list: List<Pair<String, String>>) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onCallbackForLogin(status: Boolean) {
//                TODO("Not yet implemented")
//            }
//        })
    }

}
