package ru.itis.regme.domain

import kotlinx.coroutines.withContext
import ru.itis.regme.data.repository.FirebaseRepository
import ru.itis.regme.presenter.calendar.customcalendar.Client
import ru.itis.regme.presenter.calendar.customcalendar.FirebaseCallback
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class FindUseCase @Inject constructor(
        private val firebaseRepository: FirebaseRepository,
        private val context: CoroutineContext
) {

    suspend fun registerFind(email: String, password: String, firstname: String, lastname: String) =
            withContext(context) {
                firebaseRepository.registerUser(email, password, firstname, lastname)
            }

    suspend fun loginFind(email: String, password: String, callback: FirebaseCallback) =
            withContext(context) {
                firebaseRepository.loginUser(email, password, callback)
            }

    suspend fun isLogin(): Boolean =
            withContext(context) {
                firebaseRepository.isLogin()
            }

    suspend fun signOut() =
            withContext(context) {
                firebaseRepository.signOut()
            }

    suspend fun saveRecord(year: String, month: String, date: String, time: String, client: Client) =
            withContext(context) {
                firebaseRepository.saveRecord(year, month, date, time, client)
            }

    suspend fun getRecordsForMonth(year: String, month: String, firebaseCallback: FirebaseCallback) =
            withContext(context) {
                firebaseRepository.getCountRecordsForMonth(year, month, firebaseCallback)
            }

    suspend fun getRecordsForDay(year: String, month: String, day: String, firebaseCallback: FirebaseCallback) =
            withContext(context) {
                firebaseRepository.getDetailsRecordsForDay(year, month, day, firebaseCallback)
            }

    suspend fun getPhoneNumbers(year: String, month: String, day: String, firebaseCallback: FirebaseCallback) =
            withContext(context) {
                firebaseRepository.getPhoneNumbers(year, month, day, firebaseCallback)
            }
}