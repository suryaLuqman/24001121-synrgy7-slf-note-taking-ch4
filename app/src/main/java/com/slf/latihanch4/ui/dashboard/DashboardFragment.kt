package com.slf.latihanch4.ui.dashboard

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
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
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.slf.latihanch4.R
import com.slf.latihanch4.data.SharedPreferencesHelper
import com.slf.latihanch4.data.model.Note
import com.slf.latihanch4.databinding.FragmentDashboardBinding
import com.slf.latihanch4.ui.adapter.EditNoteDialogFragment
import com.slf.latihanch4.ui.adapter.NewNoteDialogFragment
import com.slf.latihanch4.ui.adapter.NoteAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DashboardViewModel by viewModels()

    private lateinit var adapter: NoteAdapter

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        viewModel.notes.observe(viewLifecycleOwner) { notes ->
            adapter.submitList(notes)
        }

        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        val username = sharedPreferences.getString("username", "")
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Welcome, $username"

        binding.fab.setOnClickListener {
            val dialog = NewNoteDialogFragment()
            dialog.show(childFragmentManager, "NewNoteDialogFragment")
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            requireActivity().finish()
        }
    }

    private fun setupRecyclerView() {
        adapter = NoteAdapter(object : NoteAdapter.NoteListener {
            override fun onEditClicked(note: Note) {
                val dialog = EditNoteDialogFragment(note)
                dialog.show(childFragmentManager, "EditNoteDialogFragment")
            }

            override fun onDeleteClicked(note: Note) {
                AlertDialog.Builder(requireContext())
                    .setTitle("Delete Note")
                    .setMessage("Are you sure you want to delete this note?")
                    .setPositiveButton("Yes") { _, _ ->
                        viewModel.delete(note)
                    }
                    .setNegativeButton("No", null)
                    .show()
            }
        })
        binding.recyclerViewNotes.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewNotes.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        menu.findItem(R.id.action_settings).setOnMenuItemClickListener {
            logout()
            true
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun logout() {
        SharedPreferencesHelper.setIsLogin(requireContext(), false)
        findNavController().navigate(R.id.action_DashboardFragment_to_LoginFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
