package com.mahesh_prajapati.itunes.ui.main.viewmodel

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.mahesh_prajapati.itunes.data.repository.MainRepository
import com.mahesh_prajapati.itunes.storage.DatabaseClient
import com.mahesh_prajapati.itunes.storage.model.Result
import com.mahesh_prajapati.itunes.utils.AppConstants
import com.mahesh_prajapati.itunes.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    var songsList: ArrayList<Result>? = null

    fun getSongList() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.getItunesSongs().results))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun setSongToHistory(context: Context, songs: Result) {
        GlobalScope.launch {
            if (songsList == null) {
                songsList = DatabaseClient.getInstance(context)!!.appDatabase.songDao()!!
                    .getAll() as ArrayList<Result>
            }
            if (!songsList!!.any { it -> it.trackName == songs.trackName }) {
                songsList!!.add(songs)
                DatabaseClient.getInstance(context)!!.appDatabase
                    .songDao()!!.insert(songs)
            }
        }

    }

    fun getSongFromHistory(context: Context) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            songsList = DatabaseClient.getInstance(context)!!.appDatabase.songDao()!!
                .getAll() as ArrayList<Result>
            emit(
                Resource.success(
                    data = DatabaseClient.getInstance(context)!!.appDatabase.songDao()!!.getAll()
                )
            )
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }


    fun removeSongFromHistory(
        context: Context,
        song: Result
    ) {
        GlobalScope.launch {
            DatabaseClient.getInstance(context)!!.appDatabase
                .songDao()!!.delete(song)
        }
    }



    fun getSongDetails(intent: Intent)= liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = intent.extras!!.getParcelable<Result>(AppConstants.SONG_DATA)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}