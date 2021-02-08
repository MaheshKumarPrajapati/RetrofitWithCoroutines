package com.mahesh_prajapati.itunes.data.api

class ApiHelper(private val apiService: ApiService) {
    suspend fun getItunesSongs() = apiService.getItunesSongs()
}