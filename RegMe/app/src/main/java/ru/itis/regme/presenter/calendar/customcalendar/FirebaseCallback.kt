package ru.itis.regme.presenter.calendar.customcalendar

interface FirebaseCallback {
    fun onCallback(list: List<Pair<String, Int>>)
    fun onCallbackForDay(list: List<Pair<String, String>>)
    fun onCallbackForLogin(status: Boolean)
}