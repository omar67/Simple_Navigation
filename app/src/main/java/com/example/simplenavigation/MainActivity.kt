package com.example.simplenavigation

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.simplenavigation.databinding.ActivityMainBinding
import java.util.*


class MainActivity : AppCompatActivity() {

    private var isFirstTime = true
    private lateinit var navController: NavController
    private val viewModel: SharedViewModel by viewModels()
    lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        Log.d("lifecycle", "onCreate at MainActivity is called")
        setupChangeListeners()
        navController = this.findNavController(R.id.fragmentContainerView)
        setupActionBarWithNavController(navController)
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshPhones()
        Log.d("lifecycle", "onResume at MainActivity is called")
    }

    override fun onStart() {
        super.onStart()
        Log.d("lifecycle", "onStart at MainActivity is called")
    }

    override fun attachBaseContext(newBase: Context?) {

        super.attachBaseContext(LocaleHelper.onAttach(newBase!!))
    }

    //    setup change listeners that observe changes and update UI
    private fun setupChangeListeners() {
        handleThemeColorChange()
        handleThemeModeChange()
        handleLanguageChange()
    }

    //        observe theme color and update action bar and status bar and navigation bar
    private fun handleThemeColorChange() {
        viewModel.themeColor.observe(this, { color ->
            if (color != -1) {
                updateStatusBarColor(color)

                updateActionBarColor(color)
            }
        })
    }

    //        observe and update display mode
    private fun handleThemeModeChange() {
        viewModel.themeMode.observe(this, { mode ->
            AppCompatDelegate.setDefaultNightMode(mode)
        })
    }

    //  Restart activity to apply changes
    private fun handleLanguageChange() {
        viewModel.lang.observe(this, { code ->
            if (!isFirstTime)
                this.recreate()
            isFirstTime = false
        })
    }

    private fun updateLocalConfig(local: Locale) {
        val res: Resources = resources
        val dm: DisplayMetrics = res.displayMetrics
        val conf: Configuration = res.configuration

        conf.setLocale(local)
        conf.setLayoutDirection(local)
        res.updateConfiguration(conf, dm)

    }

    private fun updateStatusBarColor(color: Int) {
        this.window?.statusBarColor = color
        this.window?.navigationBarColor = color
    }

    private fun updateActionBarColor(color: Int) {
        val colorDrawable = ColorDrawable(color)
        this.supportActionBar?.setBackgroundDrawable(colorDrawable)
    }


    //    handle overflow menu
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

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()

    }
}