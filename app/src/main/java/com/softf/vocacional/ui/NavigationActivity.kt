package com.softf.vocacional.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.softf.vocacional.R
import kotlinx.android.synthetic.main.activity_navigation.*

class NavigationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        initializeActionBar()
    }

    private fun initializeActionBar() {
        setSupportActionBar(appBar)
        supportActionBar?.hide()
    }
}
