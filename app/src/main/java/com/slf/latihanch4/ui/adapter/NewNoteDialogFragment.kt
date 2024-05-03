package com.slf.latihanch4.ui.adapter

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.slf.latihanch4.R
import com.slf.latihanch4.data.model.Note
import com.slf.latihanch4.ui.dashboard.DashboardViewModel
import kotlinx.coroutines.launch

class NewNoteDialogFragment : DialogFragment() {

    private val viewModel: DashboardViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.fragment_new_note, null)

            val titleEditText = view.findViewById<EditText>(R.id.TextTitle)
            val contentEditText = view.findViewById<EditText>(R.id.TextContent)

            builder.setView(view) // Menambahkan tampilan ke pembangun dialog

            val dialog = builder.create() // Membuat dialog

            val saveButton = view.findViewById<Button>(R.id.ok_btn_id)
            val cancelButton = view.findViewById<ImageButton>(R.id.cancelID)

            saveButton.setOnClickListener {
                val title = titleEditText.text.toString()
                val content = contentEditText.text.toString()

                // Handle save note action
                lifecycleScope.launch {
                    viewModel.insert(Note(0, title, content))
                }
                Toast.makeText(context, "New note created: $title, $content", Toast.LENGTH_SHORT).show()
                dialog.dismiss() // Menutup dialog setelah menyimpan
            }

            cancelButton.setOnClickListener {
                dialog.dismiss() // Menutup dialog tanpa menyimpan perubahan
            }

            dialog // Mengembalikan dialog yang telah dibuat
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
