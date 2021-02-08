package com.mahesh_prajapati.itunes.storage.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Result(
    @PrimaryKey(autoGenerate = true) val id:Int?,
    @ColumnInfo(name = "artistId") val artistId: Int?,
    @ColumnInfo(name = "artistName") val artistName: String?,
    @ColumnInfo(name = "artistViewUrl") val artistViewUrl: String?,
    @ColumnInfo(name = "artworkUrl100") val artworkUrl100: String?,
    @ColumnInfo(name = "artworkUrl30") val artworkUrl30: String?,
    @ColumnInfo(name = "artworkUrl60") val artworkUrl60: String?,
    @ColumnInfo(name = "collectionCensoredName") val collectionCensoredName: String?,
    @ColumnInfo(name = "collectionExplicitness") val collectionExplicitness: String?,
    @ColumnInfo(name = "collectionId") val collectionId: Int?,
    @ColumnInfo(name = "collectionName") val collectionName: String?,
    @ColumnInfo(name = "collectionPrice") val collectionPrice: Double?,
    @ColumnInfo(name = "collectionViewUrl") val collectionViewUrl: String?,
    @ColumnInfo(name = "country") val country: String?,
    @ColumnInfo(name = "currency") val currency: String?,
    @ColumnInfo(name = "discCount") val discCount: Int?,
    @ColumnInfo(name = "discNumber") val discNumber: Int?,
    @ColumnInfo(name = "kind") val kind: String?,
    @ColumnInfo(name = "previewUrl") val previewUrl: String?,
    @ColumnInfo(name = "primaryGenreName") val primaryGenreName: String?,
    @ColumnInfo(name = "releaseDate") val releaseDate: String?,
    @ColumnInfo(name = "trackCensoredName") val trackCensoredName: String?,
    @ColumnInfo(name = "trackCount") val trackCount: Int?,
    @ColumnInfo(name = "trackExplicitness") val trackExplicitness: String?,
    @ColumnInfo(name = "trackId") val trackId: Int?,
    @ColumnInfo(name = "trackName") val trackName: String?,
    @ColumnInfo(name = "trackNumber") val trackNumber: Int?,
    @ColumnInfo(name = "trackPrice") val trackPrice: Double?,
    @ColumnInfo(name = "trackTimeMillis") val trackTimeMillis: Long?,
    @ColumnInfo(name = "trackViewUrl") val trackViewUrl: String?,
    @ColumnInfo(name = "wrapperType") val wrapperType: String?
): Parcelable