package ru.itis.regme.presenter.clients

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.itis.regme.data.db.model.Client
import ru.itis.regme.domain.FindUseCase
import ru.itis.regme.presenter.ContactModel
import ru.itis.regme.presenter.calendar.customcalendar.FirebaseCallback
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class PhoneNumbersViewModel(
    private val findUseCase: FindUseCase
) : ViewModel() {
    private val mNumbers: MutableLiveData<List<ContactModel>> = MutableLiveData()

    fun mainNumbers() : LiveData<List<ContactModel>> = mNumbers

    fun getNumbers() {
        viewModelScope.launch {
            val cal = Calendar.getInstance(Locale.ENGLISH)
            cal.add(Calendar.DAY_OF_YEAR, 1)
            val date = cal.time
            val simpleEventDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            val simpleMonthFormat = SimpleDateFormat("MMMM", Locale.ENGLISH)
            val format = simpleEventDateFormat.format(date)
            findUseCase.getPhoneNumbers(format.split("-")[0], simpleMonthFormat.format(date), format, object : FirebaseCallback {
                override fun onCallback(list: List<Pair<String, Int>>) {}
                override fun onCallbackForDay(list: List<Pair<String, String>>) {}
                override fun onCallbackForLogin(status: Boolean) {}
                override fun onCallbackForNotifications(list: List<ContactModel>) {
                    mNumbers.value = list
                    Log.e("LIST", list.toString())
                }
            })
        }
    }

}