package com.example.kotlinnotesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.kotlinnotesapp.databinding.ActivityUpdateNoteBinding

class UpdateNoteActivity : AppCompatActivity() {
    private lateinit var binding:ActivityUpdateNoteBinding
    private lateinit var db:DatabaseHelper
    private var noteID :Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DatabaseHelper(this)

        noteID = intent.getIntExtra("note_id",-1)

        if(noteID == -1){
            finish()
            return
        }

        val note = db.getNoteByID(noteID)
        binding.etUpdateTittle.setText(note.tittle)
        binding.etUpdateContent.setText(note.content)

        binding.ivUpdateDone.setOnClickListener {
            val newTittle = binding.etUpdateTittle.text.toString()
            val newContent = binding.etUpdateContent.text.toString()
            val updateNote = NotesData(noteID,newTittle,newContent)

            db.updateData(updateNote)
            finish()
            Toast.makeText(this@UpdateNoteActivity,"Changes Saved!",Toast.LENGTH_LONG).show()

        }
    }
}