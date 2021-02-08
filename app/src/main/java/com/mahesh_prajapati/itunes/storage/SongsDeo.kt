package com.mahesh_prajapati.itunes.storage

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.mahesh_prajapati.itunes.storage.model.Result

@Dao
interface SongsDeo {
    @Query("SELECT * FROM result")
    suspend fun getAll(): List<Result>

    @Insert
    suspend fun insert(song: Result)

    @Delete
    suspend fun delete(song: Result)
}