package ru.itis.regme.presenter.calendar.customcalendar

import ru.itis.regme.presenter.calendar.ContactModel

interface FirebaseCallback {
    fun onCallback(list: List<Pair<String, Int>>)
    fun onCallbackForDay(list: List<Pair<String, String>>)
    fun onCallbackForLogin(status: Boolean)
    fun onCallbackForNotifications(list: List<ContactModel>)
}