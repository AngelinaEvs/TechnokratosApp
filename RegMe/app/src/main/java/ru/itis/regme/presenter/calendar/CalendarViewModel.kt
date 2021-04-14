package ru.itis.regme.presenter.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.itis.regme.domain.FindUseCase
import ru.itis.regme.presenter.calendar.customcalendar.Client

class CalendarViewModel(
    private val findUseCase: FindUseCase
) : ViewModel() {

    fun saveRecord(month: String, date: String, time: String, client: Client) {
        viewModelScope.launch {
            findUseCase.saveRecord(month, date, time, client)
        }
    }

}