package com.nfd.hovering

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.nfd.hovering.fragments.AboutFragment
import com.nfd.hovering.fragments.GameFragment
import com.nfd.hovering.fragments.SettingsFragment
import com.nfd.hovering.viewmodels.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val model: MainViewModel by viewModel()

    private val aboutFragment = AboutFragment.newInstance()
    private val gameFragment = GameFragment.newInstance()
    private val settingsFragment = SettingsFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.activity_toolbar))

        openFragment(gameFragment)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_game -> {
                openFragment(gameFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_settings -> {
                openFragment(settingsFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_about -> {
                openFragment(aboutFragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
