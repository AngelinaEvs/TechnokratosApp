package ru.itis.regme.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.itis.regme.data.db.dao.ClientDao
import ru.itis.regme.data.db.model.Client

@Database(
    version = 1,
    entities = [
        Client::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        private var instance: AppDatabase? = null

        @Synchronized
        fun get(context: Context): AppDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, "clients.db"
                ).build()
            }
            return instance!!
        }

    }

    abstract fun clientsDao(): ClientDao

}