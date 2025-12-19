package com.example.a19122025.ui.tasks

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a19122025.R
import com.example.a19122025.TodoApplication
import com.example.a19122025.databinding.FragmentTaskListBinding
import com.example.a19122025.viewmodel.TaskViewModel
import com.example.a19122025.viewmodel.TaskViewModelFactory

class TaskListFragment : Fragment() {

    private var _binding: FragmentTaskListBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: TaskViewModel
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize SharedPreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())

        // Get user ID from SharedPreferences
        val userId = sharedPreferences.getInt("user_id", -1)
        if (userId == -1) {
            // Nếu chưa đăng nhập, quay lại màn hình login
            Toast.makeText(context, "Please login first", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.loginFragment)
            return
        }

        // Initialize ViewModel with Factory
        val repository = (requireActivity().application as TodoApplication).repository
        viewModel = ViewModelProvider(
            this,
            TaskViewModelFactory(repository, userId)
        ).get(TaskViewModel::class.java)  // Dùng .get() thay vì []

        setupRecyclerView()
        setupObservers()
        setupListeners()
    }

    private fun setupRecyclerView() {
        taskAdapter = TaskAdapter(
            onTaskClick = { task ->
                // Handle task click (edit/view details)
            },
            onTaskChecked = { task, isChecked ->
                viewModel.toggleTaskCompletion(task)
            }
        )

        binding.rvTasks.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = taskAdapter
        }
    }

    private fun setupObservers() {
        // Sửa Observer với kiểu rõ ràng
        viewModel.tasks.observe(viewLifecycleOwner, Observer<List<com.example.a19122025.data.model.Task>> { tasks ->
            taskAdapter.submitList(tasks)
            binding.tvEmpty.visibility = if (tasks.isEmpty()) View.VISIBLE else View.GONE
        })

        viewModel.taskOperationStatus.observe(viewLifecycleOwner, Observer<Boolean?> { success ->
            success?.let {
                if (it) {
                    Toast.makeText(context, "Operation successful", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show()
                }
                viewModel.clearStatus()
            }
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer<String?> { error ->
            error?.let {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupListeners() {
        binding.fabAddTask.setOnClickListener {
            findNavController().navigate(R.id.addTaskFragment)
        }

        binding.btnLogout.setOnClickListener {
            // Clear session
            sharedPreferences.edit().clear().apply()
            // Navigate to login
            findNavController().navigate(R.id.loginFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}