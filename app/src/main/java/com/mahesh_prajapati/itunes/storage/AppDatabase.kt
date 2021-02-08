package com.mahesh_prajapati.itunes.storage


import androidx.room.Database
import androidx.room.RoomDatabase
import com.mahesh_prajapati.itunes.storage.model.Result


@Database(entities = [Result::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun songDao(): SongsDeo?
}