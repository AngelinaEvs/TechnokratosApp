package ru.itis.regme.presenter.calendar.customcalendar

interface CalendarCallback {
    companion object {
        val EMPTY = object : CalendarCallback {
            override fun next() {}

            override fun prev() {}

            override fun setOnItemClickListener(year: String, monthCB: String, date: String) {}

            override fun currentDay(year: String, monthCB: String, date: String) {}
        }
    }

    fun next()
    fun prev()
    fun setOnItemClickListener(year: String, monthCB: String, date: String)
    fun currentDay(year: String, monthCB: String, date: String)
}