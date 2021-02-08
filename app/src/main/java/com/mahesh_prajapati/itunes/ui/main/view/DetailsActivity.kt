package com.mahesh_prajapati.itunes.ui.main.view

import android.media.MediaPlayer.OnCompletionListener
import android.media.MediaPlayer.OnPreparedListener
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.webkit.URLUtil
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.mahesh_prajapati.itunes.coroutines.R
import com.mahesh_prajapati.itunes.data.api.ApiHelper
import com.mahesh_prajapati.itunes.data.api.RetrofitBuilder
import com.mahesh_prajapati.itunes.storage.model.Result
import com.mahesh_prajapati.itunes.ui.base.ViewModelFactory
import com.mahesh_prajapati.itunes.ui.main.viewmodel.MainViewModel
import com.mahesh_prajapati.itunes.utils.HelperMethods
import com.mahesh_prajapati.itunes.utils.MyApp
import com.mahesh_prajapati.itunes.utils.Status
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_details.progressBar
import kotlinx.android.synthetic.main.content_details.*
import kotlinx.android.synthetic.main.item_layout.view.*


class DetailsActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    private val PLAYBACK_TIME = "play_time"
    var data: Result? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressed() }
        setUpData()
    }

    private fun setUpData() {
        setupViewModel()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.getSongDetails(intent).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { songs -> SetUpUI(songs = songs) }
                    }
                    Status.ERROR -> {
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {

                    }
                }
            }
        })
    }

    private fun SetUpUI(songs: Result) {
        data = songs

        tvSongName.text = getString(R.string.song_name) + " " + songs.trackName
        tvSongArtist.text = getString(R.string.artist) + " " + songs.artistName
        tvCollectionName.text =
            getString(R.string.collection) + " " + songs.collectionName ?: songs.trackName
        Glide.with(ivSongPreview.context)
            .load(songs.artworkUrl100)
            .into(ivSongPreview)

        tvSongDuration.text =
            getString(R.string.duration) + "\n\n" + HelperMethods().milliSecondToDuration(songs.trackTimeMillis!!)
        tvCollectionPrice.text =
            getString(R.string.collection_price) + "\n\n" + songs.collectionPrice + " " + songs.currency
        tvSongTrackPrice.text =
            getString(R.string.track_price) + "\n\n" + songs.trackPrice + " " + songs.currency
        tvSongReleaseDate.text =
            getString(R.string.relesed_on) + " " + HelperMethods().parseDate(songs.releaseDate!!)

        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = data!!.trackName


        val controller = MediaController(this)
        controller.setMediaPlayer(videoView)
        videoView.setMediaController(controller)
        initializePlayer()

    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(MainViewModel::class.java)
    }


    override fun onPause() {
        super.onPause()
        MyApp.mCurrentPosition = videoView.currentPosition

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            videoView.pause()
        }
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(PLAYBACK_TIME, videoView.currentPosition)
    }

    private fun initializePlayer() {
        val videoUri: Uri = getMedia(data!!.previewUrl!!)
        videoView.setVideoURI(videoUri)
        videoView.setOnPreparedListener(
            OnPreparedListener {
                progressBar.visibility = VideoView.INVISIBLE
                if (MyApp.mCurrentPosition > 0) {
                    videoView.seekTo(MyApp.mCurrentPosition)
                } else {
                    videoView.seekTo(1)
                }
                videoView.start()
            })
        videoView.setOnCompletionListener(
            OnCompletionListener {
                videoView.seekTo(0)
                MyApp.mCurrentPosition = 0
                videoView.start()
            })

    }

    private fun releasePlayer() {
        videoView.stopPlayback()
    }

    private fun getMedia(mediaName: String): Uri {
        return if (URLUtil.isValidUrl(mediaName)) {
            Uri.parse(mediaName)
        } else {
            Uri.parse(
                "android.resource://" + packageName +
                        "/raw/" + mediaName
            )
        }
    }

    override fun onBackPressed() {
        finish()
    }

}