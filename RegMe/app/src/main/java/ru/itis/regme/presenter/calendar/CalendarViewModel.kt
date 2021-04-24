package ru.itis.regme.presenter.calendar

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.itis.regme.domain.FindUseCase
import ru.itis.regme.presenter.calendar.customcalendar.Client
import ru.itis.regme.presenter.calendar.customcalendar.FirebaseCallback

class CalendarViewModel(
    private val findUseCase: FindUseCase
) : ViewModel() {
    private val mRecordsForDay: MutableLiveData<List<Pair<String, String>>> = MutableLiveData()

    fun mainRecordsDay() : LiveData<List<Pair<String, String>>> = mRecordsForDay

    fun getRecordForDay(year: String, month: String, day: String) {
        viewModelScope.launch {
            findUseCase.getRecordsForDay(year, month, day, object : FirebaseCallback {
                override fun onCallback(list: List<Pair<String, Int>>) {
                    TODO("Not yet implemented")
                }

                override fun onCallbackForDay(list: List<Pair<String, String>>) {
                    mRecordsForDay.value = list
                }

                override fun onCallbackForLogin(status: Boolean) {
                    TODO("Not yet implemented")
                }

            })
            Log.e("AAAAAAAA", mRecordsForDay.value.toString())
        }
//
//
//        appRepository.getDetailsRecordsForDay("2021", "April", "2021-04-28", object : FirebaseCallback{
//            override fun onCallbackForDay(list: List<Pair<String, String>>) {
//                Log.e("AAAAAAAA", list.toString())
//            }
//
//            override fun onCallback(list: List<Pair<String, Int>>) {
//                TODO("Not yet implemented")
//            }
//        })
    }

    fun onSaveClicked(year: String, month: String, date: String, time: String, client: Client) {
        viewModelScope.launch {
            findUseCase.saveRecord(year, month, date, time, client)
        }
    }

    fun getInitRecords(year: String, month: String, callback: FirebaseCallback) {
        viewModelScope.launch {
            findUseCase.getRecordsForMonth(year, month, callback)
        }
    }


}