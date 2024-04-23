package com.example.studentdata

// StudentDao.kt
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface StudentDao {
    @Insert
    suspend fun insert(student: StudentModel)

    @Query("SELECT * FROM students")
    fun getAllStudents(): LiveData<List<StudentModel>>

    @Query("DELETE FROM students")
    suspend fun deleteAllStudents()
}
