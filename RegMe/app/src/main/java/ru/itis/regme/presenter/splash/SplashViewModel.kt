package ru.itis.regme.presenter.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.itis.regme.domain.FindUseCase

class SplashViewModel(
    private var findUseCase: FindUseCase
) : ViewModel() {
    private val mIsLogin: MutableLiveData<Boolean> = MutableLiveData()

    fun mainIsLogin() : LiveData<Boolean> = mIsLogin

    fun isLogin() {
        viewModelScope.launch {
            findUseCase.isLogin().run {
                mIsLogin.value = this
            }
        }
    }
}