package com.example.studentdata

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


// StudentViewModel.kt
class StudentViewModel(private val repository: StudentRepository) : ViewModel() {

    class Factory(private val repository: StudentRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(StudentViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return StudentViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    fun addStudent(student: StudentModel) {
        viewModelScope.launch {
            repository.insert(student)
        }
    }


    fun getAllStudents(): LiveData<List<StudentModel>> {
        return repository.getAllStudents()
    }
}

