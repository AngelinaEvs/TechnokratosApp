package ru.itis.regme.presenter.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.itis.regme.domain.FindUseCase

class ProfileViewModel(
        private val findUseCase: FindUseCase
) : ViewModel() {

    fun signOut() {
        viewModelScope.launch {
            findUseCase.signOut()
        }
    }
}