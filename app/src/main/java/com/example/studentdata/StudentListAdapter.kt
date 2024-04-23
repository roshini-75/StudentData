
package com.example.studentdata
// StudentListAdapter.k

// StudentListAdapter.kt
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.studentdata.databinding.ItemStudentBinding

class StudentListAdapter(private var studentList: List<StudentModel>) :
    RecyclerView.Adapter<StudentListAdapter.StudentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val binding = ItemStudentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StudentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = studentList[position]
        holder.bind(student)


    }


    override fun getItemCount(): Int {
        return studentList.size
    }

    fun submitList(newList: List<StudentModel>) {
        studentList = newList
        notifyDataSetChanged()
    }

    inner class StudentViewHolder(private val binding: ItemStudentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(student: StudentModel) {
            val name = "Name: ${student.name}"
            val address = "Address: ${student.address}"
            val state = "State: ${student.state}"
            val zipCode = "Zip code: ${student.zipCode}"
            val mobileNumber = "Mobile Number: ${student.mobileNumber}"

            binding.nameTextView.text = name
            binding.addressTextView.text = address
            binding.stateTextView.text = state
            binding.zipCodeTextView.text = zipCode
            binding.mobileNumberTextView.text = mobileNumber

        }
    }
}

