package com.slf.latihanch4.ui.adapter

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.slf.latihanch4.R
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.slf.latihanch4.data.model.Note
import com.slf.latihanch4.ui.dashboard.DashboardViewModel
import kotlinx.coroutines.launch

class EditNoteDialogFragment(private val note: Note) : DialogFragment() {

    private val viewModel: DashboardViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater;
            val view = inflater.inflate(R.layout.fragment_edit_note, null)

            val titleEditText = view.findViewById<EditText>(R.id.editTextTitle)
            val contentEditText = view.findViewById<EditText>(R.id.editTextContent)

            titleEditText.setText(note.title)
            contentEditText.setText(note.content)

            builder.setView(view)
                .setPositiveButton("Save") { _, _ ->
                    val title = titleEditText.text.toString()
                    val content = contentEditText.text.toString()

                    // Handle save note action
                    lifecycleScope.launch {
                        viewModel.update(Note(note.id, title, content))
                    }
                    Toast.makeText(context, "Note saved: $title, $content", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Cancel") { _, _ ->
                    dialog?.cancel()
                }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}

