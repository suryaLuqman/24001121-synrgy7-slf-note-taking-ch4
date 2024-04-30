package com.slf.latihanch4.ui.login

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import com.slf.latihanch4.R
import com.slf.latihanch4.data.SharedPreferencesHelper
import com.slf.latihanch4.databinding.FragmentLoginBinding
import android.widget.Toast

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inisialisasi SharedPreferences
        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        // Cek apakah pengguna sudah login sebelumnya
        val isUserLoggedIn = SharedPreferencesHelper.getIsLogin(requireContext())
        Log.d("LoginFragment", "Is user logged in: $isUserLoggedIn")

        if (isUserLoggedIn) {
            // Jika sudah login, langsung arahkan ke halaman Dashboard
            findNavController().navigate(R.id.action_LoginFragment_to_SecondFragment)
            return // Keluar dari onViewCreated agar kode di bawahnya tidak dieksekusi
        }

        // Atur toolbar
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.title_activity_login)

        binding.buttonSignin.setOnClickListener {
            val username = binding.username.text.toString()
            val password = binding.password.text.toString()

            if (validateLogin(username, password)) {
                // Panggil metode untuk menyimpan status login ketika berhasil login
                saveLoginStatus(username, password)
                findNavController().navigate(R.id.action_LoginFragment_to_SecondFragment)
            }
        }

        binding.buttonRegister.setOnClickListener {
            findNavController().navigate(R.id.action_LoginFragment_to_registerFragment)
        }
    }

    private fun validateLogin(username: String, password: String): Boolean {
        val storedUsername = sharedPreferences.getString("username", "")
        val storedPassword = sharedPreferences.getString("password", "")

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(requireContext(), "Please enter username and password", Toast.LENGTH_SHORT).show()
            return false
        } else if (username != storedUsername) {
            Toast.makeText(requireContext(), "Username not found", Toast.LENGTH_SHORT).show()
            return false
        } else if (password != storedPassword) {
            Toast.makeText(requireContext(), "Incorrect password", Toast.LENGTH_SHORT).show()
            return false
        }

        // If none of the above conditions are met, then the login is successful
        return true
    }


    private fun saveLoginStatus(username: String, password: String) {
        // Panggil metode untuk menyimpan status login ke SharedPreferences
        SharedPreferencesHelper.setIsLogin(requireContext(), true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
