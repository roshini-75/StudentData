package com.example.studentdata

// StudentModel.kt
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class StudentModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0, // Primary key field with auto-generation support
    val name: String,
    val mobileNumber: String,
    val address: String,
    val state: String,
    val zipCode: String
)
