package ru.itis.regme.domain

import kotlinx.coroutines.withContext
import ru.itis.regme.data.AppRepository
import ru.itis.regme.presenter.calendar.customcalendar.Client
import ru.itis.regme.presenter.calendar.customcalendar.FirebaseCallback
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

    suspend fun loginFind(email: String, password: String) =
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

    suspend fun saveRecord(year: String, month: String, date: String, time: String, client: Client) =
            withContext(context) {
                appRepository.saveRecord(year, month, date, time, client)
            }

    suspend fun getRecordsForMonth(year: String, month: String, firebaseCallback: FirebaseCallback) =
            withContext(context) {
                appRepository.getCountRecordsForMonth(year, month, firebaseCallback)
            }

    suspend fun getRecordsForDay(year: String, month: String, day: String, firebaseCallback: FirebaseCallback) =
            withContext(context) {
                appRepository.getDetailsRecordsForDay(year, month, day, firebaseCallback)
            }

    suspend fun getPhoneNumbers() =
            withContext(context) {
                appRepository.getPhoneNumbers()
            }
}