package com.example.studentdata

import android.graphics.PorterDuff
import android.graphics.Rect
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.studentdata.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private lateinit var viewModel: StudentViewModel
    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onResume() {
        super.onResume()

        // Clear the entered data when the fragment is resumed
        binding.nameEdit.text.clear()
        binding.numberEdit.text.clear()
        binding.addressEdit.text.clear()
        binding.stateEdit.text.clear()
        binding.zipCodeEdit.text.clear()


        binding.numberEmptyMessage.visibility = View.GONE
        binding.zipcodeEmptyMessage.visibility = View.GONE
        binding.numberEdit.setBackgroundResource(R.drawable.edittext_border)

        binding.zipCodeEdit.setBackgroundResource(R.drawable.edittext_border)
        // Clear more fields as needed
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val indianStates = resources.getStringArray(R.array.indian_states)

        // Create an ArrayAdapter using the string array and a default spinner layout
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, indianStates)

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Apply the adapter to the spinner
        binding.stateEdit.setAdapter(adapter)

        // Handle item selection
        binding.stateEdit.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            // Handle the selected item
            val selectedState = indianStates[position]

        }

        val studentDao = StudentDatabase.getInstance(requireContext()).studentDao()
        val repository = StudentRepository(studentDao)
        val viewModelFactory = StudentViewModel.Factory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(StudentViewModel::class.java)

        // Set the textWatcher on the EditText fields
        val submitButton = binding.registerBtn

        // Disable the button initially
        submitButton.isEnabled = false

        // Define colors for enabled and disabled states
        val disabledColor = ContextCompat.getColorStateList(requireContext(), R.color.light_blue_disabled)
        val enabledColor = ContextCompat.getColorStateList(requireContext(), R.color.original_button_color)

        // Set the initial background tint to the disabled color
        submitButton.backgroundTintList = disabledColor

        // Create a single TextWatcher instance
        val textWatcher = object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // Enable the button if all EditText fields have non-empty text, disable otherwise
                val name = binding.nameEdit.text.toString().trim()
                val number = binding.numberEdit.text.toString().trim()
                val address = binding.addressEdit.text.toString().trim()
                val state = binding.stateEdit.text.toString().trim() // New state field
                val zipCode = binding.zipCodeEdit.text.toString().trim() // New zip code field

                submitButton.isEnabled = name.isNotEmpty() &&
                        number.length == 10 &&
                        address.isNotEmpty() &&
                        state.isNotEmpty() &&
                        zipCode.length == 6

                // Change button background color based on its state
                submitButton.backgroundTintList = if (submitButton.isEnabled) enabledColor else disabledColor
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Not needed for this implementation
            }

            override fun afterTextChanged(s: Editable) {
                // Not needed for this implementation
            }
        }

// Attach the TextWatcher to all EditText fields
        binding.nameEdit.addTextChangedListener(textWatcher)
        binding.numberEdit.addTextChangedListener(textWatcher)
        binding.addressEdit.addTextChangedListener(textWatcher)
        binding.stateEdit.addTextChangedListener(textWatcher) // Add watcher for state field
        binding.zipCodeEdit.addTextChangedListener(textWatcher) // Add watcher for zip code field

        // Text change listener for name EditText
        binding.nameEdit.addTextChangedListener {
            binding.nameEmptyMessage.visibility = View.GONE
            binding.nameEdit.setBackgroundResource(R.drawable.edittext_border)
            binding.nameEdit.inputType = InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
        }

        // Text change listener for address EditText
        binding.addressEdit.addTextChangedListener {
            binding.addressEmptyMessage.visibility = View.GONE
            binding.addressEdit.setBackgroundResource(R.drawable.edittext_border)
            binding.addressEdit.inputType = InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
        }


        // Text change listener for number EditText
        binding.numberEdit.addTextChangedListener {
            val mobileNumber = it.toString()
            if (mobileNumber.length == 10) {
                binding.numberEmptyMessage.visibility = View.GONE
                binding.numberEdit.setBackgroundResource(R.drawable.edittext_border)
            } else {
                // Show red border and error message if the length is not 10
                binding.numberEmptyMessage.text = "Mobile number should contain 10 digits"
                binding.numberEmptyMessage.visibility = View.VISIBLE
                binding.numberEdit.setBackgroundResource(R.drawable.edittext_border_red)
            }
        }

        binding.zipCodeEdit.addTextChangedListener {
            val zipCode = it.toString().trim()
            if (zipCode.length == 6) {
                // Hide error message and set normal background if the length is 6
                binding.zipcodeEmptyMessage.visibility = View.GONE
                binding.zipCodeEdit.setBackgroundResource(R.drawable.edittext_border)
            } else {
                // Show red border and error message if the length is not 6
                binding.zipcodeEmptyMessage.visibility = View.VISIBLE
                binding.zipcodeEmptyMessage.text = "Zip code should be 6 digits"
                binding.zipCodeEdit.setBackgroundResource(R.drawable.edittext_border_red)
            }
        }


// Focus change listener for number EditText
        binding.numberEdit.setOnFocusChangeListener { _, hasFocus ->
            val mobileNumber = binding.numberEdit.text.toString()
            if (!hasFocus) {
                if (mobileNumber.length != 10) {
                    // Show red border and error message if the length is not 10 when losing focus
                    binding.numberEmptyMessage.text = "Mobile number should contain 10 digits"
                    binding.numberEmptyMessage.visibility = View.VISIBLE
                    binding.numberEdit.setBackgroundResource(R.drawable.edittext_border_red)
                }
            } else {
                // Hide error message when gaining focus
                binding.numberEmptyMessage.visibility = View.GONE
            }
        }

        // Set OnFocusChangeListener for each EditText
        binding.nameEdit.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                scrollToView(binding.nameEdit)
            }
        }

        binding.numberEdit.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                scrollToView(binding.numberEdit)
            }
        }

        binding.addressEdit.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                scrollToView(binding.addressEdit)
            }
        }

        binding.stateEdit.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                scrollToView(binding.stateEdit)
            }
        }

        binding.zipCodeEdit.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                scrollToView(binding.zipCodeEdit)
            }
        }




        binding.stateEdit.setOnItemClickListener { _, _, position, _ ->
            // Handle the selected item
            val selectedState = indianStates[position]
        }

// Set an OnClickListener on the stateEdit AutoCompleteTextView
        binding.stateEdit.setOnClickListener {
            // Show the dropdown list programmatically
            binding.stateEdit.showDropDown()

            binding.stateInputLayout.hint = ""

            binding.stateEdit.dropDownBackground.setColorFilter(
                ContextCompat.getColor(requireContext(), R.color.white),
                PorterDuff.Mode.SRC_ATOP
            )
        }

        binding.stateEdit.setOnFocusChangeListener { _, hasFocus ->
            // Show the hint only if no item is selected and the view loses focus
            if (!hasFocus && binding.stateEdit.text.isEmpty()) {
                binding.stateInputLayout.hint = "select"
            }
        }



        binding.registerBtn.setOnClickListener {
            val name = binding.nameEdit.text.toString()
            val mobileNumber = binding.numberEdit.text.toString()
            val address = binding.addressEdit.text.toString()
            val state = binding.stateEdit.text.toString()
            val zipCode = binding.zipCodeEdit.text.toString()

            if (name.isNotBlank() && mobileNumber.isNotBlank() && address.isNotBlank() && state.isNotBlank() && zipCode.isNotBlank()) {
                val student = StudentModel(
                    name = name,
                    mobileNumber = mobileNumber,
                    address = address,
                    state = state,
                    zipCode = zipCode
                )
                viewModel.addStudent(student)



                Toast.makeText(
                    requireContext(),
                    "Student registered successfully",
                    Toast.LENGTH_SHORT
                ).show()
               // Log.d("RegisterFragment", "Student Details: $student")

                findNavController().navigate(R.id.action_registerFragment_to_secondActivity)

               // Log.d("RegisterFragment", "Student Details: $student")


            } else {
                if (name.isBlank()) {
                    binding.nameEmptyMessage.visibility = View.VISIBLE
                    binding.nameEdit.setBackgroundResource(R.drawable.edittext_border_red)
                }
                if (mobileNumber.isBlank()) {
                    binding.numberEmptyMessage.visibility = View.VISIBLE
                    binding.numberEdit.setBackgroundResource(R.drawable.edittext_border_red)
                }
                if (address.isBlank()) {
                    binding.addressEmptyMessage.visibility = View.VISIBLE
                    binding.addressEdit.setBackgroundResource(R.drawable.edittext_border_red)
                }
                if (state.isBlank()) {
                    binding.stateEmptyMessage.visibility = View.VISIBLE
                    binding.stateEdit.setBackgroundResource(R.drawable.edittext_border_red)
                } else {
                    binding.stateEmptyMessage.visibility = View.GONE
                }
                if (zipCode.isBlank()) {
                    binding.zipcodeEmptyMessage.visibility = View.VISIBLE
                    binding.zipCodeEdit.setBackgroundResource(R.drawable.edittext_border_red)
                } else {
                    binding.zipcodeEmptyMessage.visibility = View.GONE
                }
                Toast.makeText(
                    requireContext(),
                    "Please fill in all fields",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }

    private fun scrollToView(view: View) {
        // Scroll to the EditText when it gains focus
        binding.scrollView.post {
            binding.scrollView.smoothScrollTo(0, view.bottom)
        }
    }


}
