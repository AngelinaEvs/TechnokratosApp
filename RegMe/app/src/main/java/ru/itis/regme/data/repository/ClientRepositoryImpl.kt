package ru.itis.regme.data.repository

import android.provider.ContactsContract
import ru.itis.regme.data.db.dao.ClientDao
import ru.itis.regme.data.db.model.Client
import ru.itis.regme.domain.interfaces.ClientRepository
import ru.itis.regme.presenter.ContactModel

class ClientRepositoryImpl (
    var clientDao: ClientDao
) : ClientRepository {

    override fun findAll(): List<String> {
        return clientDao.findAll()
    }

    override fun findAllClients(): List<Client> {
        return clientDao.findAllClients()
    }

    override fun save(client: Client) {
        clientDao.save(client)
    }

    override fun save(listClient: List<Client>) {
        clientDao.save(listClient)
    }

    override fun findAllFromTelephone() {
//        fun getPhoneNumbers(): ArrayList<ContactModel> {
//            var arrayContacts = arrayListOf<ContactModel>()
//            val cursor = context.contentResolver.query(
//                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//                null, null,
//                null, null
//            )
//            cursor?.let {
//                while (cursor.moveToNext()) {
//                    val fullName = it.getString(it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
//                    val phone = it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
//                    val newModel = ContactModel()
//                    newModel.fullname = fullName
//                    newModel.phone = phone
//                    arrayContacts.add(newModel)
//                }
//            }
//            cursor?.close()
//            return arrayContacts
//        }
    }

}