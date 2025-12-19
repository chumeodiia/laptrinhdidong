package com.example.a19122025.ui.auth

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.a19122025.R
import com.example.a19122025.databinding.FragmentLoginBinding
import com.example.a19122025.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthViewModel by viewModels()

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
        viewModel.loginStatus.observe(viewLifecycleOwner, Observer { user ->
            user?.let {
                // Save user session
                sharedPreferences.edit {
                    putInt("user_id", user.id)
                    putString("user_email", user.email)
                    putString("user_name", user.username)
                }

                Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.taskListFragment)
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
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString()

            viewModel.login(email, password)
        }

        binding.tvRegister.setOnClickListener {
            findNavController().navigate(R.id.registerFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}