package com.backbase.assignment.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.backbase.assignment.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar()
    }

    private fun setToolbar() {
        setSupportActionBar(binding.toolbar.toolbarSingleTitle)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    fun enableHomeUpButton(enable: Boolean) = supportActionBar?.setDisplayHomeAsUpEnabled(enable)

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            super.onBackPressed()
            true
        } else {
            false
        }
    }
}
