package ru.itis.regme.presenter.sign.`in`

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.itis.regme.domain.FindUseCase
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
    fun mainIsLogin(): LiveData<Boolean> = mIsLogin

    fun loginStatus(email: String, password: String) {
        viewModelScope.launch {
            try {
                var st: Boolean? = null
                findUseCase.loginFind(email, password).run {
                    mStatus.value = this
                    mNetworkState.value = true
                }
            } catch (throwable: UnknownHostException) {
                mNetworkState.value = false
            }
        }
    }

    fun isLogin() {
        var flag: Boolean = false
        viewModelScope.launch {
            findUseCase.isLogin().run {
                mIsLogin.value = this
            }
        }
    }

}