package ru.itis.regme.presenter.calendar.customcalendar

interface CalendarCallback {
    companion object {
        val EMPTY = object : CalendarCallback {
            override fun next() {}

            override fun prev() {}

            override fun setOnItemClickListener(year: String, monthCB: String, date: String) {}

            override fun currentMonth(date: String, monthCB: String) {}

            override fun currentDay(year: String, monthCB: String, date: String) {}
        }
    }

    fun next()
    fun prev()
    fun setOnItemClickListener(year: String, monthCB: String, date: String)

    //date = datefocus, m=m, y = date.split
    fun currentMonth(date: String, monthCB: String)
    fun currentDay(year: String, monthCB: String, date: String)
}