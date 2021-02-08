package com.mahesh_prajapati.itunes.data.api

import com.mahesh_prajapati.itunes.storage.model.Songs
import retrofit2.http.GET

interface ApiService {
    @GET("search?term=Michael+jackson&media=musicVideo")
    suspend fun getItunesSongs(): Songs
}