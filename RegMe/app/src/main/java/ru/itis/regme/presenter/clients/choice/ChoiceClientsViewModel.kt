package ru.itis.regme.presenter.clients.choice

import android.content.Context
import android.provider.ContactsContract
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.launch
import ru.itis.regme.data.db.model.Client
import ru.itis.regme.data.repository.ClientRepositoryImpl
import ru.itis.regme.domain.ClientInterractor
import ru.itis.regme.domain.interfaces.ClientRepository
import ru.itis.regme.presenter.ContactModel

class ChoiceClientsViewModel(
    private val interractor: ClientInterractor
) : ViewModel() {
    private val mNumbers: MutableLiveData<List<String>> = MutableLiveData()
    private val telephNumbers: MutableLiveData<List<Client>> = MutableLiveData()

    fun mainNumbers() : LiveData<List<String>> = mNumbers
    fun telephNumbers() : LiveData<List<Client>> = telephNumbers

    fun getNumbersFromDb() {
        viewModelScope.launch {
            interractor.findAll().run {
                mNumbers.value = this
            }
        }
    }

    fun save(client: Client) {
        viewModelScope.launch {
            interractor.save(client)
        }
    }

    fun findAllFromTelephone(context: Context) {
        viewModelScope.launch {
            var arrayContacts = arrayListOf<Client>()
            val cursor = context.contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null,
                null, null
            )
            cursor?.let {
                while (cursor.moveToNext()) {
                    val fullName = it.getString(it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                    val phone = it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                    val newModel = Client(fullName, phone)
                    arrayContacts.add(newModel)
                }
            }
            telephNumbers.value = arrayContacts
            cursor?.close()
        }
    }

}