package com.deepdweller.replinote.room

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.deepdweller.replinote.room.dao.BaseNoteDao
import com.deepdweller.replinote.room.dao.CommonDao
import com.deepdweller.replinote.room.dao.LabelDao

@TypeConverters(Converters::class)
@Database(entities = [BaseNote::class, Label::class], version = 1)
abstract class ReplinoteDatabase : RoomDatabase() {

    abstract val labelDao: LabelDao
    abstract val commonDao: CommonDao
    abstract val baseNoteDao: BaseNoteDao

    companion object {

        private const val databaseName = "NotallyDatabase"

        @Volatile
        private var instance: ReplinoteDatabase? = null

        fun getDatabase(application: Application): ReplinoteDatabase {
            return instance ?: synchronized(this) {
                val instance = Room.databaseBuilder(application, ReplinoteDatabase::class.java, databaseName).build()
                this.instance = instance
                return instance
            }
        }
    }
}