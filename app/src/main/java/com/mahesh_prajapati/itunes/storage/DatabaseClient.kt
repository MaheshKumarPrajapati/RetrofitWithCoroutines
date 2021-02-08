package com.mahesh_prajapati.itunes.storage
import android.content.Context
import androidx.room.Room
import com.mahesh_prajapati.itunes.coroutines.R
import kotlinx.android.synthetic.main.activity_details.view.*


class DatabaseClient private constructor(private val mCtx: Context) {

    val appDatabase: AppDatabase =
        Room.databaseBuilder(mCtx, AppDatabase::class.java, "TaskAndroid").build()

    companion object {
        private var mInstance: DatabaseClient? = null

        @Synchronized
        fun getInstance(mCtx: Context): DatabaseClient? {
            if (mInstance == null) {
                mInstance = DatabaseClient(mCtx)
            }
            return mInstance
        }
    }

}