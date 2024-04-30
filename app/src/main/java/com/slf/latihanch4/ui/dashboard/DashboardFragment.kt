package com.slf.latihanch4.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.slf.latihanch4.R
import com.slf.latihanch4.data.SharedPreferencesHelper
import com.slf.latihanch4.data.model.Note
import com.slf.latihanch4.databinding.FragmentDashboardBinding
import com.slf.latihanch4.ui.adapter.NoteAdapter

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    // Dummy list of notes (you should replace this with your actual data source)
    private val notes = mutableListOf<Note>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true) // Ensure that the options menu is created
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup RecyclerView
        val recyclerView = binding.recyclerViewNotes
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = NoteAdapter(notes, object : NoteAdapter.NoteListener {
            override fun onEditClicked(note: Note) {
                // Handle edit action
                Log.d("DashboardFragment", "Note edited: $note")
            }

            override fun onDeleteClicked(note: Note) {
                // Handle delete action
                Log.d("DashboardFragment", "Note deleted: $note")
            }
        })

        // Load notes (replace this with your actual logic to fetch notes)
        loadNotes()

        // Atur toolbar
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.title_activity_dashboard)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setAnchorView(R.id.fab).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // Inflate the menu
        inflater.inflate(R.menu.menu_main, menu)
        menu.findItem(R.id.action_settings).setOnMenuItemClickListener {
            // Panggil metode untuk logout dan hapus data login dari SharedPreferences
            logout()
            //findNavController().navigate(R.id.action_DashboardFragment_to_LoginFragment)
            true
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun logout() {
        // Panggil metode untuk menghapus data login dari SharedPreferences
        SharedPreferencesHelper.setIsLogin(requireContext(), false)
        // Navigasi ke halaman login
        findNavController().navigate(R.id.action_DashboardFragment_to_LoginFragment)
    }

    private fun loadNotes() {
        // Here you should fetch your notes from your data source
        // For example, you can add some dummy data like this:
        notes.add(Note(1, "title 1", "Content 1"))
        notes.add(Note(2, "title 2", "Content 2"))
        notes.add(Note(3, "title 3", "Content 3"))
        // Notify the adapter that the data set has changed
        binding.recyclerViewNotes.adapter?.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
