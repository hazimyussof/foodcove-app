package com.lortnoc.foodcove.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.lortnoc.foodcove.MainActivity
import com.lortnoc.foodcove.R

class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val settingsButton: ImageView = view.findViewById(R.id.settingsButton)
        val beefButton: TextView = view.findViewById(R.id.Beef)
        val chickenButton: TextView = view.findViewById(R.id.Chicken)
        val dessertButton: TextView = view.findViewById(R.id.Dessert)
        val seafoodButton: TextView = view.findViewById(R.id.Seafood)
        val pastaButton: TextView = view.findViewById(R.id.Pasta)
        val veganButton: TextView = view.findViewById(R.id.Vegan)
        val confirmButton: Button = view.findViewById(R.id.confirm_button)

        settingsButton.setOnClickListener {
            (activity as MainActivity).changeToSettingsActivity()
        }

        beefButton.setOnClickListener {
            beefButton.setTextColor(Color.GREEN)
            chickenButton.setTextColor(Color.BLACK)
            dessertButton.setTextColor(Color.BLACK)
            seafoodButton.setTextColor(Color.BLACK)
            pastaButton.setTextColor(Color.BLACK)
            veganButton.setTextColor(Color.BLACK)
            Log.d("TAG", "setOnClickListener: Beef Button Clicked")
        }
        chickenButton.setOnClickListener {
            beefButton.setTextColor(Color.BLACK)
            chickenButton.setTextColor(Color.GREEN)
            dessertButton.setTextColor(Color.BLACK)
            seafoodButton.setTextColor(Color.BLACK)
            pastaButton.setTextColor(Color.BLACK)
            veganButton.setTextColor(Color.BLACK)
            Log.d("TAG", "setOnClickListener: Chicken Button Clicked")
        }
        dessertButton.setOnClickListener {
            beefButton.setTextColor(Color.BLACK)
            chickenButton.setTextColor(Color.BLACK)
            dessertButton.setTextColor(Color.GREEN)
            seafoodButton.setTextColor(Color.BLACK)
            pastaButton.setTextColor(Color.BLACK)
            veganButton.setTextColor(Color.BLACK)
            Log.d("TAG", "setOnClickListener: Dessert Button Clicked")
        }
        seafoodButton.setOnClickListener {
            beefButton.setTextColor(Color.BLACK)
            chickenButton.setTextColor(Color.BLACK)
            dessertButton.setTextColor(Color.BLACK)
            seafoodButton.setTextColor(Color.GREEN)
            pastaButton.setTextColor(Color.BLACK)
            veganButton.setTextColor(Color.BLACK)
            Log.d("TAG", "setOnClickListener: Seafood Button Clicked")
        }
        pastaButton.setOnClickListener {
            beefButton.setTextColor(Color.BLACK)
            chickenButton.setTextColor(Color.BLACK)
            dessertButton.setTextColor(Color.BLACK)
            seafoodButton.setTextColor(Color.BLACK)
            pastaButton.setTextColor(Color.GREEN)
            veganButton.setTextColor(Color.BLACK)
            Log.d("TAG", "setOnClickListener: Pasta Button Clicked")
        }
        veganButton.setOnClickListener {
            beefButton.setTextColor(Color.BLACK)
            chickenButton.setTextColor(Color.BLACK)
            dessertButton.setTextColor(Color.BLACK)
            seafoodButton.setTextColor(Color.BLACK)
            pastaButton.setTextColor(Color.BLACK)
            veganButton.setTextColor(Color.GREEN)
            Log.d("TAG", "setOnClickListener: Vegan Button Clicked")
        }

        confirmButton.setOnClickListener {
            if (beefButton.currentTextColor == Color.GREEN) {
                fragmentManager?.beginTransaction()
                    ?.replace(R.id.fragmentation_section, CategoryFragment())?.commit()
                (activity as MainActivity).beefClicked = true
                (activity as MainActivity).chickenClicked = false
                (activity as MainActivity).dessertClicked = false
                (activity as MainActivity).seafoodClicked = false
                (activity as MainActivity).pastaClicked = false
                (activity as MainActivity).veganClicked = false
            }
            if (chickenButton.currentTextColor == Color.GREEN) {
                fragmentManager?.beginTransaction()
                    ?.replace(R.id.fragmentation_section, CategoryFragment())?.commit()
                (activity as MainActivity).beefClicked = false
                (activity as MainActivity).chickenClicked = true
                (activity as MainActivity).dessertClicked = false
                (activity as MainActivity).seafoodClicked = false
                (activity as MainActivity).pastaClicked = false
                (activity as MainActivity).veganClicked = false
            }
            if (dessertButton.currentTextColor == Color.GREEN) {
                fragmentManager?.beginTransaction()
                    ?.replace(R.id.fragmentation_section, CategoryFragment())?.commit()
                (activity as MainActivity).beefClicked = false
                (activity as MainActivity).chickenClicked = false
                (activity as MainActivity).dessertClicked = true
                (activity as MainActivity).seafoodClicked = false
                (activity as MainActivity).pastaClicked = false
                (activity as MainActivity).veganClicked = false
            }
            if (seafoodButton.currentTextColor == Color.GREEN) {
                fragmentManager?.beginTransaction()
                    ?.replace(R.id.fragmentation_section, CategoryFragment())?.commit()
                (activity as MainActivity).beefClicked = false
                (activity as MainActivity).chickenClicked = false
                (activity as MainActivity).dessertClicked = false
                (activity as MainActivity).seafoodClicked = true
                (activity as MainActivity).pastaClicked = false
                (activity as MainActivity).veganClicked = false
            }
            if (pastaButton.currentTextColor == Color.GREEN) {
                fragmentManager?.beginTransaction()
                    ?.replace(R.id.fragmentation_section, CategoryFragment())?.commit()
                (activity as MainActivity).beefClicked = false
                (activity as MainActivity).chickenClicked = false
                (activity as MainActivity).dessertClicked = false
                (activity as MainActivity).seafoodClicked = false
                (activity as MainActivity).pastaClicked = true
                (activity as MainActivity).veganClicked = false
            }
            if (veganButton.currentTextColor == Color.GREEN) {
                fragmentManager?.beginTransaction()
                    ?.replace(R.id.fragmentation_section, CategoryFragment())?.commit()
                (activity as MainActivity).beefClicked = false
                (activity as MainActivity).chickenClicked = false
                (activity as MainActivity).dessertClicked = false
                (activity as MainActivity).seafoodClicked = false
                (activity as MainActivity).pastaClicked = false
                (activity as MainActivity).veganClicked = true
            } else if (beefButton.currentTextColor != Color.GREEN &&
                chickenButton.currentTextColor != Color.GREEN &&
                dessertButton.currentTextColor != Color.GREEN &&
                seafoodButton.currentTextColor != Color.GREEN &&
                pastaButton.currentTextColor != Color.GREEN &&
                veganButton.currentTextColor != Color.GREEN
            ) {
                Toast.makeText(context, "No category selected", Toast.LENGTH_SHORT).show()
            }
            Log.d("TAG", "setOnClickListener: Confirm Button Clicked")
        }
    }
}
