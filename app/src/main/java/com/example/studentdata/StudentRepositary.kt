package com.example.studentdata

import androidx.lifecycle.LiveData

// StudentRepository.kt
class StudentRepository(private val studentDao: StudentDao) {
    suspend fun insert(student: StudentModel) {
        studentDao.insert(student)
    }

    fun getAllStudents(): LiveData<List<StudentModel>> {
        return studentDao.getAllStudents()
    }


}