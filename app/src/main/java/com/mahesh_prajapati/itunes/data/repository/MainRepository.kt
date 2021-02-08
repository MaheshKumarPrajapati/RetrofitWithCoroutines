package com.mahesh_prajapati.itunes.data.repository

import com.mahesh_prajapati.itunes.data.api.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {
    suspend fun getItunesSongs() = apiHelper.getItunesSongs()
}