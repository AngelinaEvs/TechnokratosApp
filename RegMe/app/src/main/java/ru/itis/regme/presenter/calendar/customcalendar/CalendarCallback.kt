package ru.itis.regme.presenter.calendar.customcalendar

interface CalendarCallback {
    fun next()
    fun prev()
    fun setOnItemClickListener(year: String, monthCB: String, date: String)
    //date = datefocus, m=m, y = date.split
    fun current(date: String, monthCB: String)
}