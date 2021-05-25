package ru.itis.regme.presenter.calendar.customcalendar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.*
import kotlinx.android.synthetic.main.my_custom_calendar_layout.view.*
import ru.itis.regme.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CustomCalendarView(
    context: Context,
    attributes: AttributeSet?
) : LinearLayout(context, attributes) {
    private val MAX_DAYS_IN_MONTH = 42
    var calendar = Calendar.getInstance(Locale.ENGLISH)//(Locale.getDefault())
    var simpleDataFormat = SimpleDateFormat("MMMM yyyy", Locale.ENGLISH)
    var simpleMonthFormat = SimpleDateFormat("MMMM", Locale.ENGLISH)
    var simpleMonthNumberFormat = SimpleDateFormat("M", Locale.ENGLISH)
    var simpleYearFormat = SimpleDateFormat("yyyy", Locale.ENGLISH)
    var simpleDayFormat = SimpleDateFormat("dd", Locale.ENGLISH)
    var simpleEventDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    var myGridAdapter: MyGridAdapter? = null
    var currentDate = simpleDataFormat.format(calendar.time)
    var dates = ArrayList<Date>()
    var a = 0
    var calendarCallback = CalendarCallback.EMPTY


    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var view = inflater.inflate(R.layout.my_custom_calendar_layout, this)
        setUpCalendar()
        daysGrid?.setOnItemClickListener { adapterView, view, i, l ->
            val date = simpleEventDateFormat.format(dates[i])
            val month = simpleMonthFormat.format(dates[i])
            calendarCallback.setOnItemClickListener(date.split("-")[0], month, date)
        }
        previousButton.setOnClickListener { prev() }
        nextButton.setOnClickListener { next() }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        var curDate = simpleEventDateFormat.format(calendar.time)
        var curMonth = simpleMonthFormat.format(calendar.time)
        calendarCallback.currentMonth(curDate, simpleMonthFormat.format(calendar.time))
        //calendarCallback.currentDay(curDate.split("-")[0], curMonth, curDate)
    }

    fun prev() {
        calendar.add(Calendar.MONTH, -1)
        setUpCalendar()
        calendarCallback.prev()
    }

    fun next() {
        calendar.add(Calendar.MONTH, 1)
        setUpCalendar()
        calendarCallback.next()
    }

    fun setUpCalendar() {
        currentDate = simpleDataFormat.format(calendar.time)
        currentMonth.text = currentDate
        dates.clear()
        val monthCalendar = calendar.clone() as Calendar
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1)
        val firstDayOfMonth = monthCalendar.get(Calendar.DAY_OF_WEEK) - 2
        monthCalendar.add(Calendar.DAY_OF_MONTH, -firstDayOfMonth)
        while (dates.size < MAX_DAYS_IN_MONTH) {
            dates.add(monthCalendar.time)
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1)
        }
    }

}
