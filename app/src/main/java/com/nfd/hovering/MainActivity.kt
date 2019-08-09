package com.nfd.hovering

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.nfd.hovering.fragments.AboutFragment
import com.nfd.hovering.fragments.GameFragment
import com.nfd.hovering.fragments.RecordsFragment
import com.nfd.hovering.fragments.SettingsFragment
import com.nfd.hovering.viewmodels.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val model: MainViewModel by viewModel()

    private val aboutFragment = AboutFragment.newInstance()
    private val gameFragment = GameFragment.newInstance()
    private val settingsFragment = SettingsFragment.newInstance()

    private val recordsFragment = RecordsFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.activity_toolbar))

        openFragment(gameFragment)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
//        R.id.navigation_records -> {
//            openFragment(recordsFragment)
//            true
//        }
        R.id.navigation_website -> {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://tryhovering.com/"))
            startActivity(browserIntent)
            true
        }
        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
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

        fun onClickWebsiteLink(v: View) {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://neverfapdeluxe.com/"))
            startActivity(browserIntent)
        }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
