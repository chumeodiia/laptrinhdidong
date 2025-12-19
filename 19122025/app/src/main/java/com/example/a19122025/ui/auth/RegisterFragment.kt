package com.example.a19122025.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.a19122025.R
import com.example.a19122025.databinding.FragmentRegisterBinding
import com.example.a19122025.viewmodel.AuthViewModel

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
        viewModel.registrationStatus.observe(viewLifecycleOwner, Observer { success ->
            success?.let {
                if (it) {
                    Toast.makeText(context, "Registration successful", Toast.LENGTH_SHORT).show()
                    // Điều hướng bằng fragment ID thay vì action ID
                    findNavController().navigate(R.id.loginFragment)
                } else {
                    Toast.makeText(context, "Registration failed", Toast.LENGTH_SHORT).show()
                }
                viewModel.clearStatus()
            }
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupListeners() {
        binding.btnRegister.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString()
            val confirmPassword = binding.etConfirmPassword.text.toString()

            if (password != confirmPassword) {
                Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.register(username, email, password)
        }

        binding.tvLogin.setOnClickListener {
            // Điều hướng bằng fragment ID
            findNavController().navigate(R.id.loginFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}