package com.example.kotlinnotesapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinnotesapp.databinding.ItemRowBinding

class NotesAdapter(private var list:List<NotesData>,context: Context): RecyclerView.Adapter<NotesAdapter.ViewHolderNote>()  {

    private val db :DatabaseHelper = DatabaseHelper(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderNote {
        val binding = ItemRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolderNote(binding)
    }

    override fun onBindViewHolder(holder: ViewHolderNote, position: Int) {
        val note = list[position]
        holder.binding.tvTittle.text = note.tittle
        holder.binding.tvContent.text = note.content

        holder.binding.ivUpdateButton.setOnClickListener{
            val intent = Intent(holder.itemView.context,UpdateNoteActivity::class.java).apply {
                putExtra("note_id",note.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.binding.ivDeleteButton.setOnClickListener {
            db.deleteData(note.id)
            refreshData(db.readData())
            Toast.makeText(holder.itemView.context,"Note Deleted!",Toast.LENGTH_LONG).show()
        }
    }

    fun refreshData(newList :List<NotesData>){
        list = newList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size

    class ViewHolderNote(val binding: ItemRowBinding) :RecyclerView.ViewHolder(binding.root){}
}