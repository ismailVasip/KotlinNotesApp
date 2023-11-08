package com.example.kotlinnotesapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.ContactsContract.CommonDataKinds.Note

class DatabaseHelper(context:Context): SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {

    companion object{
        private const val DATABASE_NAME = "notesapp.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "allnotes"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITTLE = "tittle"
        private const val COLUMN_CONTENT = "content"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable= "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_TITTLE TEXT, $COLUMN_CONTENT TEXT)"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        val dropTabQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTabQuery)
        onCreate(db)
    }

    fun insertNote(note: NotesData) {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_TITTLE,note.tittle)
            put(COLUMN_CONTENT,note.content)
        }
        db.insert(TABLE_NAME,null,contentValues)
        db.close()
    }

    fun readData() :List<NotesData> {
        val list = mutableListOf<NotesData>()
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query,null)

        while(cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val tittle = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITTLE))
            val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))

            val note = NotesData(id,tittle,content)
            list.add(note)
        }
        cursor.close()
        db.close()

        return list
    }

    fun updateData(note :NotesData){
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITTLE,note.tittle)
            put(COLUMN_CONTENT,note.content)
        }

        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(note.id.toString())

        db.update(TABLE_NAME,values,whereClause,whereArgs)
        db.close()
    }

    fun getNoteByID(noteID :Int):NotesData{
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $noteID"
        val cursor = db.rawQuery(query,null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val tittle = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITTLE))
        val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))

        cursor.close()
        db.close()
        return NotesData(id,tittle,content)
    }

    fun deleteData(note_id:Int){
        val db = this.writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(note_id.toString())
        db.delete(TABLE_NAME,whereClause,whereArgs)

        db.close()
    }

}