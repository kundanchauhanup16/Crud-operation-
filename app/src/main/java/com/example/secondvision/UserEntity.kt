package com.example.secondvision

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var name: String,
    var phone: String,
    var password: String,
    var mail: String,
    var gender: String,
)
