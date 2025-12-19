package com.example.a19122025.ui.tasks


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import com.example.a19122025.R
import com.example.a19122025.TodoApplication
import com.example.a19122025.databinding.FragmentAddTaskBinding
import com.example.a19122025.viewmodel.TaskViewModel
import com.example.a19122025.viewmodel.TaskViewModelFactory

class AddTaskFragment : Fragment() {

    private var _binding: FragmentAddTaskBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: TaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get user ID from SharedPreferences
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val userId = sharedPreferences.getInt("user_id", -1)

        // Initialize ViewModel
        val repository = (requireActivity().application as TodoApplication).repository
        viewModel = ViewModelProvider(
            this,
            TaskViewModelFactory(repository, userId)
        )[TaskViewModel::class.java]

        setupListeners()
        setupObservers()
    }

    private fun setupListeners() {
        binding.btnSaveTask.setOnClickListener {
            val title = binding.etTitle.text.toString().trim()
            val description = binding.etDescription.text.toString().trim()

            if (title.isEmpty()) {
                Toast.makeText(context, "Please enter task title", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.addTask(title, description, null, 1)
        }

        binding.btnCancel.setOnClickListener {
            findNavController().navigate(R.id.taskListFragment)
        }
    }

    private fun setupObservers() {
        viewModel.taskOperationStatus.observe(viewLifecycleOwner) { success ->
            success?.let {
                if (it) {
                    Toast.makeText(context, "Task added successfully", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.taskListFragment)
                } else {
                    Toast.makeText(context, "Failed to add task", Toast.LENGTH_SHORT).show()
                }
                viewModel.clearStatus()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}