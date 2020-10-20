package com.amwa.noteapp.data.db.dao

import androidx.room.*
import com.amwa.noteapp.data.db.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM Note ORDER BY id DESC")
    fun getAll(): Flow<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Query("SELECT * FROM Note WHERE id = :id")
    suspend fun get(id: Int): Note

    @Delete
    suspend fun delete(note: Note)
}