package com.example.simplenavigation

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




//        val helpBtn = findViewById<Button>(R.id.helpBtn)
//        val storeBtn = findViewById<Button>(R.id.storeBtn)
//        val optionsBtn = findViewById<Button>(R.id.optionsBtn)

        val navController = findNavController( R.id.fragmentContainerView)

//        helpBtn.setOnClickListener {
//            navController.navigate(R.id.action_storeFragment_to_helpFragment)
//        }
//        storeBtn.setOnClickListener {
//            navController.navigate(R.id.action_helpFragment_to_storeFragment)
//        }
//        optionsBtn.setOnClickListener {
////            navController.navigateUp()
////            navController.navigate(R.id.action_storeFragment_to_options)
//            val optionsFragment = OptionsFragment()
//
//            supportFragmentManager.commit {
//                setReorderingAllowed(true)
//                replace(R.id.fragmentContainerView, optionsFragment)
//
//            }
//        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return  NavigationUI.onNavDestinationSelected(item, findNavController(R.id.fragmentContainerView)) || super.onOptionsItemSelected(item)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
         super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.overflow_menu, menu)
        return true
    }
}