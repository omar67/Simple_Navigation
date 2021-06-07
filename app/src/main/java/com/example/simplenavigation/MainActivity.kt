package com.example.simplenavigation

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
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
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.simplenavigation.databinding.ActivityMainBinding
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private val viewModel: SharedViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)



        binding.lifecycleOwner = this


//        observe theme color and update action bar and status bar and navigation bar
        viewModel.themeColor.observe(this, { color ->
            if (color != -1) {
                this.window?.statusBarColor = color
                this.window?.navigationBarColor = color

//                update action bar color
                val colorDrawable = ColorDrawable(color)
                this.supportActionBar?.setBackgroundDrawable(colorDrawable)
            }
        })


//        observe and update display mode
        viewModel.themeMode.observe(this, { mode ->
            AppCompatDelegate.setDefaultNightMode(mode)
        })

        // Update the language and restart activity to apply changes
        viewModel.lang.observe(this, { code->
            val currLocal = resources.configuration.locale
            val local = Locale(code)

            if ( !local.toString().equals(currLocal.toString(), true) ) {
                val res: Resources = resources
                val dm: DisplayMetrics = res.displayMetrics
                val conf: Configuration = res.configuration

                conf.setLocale(local)
                conf.setLayoutDirection(local)
                res.updateConfiguration(conf, dm)

//                reload activity to apply changes
                this.recreate()

            }
        })


        navController = this.findNavController(R.id.fragmentContainerView)
        setupActionBarWithNavController(navController)

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