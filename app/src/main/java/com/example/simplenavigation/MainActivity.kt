package com.example.simplenavigation

import android.content.SharedPreferences
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.simplenavigation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        val viewModel: SharedViewModel by viewModels()

        val sharedPref = this.getPreferences(MODE_PRIVATE)
        val preferredColor = sharedPref.getInt("color", -1) //-1 is the default value.
        val preferredMode = sharedPref.getInt("mode", -1) //-1 is the default value.
        if (preferredColor != -1) {
            viewModel.themeColor.postValue(preferredColor)
            Log.d("debugg", "ThemeColor set to: $preferredColor")
        }
        if (preferredMode != -1) {
            AppCompatDelegate.setDefaultNightMode(preferredMode)
            Log.d("debugg", "ThemeMode set to: $preferredMode")
        }



        viewModel.themeColor.observe(this, Observer { color ->
            if (color != -1) {
                this.window?.statusBarColor = color
                this.window?.navigationBarColor = color
                val colorDrawable = ColorDrawable(color)
                this.supportActionBar?.setBackgroundDrawable(colorDrawable)
            }
        })

        setupActionBarWithNavController(this.findNavController(R.id.fragmentContainerView))


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(
            item,
            findNavController(R.id.fragmentContainerView)
        ) || super.onOptionsItemSelected(item)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.overflow_menu, menu)
        return true
    }
}