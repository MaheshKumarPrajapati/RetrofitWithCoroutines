package com.mahesh_prajapati.itunes.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mahesh_prajapati.itunes.coroutines.R
import com.mahesh_prajapati.itunes.data.api.ApiHelper
import com.mahesh_prajapati.itunes.data.api.RetrofitBuilder
import com.mahesh_prajapati.itunes.storage.model.Result
import com.mahesh_prajapati.itunes.ui.base.ViewModelFactory
import com.mahesh_prajapati.itunes.ui.main.adapter.SongListAdapter
import com.mahesh_prajapati.itunes.ui.main.viewmodel.MainViewModel
import com.mahesh_prajapati.itunes.utils.AppConstants
import com.mahesh_prajapati.itunes.utils.Status
import com.mahesh_prajapati.itunes.utils.SwipeToDeleteCallback
import kotlinx.android.synthetic.main.fragment_songs.*

class SongsFragments : Fragment() {
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: SongListAdapter
    private var index: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        index = arguments!!.getInt(AppConstants.FRAG_INDEX)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_songs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (index == AppConstants.VIDIO_INDEX) {
            setUpData()
        }
    }

    override fun onResume() {
        super.onResume()
        if (index == AppConstants.HISTORY_INDEX) {
            setUpData()
        }
    }

    private fun setUpData() {
        setupViewModel()
        setupUI()
        setupObservers()
    }


    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(MainViewModel::class.java)
    }

    private fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = SongListAdapter(arrayListOf(), activity!!, viewModel, index)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter


        if (index == AppConstants.HISTORY_INDEX) {
            val swipeHandler = object : SwipeToDeleteCallback(activity!!) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val adapter = recyclerView.adapter as SongListAdapter
                    adapter.removeAt(viewHolder.adapterPosition)
                }
            }
            val itemTouchHelper = ItemTouchHelper(swipeHandler)
            itemTouchHelper.attachToRecyclerView(recyclerView)
        }

    }

    private fun setupObservers() {
        if (index == AppConstants.VIDIO_INDEX) {
            viewModel.getSongList()
        } else {
            viewModel.getSongFromHistory(activity!!)
        }
            .observe(this, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            recyclerView.visibility = View.VISIBLE
                            progressBar.visibility = View.GONE
                            resource.data?.let { songs -> retrieveList(songs = songs) }
                        }
                        Status.ERROR -> {
                            recyclerView.visibility = View.VISIBLE
                            progressBar.visibility = View.GONE
                            Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                        }
                        Status.LOADING -> {
                            progressBar.visibility = View.VISIBLE
                            recyclerView.visibility = View.GONE
                        }
                    }
                }
            })
    }

    private fun retrieveList(songs: List<Result>) {
        adapter.apply {
            addSongs(songs)
            notifyDataSetChanged()
        }
    }

    companion object {
        fun newInstance(index: Int): SongsFragments {
            return SongsFragments().apply {
                arguments = Bundle().apply {
                    putInt(AppConstants.FRAG_INDEX, index)
                }
            }
        }
    }

}