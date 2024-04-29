package com.slf.latihanch4.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.slf.latihanch4.data.model.Note
import com.slf.latihanch4.databinding.NoteItemBinding

class NoteAdapter(private val notes: List<Note>, private val listener: NoteListener) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = NoteItemBinding.inflate(inflater, parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.bind(note)
    }

    override fun getItemCount(): Int = notes.size

    inner class NoteViewHolder(private val binding: NoteItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            binding.textTitle.text = note.title
            binding.textContent.text = note.content

            binding.buttonEdit.setOnClickListener {
                listener.onEditClicked(note)
            }

            binding.buttonDelete.setOnClickListener {
                listener.onDeleteClicked(note)
            }
        }
    }

    interface NoteListener {
        fun onEditClicked(note: Note)
        fun onDeleteClicked(note: Note)
    }
}
