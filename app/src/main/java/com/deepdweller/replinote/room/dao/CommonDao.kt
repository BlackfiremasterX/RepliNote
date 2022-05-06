package com.deepdweller.replinote.room.dao

import androidx.room.Dao
import androidx.room.Transaction
import com.deepdweller.replinote.room.ReplinoteDatabase

@Dao
abstract class CommonDao(private val database: ReplinoteDatabase) {

    @Transaction
    open suspend fun deleteLabel(value: String) {
        val baseNotesWithLabel = database.baseNoteDao.getListOfBaseNotesByLabel(value)
        val modified = baseNotesWithLabel.map { baseNote ->
            baseNote.labels.remove(value)
            baseNote
        }
        database.baseNoteDao.update(modified)
        database.labelDao.delete(value)
    }

    @Transaction
    open suspend fun updateLabel(oldValue: String, newValue: String) {
        val baseNotesWithLabel = database.baseNoteDao.getListOfBaseNotesByLabel(oldValue)
        val modified = baseNotesWithLabel.map { baseNote ->
            baseNote.labels.remove(oldValue)
            baseNote.labels.add(newValue)
            baseNote
        }
        database.baseNoteDao.update(modified)
        database.labelDao.update(oldValue, newValue)
    }
}