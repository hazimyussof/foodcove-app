package com.lortnoc.foodcove.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.lortnoc.foodcove.R
import com.lortnoc.foodcove.data.Meal
import com.squareup.picasso.Picasso

class RecyclerViewAdapter(
    private val mealList: ArrayList<Meal>,
    var clickListener: OnMealClickListener
) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var foodImage: ImageView = itemView.findViewById(R.id.FoodImage)
        var foodName: TextView = itemView.findViewById(R.id.FoodName)
        var cardView: CardView = itemView.findViewById(R.id.cardView)

        fun initialize(item: Meal, action: OnMealClickListener) {
            foodName.text = item.strMeal

            cardView.setOnClickListener {
                action.onClick(item, adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_food_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val meal: Meal = mealList[position]

        holder.foodName.text = meal.strMeal
        Picasso.get().load(meal.strMealThumb).into(holder.foodImage)

        holder.initialize(mealList[position], clickListener)
    }

    override fun getItemCount(): Int {
        return mealList.size
    }
}

interface OnMealClickListener {
    fun onClick(item: Meal, position: Int)
}



