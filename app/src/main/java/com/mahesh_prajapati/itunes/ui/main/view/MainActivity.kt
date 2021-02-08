package com.mahesh_prajapati.itunes.ui.main.view
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mahesh_prajapati.itunes.coroutines.R.layout
import com.mahesh_prajapati.itunes.ui.main.adapter.SongTabPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)

        val songTabPagerAdapter = SongTabPagerAdapter(this, supportFragmentManager)
        viewPager.adapter = songTabPagerAdapter
        tabs.setupWithViewPager(viewPager)
    }

}
