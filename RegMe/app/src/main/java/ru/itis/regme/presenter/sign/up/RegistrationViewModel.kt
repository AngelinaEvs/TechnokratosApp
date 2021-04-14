package ru.itis.regme.presenter.sign.up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.itis.regme.domain.FindUseCase

class RegistrationViewModel(
        private val findUseCase: FindUseCase
) : ViewModel() {
//    private var userMutableLivaData: MutableLiveData<FirebaseUser> = MutableLiveData()

    fun register(email: String, password: String, firstname: String, lastname: String) {
        viewModelScope.launch {
            findUseCase.registerFind(email, password, firstname, lastname)
        }
    }

}
