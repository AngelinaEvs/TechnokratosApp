package ru.itis.regme.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.itis.regme.data.db.model.Client

@Dao
interface ClientDao {

    @Query("select number from Client")
    fun findAll(): List<String>

    @Query("select * from Client")
    fun findAllClients(): List<Client>

    @Insert
    fun save(client: Client)

    @Insert
    fun save(listClient: List<Client>)

    @Query("delete from Client where number = :number")
    fun delete(number: String)

}