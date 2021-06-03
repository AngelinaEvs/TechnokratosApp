package ru.itis.regme.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Client (
//    @ColumnInfo(name = "name")
    var name: String,

//    @ColumnInfo(name = "number")
    var number: String?
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    override fun toString(): String {
        return "$name($number)"
    }

}