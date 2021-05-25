package ru.itis.regme.presenter.sign.`in`

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.itis.regme.domain.FindUseCase
import ru.itis.regme.presenter.ContactModel
import ru.itis.regme.presenter.calendar.customcalendar.FirebaseCallback
import java.net.UnknownHostException

class LoginViewModel(
        private val findUseCase: FindUseCase
) : ViewModel() {

    private val mStatus: MutableLiveData<Boolean> = MutableLiveData()
    private val mNetworkState: MutableLiveData<Boolean> = MutableLiveData()
    private val mIsLogin: MutableLiveData<Boolean> = MutableLiveData()

    fun mainNetworkState() : LiveData<Boolean> = mNetworkState
    fun mainStatus(): LiveData<Boolean> = mStatus

    fun loginStatus(email: String, password: String) {
        viewModelScope.launch {
            try {
                findUseCase.loginFind(email, password, object : FirebaseCallback{
                    override fun onCallback(list: List<Pair<String, Int>>) { }

                    override fun onCallbackForDay(list: List<Pair<String, String>>) { }

                    override fun onCallbackForLogin(status: Boolean) {
                       mStatus.value = status
                    }

                    override fun onCallbackForNotifications(list: List<ContactModel>) {}

                }).run {
                    mNetworkState.value = true
                }
            } catch (throwable: UnknownHostException) {
                mNetworkState.value = false
            }
        }
    }

}