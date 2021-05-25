package ru.itis.regme.domain.interfaces

import androidx.lifecycle.LiveData
import ru.itis.regme.data.db.model.Client

interface ClientRepository {

    fun findAll(): List<String>

    fun findAllClients(): List<Client>

    fun save(client: Client)

    fun save(listClient: List<Client>)

    fun findAllFromTelephone()
}
