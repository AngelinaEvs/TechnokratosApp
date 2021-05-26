package ru.itis.regme.domain

import androidx.lifecycle.LiveData
import kotlinx.coroutines.withContext
import ru.itis.regme.data.db.model.Client
import ru.itis.regme.domain.interfaces.ClientRepository
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ClientInterractor @Inject constructor(
    private val clientRepository: ClientRepository,
    private val context: CoroutineContext
) {

    suspend fun findAll(): List<String> =
        withContext(context) {
            clientRepository.findAll()
        }

    suspend fun findAllClients(): List<Client> =
        withContext(context) {
            clientRepository.findAllClients()
        }

    suspend fun save(client: Client) {
        withContext(context) {
            clientRepository.save(client)
        }
    }

    suspend fun save(listClient: List<Client>) {
        withContext(context) {
            clientRepository.save(listClient)
        }
    }

    suspend fun delete(number: String) =
        withContext(context) {
            clientRepository.delete(number)
        }

}