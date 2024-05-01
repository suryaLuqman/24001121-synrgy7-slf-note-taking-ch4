import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.slf.latihanch4.R
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast

class EditNoteDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater;
            val view = inflater.inflate(R.layout.fragment_edit_note, null)

            builder.setView(view)
                .setPositiveButton("Save") { _, _ ->
                    val title = view.findViewById<EditText>(R.id.editTextTitle).text.toString()
                    val content = view.findViewById<EditText>(R.id.editTextContent).text.toString()

                    // Handle save note action
                    // You need to replace this with your actual logic to save the edited note
                    Toast.makeText(context, "Note saved: $title, $content", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Cancel") { _, _ ->
                    dialog?.cancel()
                }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
