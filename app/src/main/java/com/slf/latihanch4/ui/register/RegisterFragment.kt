package com.slf.latihanch4.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.slf.latihanch4.databinding.FragmentRegisterBinding
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.slf.latihanch4.R
import android.widget.Toast
import android.content.Context
import android.content.SharedPreferences
import com.slf.latihanch4.data.SharedPreferencesHelper

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inisialisasi SharedPreferences
        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        // Atur toolbar
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.title_activity_register)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_LoginFragment)
        }

        binding.buttonRegister.setOnClickListener {
            val username = binding.username.text.toString()
            val email = binding.registerEmail.text.toString()
            val password = binding.password.text.toString()
            val confirmPassword = binding.confirmPassword.text.toString()

            if (validateRegister(username, email, password, confirmPassword)) {
                // Simpan data user ke SharedPreferences
                saveUserData(username, email, password)
                findNavController().navigate(R.id.action_registerFragment_to_LoginFragment)
            }
        }
    }

    private fun validateRegister(username: String, email: String, password: String, confirmPassword: String): Boolean {
        // Validasi email
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(requireContext(), "Invalid email", Toast.LENGTH_SHORT).show()
            return false
        }

        // Validasi password
        if (password.length < 8) {
            Toast.makeText(requireContext(), "Password should be at least 8 characters", Toast.LENGTH_SHORT).show()
            return false
        }

        // Validasi konfirmasi password
        if (password != confirmPassword) {
            Toast.makeText(requireContext(), "Passwords do not match", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun saveUserData(username: String, email: String, password: String) {
        val editor = sharedPreferences.edit()
        editor.putString("username", username)
        editor.putString("email", email)
        editor.putString("password", password)
        editor.apply()

        Toast.makeText(requireContext(), "User registered successfully", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
