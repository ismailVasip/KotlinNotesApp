package com.example.kotlinnotesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.kotlinnotesapp.databinding.ActivityAddNoteBinding

class AddNoteActivity : AppCompatActivity() {
    private lateinit var binding:ActivityAddNoteBinding
    private lateinit var db:DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DatabaseHelper(this)

        binding.ivDone.setOnClickListener {
            val tittle = binding.etTittle.text.toString()
            val content = binding.etContent.text.toString()

            val note = NotesData(0,tittle,content)

            db.insertNote(note)
            finish()
            Toast.makeText(this,"Note Saved",Toast.LENGTH_LONG).show()
        }
    }
}