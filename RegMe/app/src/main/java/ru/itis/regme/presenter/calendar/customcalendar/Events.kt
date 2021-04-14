package ru.itis.regme.presenter.calendar.customcalendar

data class Events (
    var description: String,
    var time: String,
    var day: String,
    var month: String,
    var year: String,
    var client: Client
)
