package ru.itis.regme.presenter.phoneNumbers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.itis.regme.domain.FindUseCase
import ru.itis.regme.presenter.ContactModel

class PhoneNumbersViewModel(
    private val findUseCase: FindUseCase
) : ViewModel() {
    private val mNumbers: MutableLiveData<ArrayList<ContactModel>> = MutableLiveData()

    fun mainNumbers() : LiveData<ArrayList<ContactModel>> = mNumbers

    fun getNumbers() {
        viewModelScope.launch {
            findUseCase.getPhoneNumbers().run {
                mNumbers.value = this
            }
        }
    }


}