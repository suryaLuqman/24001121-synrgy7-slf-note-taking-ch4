package com.slf.latihanch4.ui.dashboard

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.slf.latihanch4.R
import com.slf.latihanch4.data.SharedPreferencesHelper
import com.slf.latihanch4.data.model.Note
import com.slf.latihanch4.databinding.FragmentDashboardBinding
import com.slf.latihanch4.ui.adapter.EditNoteDialogFragment
import com.slf.latihanch4.ui.adapter.NewNoteDialogFragment
import com.slf.latihanch4.ui.adapter.NoteAdapter
import kotlinx.coroutines.launch

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DashboardViewModel by viewModels()

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
        recyclerView.adapter = NoteAdapter(object : NoteAdapter.NoteListener {
            override fun onEditClicked(note: Note) {
                // Handle edit action
                Log.d("DashboardFragment", "Note edited: $note")
                val dialog = EditNoteDialogFragment(note)
                dialog.show(childFragmentManager, "EditNoteDialogFragment")
            }

            override fun onDeleteClicked(note: Note) {
                // Handle delete action
                Log.d("DashboardFragment", "Note deleted: $note")
                AlertDialog.Builder(requireContext())
                    .setTitle("Delete Note")
                    .setMessage("Are you sure you want to delete this note?")
                    .setPositiveButton("Yes") { _, _ ->
                        lifecycleScope.launch {
                            viewModel.delete(note)
                        }
                    }
                    .setNegativeButton("No", null)
                    .show()
            }
        })

        // Observe the notes LiveData from the ViewModel
//        viewModel.notes.observe(viewLifecycleOwner) { notes ->
//            // Update the RecyclerView adapter
//            (binding.recyclerViewNotes.adapter as NoteAdapter).submitList(notes)
//        }
        // Observe the notes LiveData from the ViewModel
        viewModel.notes.observe(viewLifecycleOwner) { notes ->
            // Update the RecyclerView adapter
            val adapter = binding.recyclerViewNotes.adapter as NoteAdapter
            adapter.submitList(notes)
            adapter.notifyDataSetChanged() // Force RecyclerView to update
        }


        // Atur toolbar
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.title_activity_dashboard)

        binding.fab.setOnClickListener {
            val dialog = NewNoteDialogFragment()
            dialog.show(childFragmentManager, "NewNoteDialogFragment")
        }

        // Handle back button press
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            requireActivity().finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // Inflate the menu
        inflater.inflate(R.menu.menu_main, menu)
        menu.findItem(R.id.action_settings).setOnMenuItemClickListener {
            // Panggil metode untuk logout dan hapus data login dari SharedPreferences
            logout()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
