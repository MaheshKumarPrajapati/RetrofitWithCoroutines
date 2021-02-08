package com.mahesh_prajapati.itunes.ui.main.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mahesh_prajapati.itunes.coroutines.R
import com.mahesh_prajapati.itunes.storage.model.Result
import com.mahesh_prajapati.itunes.ui.main.adapter.SongListAdapter.DataViewHolder
import com.mahesh_prajapati.itunes.ui.main.view.DetailsActivity
import com.mahesh_prajapati.itunes.ui.main.viewmodel.MainViewModel
import com.mahesh_prajapati.itunes.utils.AppConstants
import com.mahesh_prajapati.itunes.utils.HelperMethods
import com.mahesh_prajapati.itunes.utils.MyApp
import kotlinx.android.synthetic.main.item_layout.view.*
import java.time.Duration


class SongListAdapter(
    private val songsList: ArrayList<Result>,
    private val context: Context,
    private val viewModel: MainViewModel,
    private val index: Int
) : RecyclerView.Adapter<DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(
            songs: Result,
            context: Context,
            viewModel: MainViewModel,
            index: Int
        ) {
            itemView.apply {
                tvSongName.text = songs.trackName
                tvSongArtist.text = songs.artistName
                tvCollectionName.text = songs.collectionName?:songs.trackName
                Glide.with(ivSongPreview.context)
                    .load(songs.artworkUrl100)
                    .into(ivSongPreview)
            }
            itemView.setOnClickListener {
                //adding to database
                if(index==AppConstants.VIDIO_INDEX){
                    viewModel.setSongToHistory(context,songs)
                }
                MyApp.mCurrentPosition=0
                val intent = Intent(context, DetailsActivity::class.java)
                intent.putExtra(AppConstants.SONG_DATA, songs)
                context.startActivity(intent)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false))

    override fun getItemCount(): Int = songsList.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(songsList[position],context,viewModel,index)
    }

    fun addSongs(songs: List<Result>) {
        this.songsList.apply {
            clear()
            addAll(songs)
        }
    }

    fun removeAt(position: Int) {
        viewModel.removeSongFromHistory(context,this.songsList.get(position))
        this.songsList.removeAt(position)
        notifyItemRemoved(position)
    }

}