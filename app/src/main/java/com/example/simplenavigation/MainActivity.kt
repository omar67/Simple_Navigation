package com.example.simplenavigation

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.simplenavigation.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.progressBar.visibility = View.VISIBLE

        val viewModel: SharedViewModel by viewModels()
        binding.lifecycleOwner = this

//        observe theme color and update action bar and status bar and navigation bar
        viewModel.themeColor.observe(this, Observer { color ->
            if (color != -1) {
                this.window?.statusBarColor = color
                this.window?.navigationBarColor = color
//                if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO) {
                    val colorDrawable = ColorDrawable(color)
                    this.supportActionBar?.setBackgroundDrawable(colorDrawable)
//                }
            }
        })
        //        observe theme text color and update action bar title
//        viewModel.textColor.observe(this, Observer { color ->
//            val text: Spannable = SpannableString(supportActionBar?.title)
//            text.setSpan(
//                ForegroundColorSpan(color),
//                0,
//                text.length,
//                Spannable.SPAN_INCLUSIVE_INCLUSIVE
//            )
//            supportActionBar!!.title = text
//        })

//        observe and update display mode
        viewModel.themeMode.observe(this, Observer { mode ->
            AppCompatDelegate.setDefaultNightMode(mode)
        })

        navController = this.findNavController(R.id.fragmentContainerView)
        setupActionBarWithNavController(navController)

        binding.progressBar.visibility = View.GONE
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

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()

    }
}