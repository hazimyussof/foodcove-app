package com.lortnoc.foodcove

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.lortnoc.foodcove.fragment.FoodDescriptionFragment
import com.lortnoc.foodcove.fragment.HomeFragment

class MainActivity : AppCompatActivity() {

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Shows HomeFragment as the 1st fragment when application is opened
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentation_section, HomeFragment()).commit()

        Log.d("TAG", "onCreate: Application started")
    }

    // Method to change Activity from HomeFragment (MainActivity) to SettingsFragment (PreferenceActivity)
    fun changeToSettingsActivity() {
        startActivity(Intent(this, PreferenceActivity::class.java))
    }

    // Method to change Fragment from CategoryFragment to FoodDescriptionFragment by RecyclerView Item onClickListener
    fun changeToFoodDescriptionFragment() {
        supportFragmentManager.beginTransaction().replace(
            R.id.fragmentation_section,
            FoodDescriptionFragment()
        ).commit()
    }

    // Public variable to cast mealItemId string retrieved from RecyclerView Item onClick to FoodDescriptionFragment
    var mealItemId: String? = null

    // Public variable to cast which TextView was clicked from HomeFragment to determine which data to show in CategoryFragment
    var beefClicked: Boolean = false
    var chickenClicked: Boolean = false
    var dessertClicked: Boolean = false
    var seafoodClicked: Boolean = false
    var pastaClicked: Boolean = false
    var veganClicked: Boolean = false
}

/*
References:
    API Website - https://www.themealdb.com/
    API Documentation - https://www.themealdb.com/api.php

    Volley Networking Library - https://github.com/google/Volley
    Volley Documentation - https://developer.android.com/training/volley/index.html
    Volley RequestQueue Method - https://developer.android.com/training/volley/simple

    Picasso Image Downloading and Caching Library - https://github.com/square/picasso
    Picasso Documentation - https://square.github.io/picasso/

    Foundry Font - https://elements.envato.com/foundry-font-pack-5WZA9P?utm_campaign=elements_social_eyt_zMuewiVpg_U&utm_medium=social&utm_source=YouTube&utm_content=description
    Fredona One Font - https://fonts.google.com/specimen/Fredoka+One#standard-styles
    Forma Font - https://www.dafontfree.io/forma-font/
*/