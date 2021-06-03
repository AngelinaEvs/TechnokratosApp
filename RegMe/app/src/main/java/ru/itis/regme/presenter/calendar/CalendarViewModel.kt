package ru.itis.regme.presenter.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.itis.regme.domain.ClientInterractor
import ru.itis.regme.domain.FindUseCase
import ru.itis.regme.presenter.calendar.customcalendar.FirebaseCallback

class CalendarViewModel(
    private val findUseCase: FindUseCase,
    private val interractor: ClientInterractor
) : ViewModel() {
    private val mRecordsForDay: MutableLiveData<List<Pair<String, String>>> = MutableLiveData()
    private val mRecordsForCalendar: MutableLiveData<List<Pair<String, Int>>> = MutableLiveData()
    private val mPhNumbersString: MutableLiveData<ArrayList<String>> = MutableLiveData()

    fun mainRecordsDay(): LiveData<List<Pair<String, String>>> = mRecordsForDay
    fun mainRecordsCalendar(): LiveData<List<Pair<String, Int>>> = mRecordsForCalendar
    fun mainPnNumbersString(): LiveData<ArrayList<String>> = mPhNumbersString

    fun findAllPhNumbers() {
        viewModelScope.launch {
            interractor.findAllClients().run {
                var list = ArrayList<String>()
                for (i in this.indices) {
                    list.add(this[i].toString())
                }
                mPhNumbersString.value = list
            }
        }
    }

    fun getRecordForDay(year: String, month: String, day: String) {
        viewModelScope.launch {
            findUseCase.getRecordsForDay(year, month, day, object : FirebaseCallback {
                override fun onCallback(list: List<Pair<String, Int>>) {}
                override fun onCallbackForDay(list: List<Pair<String, String>>) {
                    mRecordsForDay.value = list
                }
                override fun onCallbackForLogin(status: Boolean) {}
                override fun onCallbackForNotifications(list: List<ContactModel>) {}
            })
        }
    }

    fun onSaveClicked(
        year: String,
        month: String,
        date: String,
        time: String,
        client: ru.itis.regme.presenter.calendar.customcalendar.Client
    ) {
        viewModelScope.launch {
            findUseCase.saveRecord(year, month, date, time, client)
        }
    }

    fun getInitRecords(year: String, month: String) {
        viewModelScope.launch {
            findUseCase.getRecordsForMonth(year, month, object : FirebaseCallback {
                override fun onCallback(list: List<Pair<String, Int>>) {
                    mRecordsForCalendar.value = list
                }
                override fun onCallbackForDay(list: List<Pair<String, String>>) {}
                override fun onCallbackForLogin(status: Boolean) {}
                override fun onCallbackForNotifications(list: List<ContactModel>) {}
            })

        }
    }


}