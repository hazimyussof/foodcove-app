package com.lortnoc.foodcove.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.lortnoc.foodcove.MainActivity
import com.lortnoc.foodcove.R
import com.lortnoc.foodcove.data.Food
import com.squareup.picasso.Picasso
import org.json.JSONArray
import org.json.JSONObject

class FoodDescriptionFragment : Fragment(R.layout.fragment_description_food) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mealID: String? = (activity as MainActivity).mealItemId
        val mealFoodURL = "https://www.themealdb.com/api/json/v1/1/lookup.php?i=$mealID"
        val backButton: Button = view.findViewById(R.id.backButton2)
        val homeButton: Button = view.findViewById(R.id.homeButton)
        val mealName: TextView = view.findViewById(R.id.mealName)
        val mealImage: ImageView = view.findViewById(R.id.MealImage)
        val mealArea: TextView = view.findViewById(R.id.MealArea)
        val mealInstructions: TextView = view.findViewById(R.id.MealInstructions)

        backButton.setOnClickListener {
            fragmentManager?.beginTransaction()
                ?.replace(R.id.fragmentation_section, CategoryFragment())?.commit()
            Log.d("TAG", "setOnClickListener: Back Button Clicked")
        }

        homeButton.setOnClickListener {
            fragmentManager?.beginTransaction()?.replace(R.id.fragmentation_section, HomeFragment())
                ?.commit()
            Log.d("TAG", "setOnClickListener: Back Button Clicked")
        }

        // Object for Data Class Food
        val foodItem = Food(null, null, null, null)

        // GET REQUEST USING VOLLEY
        val req: RequestQueue = Volley.newRequestQueue(context)
        val que = JsonObjectRequest(Request.Method.GET, mealFoodURL, null, { response ->

            val meals: JSONArray = response.getJSONArray("meals")
            val mealItems: JSONObject = meals.getJSONObject(0)

            // Meal Name
            val getMealName: String = mealItems.getString("strMeal")
            foodItem.strMeal = getMealName
            mealName.text = foodItem.strMeal

            // Meal Image
            val getMealImage: String = mealItems.getString("strMealThumb")
            foodItem.strMealThumb = getMealImage
            Picasso.get().load(foodItem.strMealThumb).into(mealImage)

            // Meal Area
            val getMealArea: String = mealItems.getString("strArea")
            foodItem.strArea = getMealArea
            mealArea.text = foodItem.strArea

            // Meal Instruction
            val getMealInstructions: String = mealItems.getString("strInstructions")
            foodItem.strInstructions = getMealInstructions
            mealInstructions.text = foodItem.strInstructions
        }, {
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
        })
        req.add(que)
        Log.d("TAG", "POST&GET API: Requests Meal Description")


    }
}