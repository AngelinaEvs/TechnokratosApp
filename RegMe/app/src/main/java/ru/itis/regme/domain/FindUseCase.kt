package ru.itis.regme.domain

import kotlinx.coroutines.withContext
import ru.itis.regme.data.AppRepository
import ru.itis.regme.presenter.calendar.customcalendar.Client
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class FindUseCase @Inject constructor(
    private val appRepository: AppRepository,
    private val context: CoroutineContext
) {

    suspend fun registerFind(email: String, password: String, firstname: String, lastname: String) =
            withContext(context) {
                appRepository.registerUser(email, password, firstname, lastname)
            }

    suspend fun loginFind(email: String, password: String): Boolean =
            withContext(context) {
                appRepository.loginUser(email, password)
            }

    suspend fun isLogin(): Boolean =
            withContext(context) {
                appRepository.isLogin()
            }

    suspend fun signOut() =
            withContext(context) {
                appRepository.signOut()
            }

    suspend fun saveRecord(month: String, date: String, time: String, client: Client) =
            withContext(context) {
                appRepository.saveRecord(month, date, time, client)
            }
}