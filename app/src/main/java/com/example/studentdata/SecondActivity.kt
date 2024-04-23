package com.example.studentdata

// Corrected SecondActivity.kt
// Corrected SecondActivity.kt
// SecondActivity.kt
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studentdata.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding
    private lateinit var viewModel: StudentViewModel
    private lateinit var adapter: StudentListAdapter
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val studentDao = StudentDatabase.getInstance(applicationContext).studentDao()
        val repository = StudentRepository(studentDao)
        val viewModelFactory = StudentViewModel.Factory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(StudentViewModel::class.java)
        adapter = StudentListAdapter(emptyList())

        binding.studentRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.studentRecyclerView.adapter = adapter

        // After setting up adapter and layout manager
        val spacingDecoration = ItemSpacingDecoration(3) // Adjust the space height as needed
        binding.studentRecyclerView.addItemDecoration(spacingDecoration)





        viewModel.getAllStudents().observe(this, Observer { students ->
            students?.let {
                adapter.submitList(it)


            }
        })



        // Set click listener for the ImageButton using binding
        binding.backButton.setOnClickListener {
            // Navigate back to the registerFragment


            findNavController(R.id.fragment_container_view).popBackStack()
        }
    }
}
