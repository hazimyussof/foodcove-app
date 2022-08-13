package com.lortnoc.foodcove.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.lortnoc.foodcove.MainActivity
import com.lortnoc.foodcove.R
import com.lortnoc.foodcove.adapter.OnMealClickListener
import com.lortnoc.foodcove.adapter.RecyclerViewAdapter
import com.lortnoc.foodcove.data.Category
import com.lortnoc.foodcove.data.Meal
import com.squareup.picasso.Picasso
import org.json.JSONArray
import org.json.JSONObject

class CategoryFragment : Fragment(R.layout.fragment_category), OnMealClickListener {

    @SuppressLint("WrongConstant")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val foodItemRecyclerView: RecyclerView = view.findViewById(R.id.FoodItemRecyclerView)
        foodItemRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)

        // Json API URLs
        val descriptionURL = "https://jsonformatter.curiousconcept.com/"
        val beefFoodURL = "https://www.themealdb.com/api/json/v1/1/filter.php?c=Beef"
        val chickenFoodURL = "https://www.themealdb.com/api/json/v1/1/filter.php?c=Chicken"
        val dessertFoodURL = "https://www.themealdb.com/api/json/v1/1/filter.php?c=Dessert"
        val seafoodFoodURL = "https://www.themealdb.com/api/json/v1/1/filter.php?c=Seafood"
        val pastaFoodURL = "https://www.themealdb.com/api/json/v1/1/filter.php?c=Pasta"
        val veganFoodURL = "https://www.themealdb.com/api/json/v1/1/filter.php?c=Vegan"

        val categoryText: TextView = view.findViewById(R.id.CategoryText)
        val backButton: Button = view.findViewById(R.id.backButton)
        val loadButton: Button = view.findViewById(R.id.loadButton)
        val categoryImage: ImageView = view.findViewById(R.id.CategoryImage)
        val categoryDescription: TextView = view.findViewById(R.id.CategoryDescription)

        backButton.setOnClickListener {
            fragmentManager?.beginTransaction()?.replace(R.id.fragmentation_section, HomeFragment())?.commit()
            Log.d("TAG", "setOnClickListener: Back Button Clicked")
        }

        val beefClicked: Boolean = (activity as MainActivity).beefClicked
        val chickenClicked: Boolean = (activity as MainActivity).chickenClicked
        val dessertClicked: Boolean = (activity as MainActivity).dessertClicked
        val seafoodClicked: Boolean = (activity as MainActivity).seafoodClicked
        val pastaClicked: Boolean = (activity as MainActivity).pastaClicked
        val veganClicked: Boolean = (activity as MainActivity).veganClicked

        // Data Class Objects for each Category and Food Item
        val beef = Category(null, null)
        val chicken = Category(null, null)
        val dessert = Category(null, null)
        val seafood = Category(null, null)
        val pasta = Category(null, null)
        val vegan = Category(null, null)

        when {
            beefClicked -> {
                categoryText.text = getString(R.string.category_beef)
                loadButton.setOnClickListener {
                    getBeefDescriptionFunction(descriptionURL, beef, categoryDescription, categoryImage)
                    getBeefItemDataFunction(beefFoodURL, foodItemRecyclerView)
                }
                Log.d("TAG", "Function: Replace Fragment with Beef com.lortnoc.foodcove.Data")
            }
            chickenClicked -> {
                categoryText.text = getString(R.string.category_chicken)
                loadButton.setOnClickListener {
                    getChickenDescriptionFunction(descriptionURL, chicken, categoryDescription, categoryImage)
                    getChickenItemDataFunction(chickenFoodURL, foodItemRecyclerView)
                }
                Log.d("TAG", "Function: Replace Fragment with Chicken com.lortnoc.foodcove.Data")
            }
            dessertClicked -> {
                categoryText.text = getText(R.string.category_dessert)
                loadButton.setOnClickListener {
                    getDessertDescriptionFunction(descriptionURL, dessert, categoryDescription, categoryImage)
                    getDessertItemDataFunction(dessertFoodURL, foodItemRecyclerView)
                }
                Log.d("TAG", "Function: Replace Fragment with Dessert com.lortnoc.foodcove.Data")
            }
            seafoodClicked -> {
                categoryText.text = getText(R.string.category_seafood)
                loadButton.setOnClickListener {
                    getSeafoodDescriptionFunction(descriptionURL, seafood, categoryDescription, categoryImage)
                    getSeafoodItemDataFunction(seafoodFoodURL, foodItemRecyclerView)
                }
                Log.d("TAG", "Function: Replace Fragment with Seafood com.lortnoc.foodcove.Data")
            }
            pastaClicked -> {
                categoryText.text = getText(R.string.category_pasta)
                loadButton.setOnClickListener {
                    getPastaDescriptionFunction(descriptionURL, pasta, categoryDescription, categoryImage)
                    getPastaItemDataFunction(pastaFoodURL, foodItemRecyclerView)
                }
                Log.d("TAG", "Function: Replace Fragment with Pasta com.lortnoc.foodcove.Data")
            }
            veganClicked -> {
                categoryText.text = getText(R.string.category_vegan)
                loadButton.setOnClickListener {
                    getVeganDescriptionFunction(descriptionURL, vegan, categoryDescription, categoryImage)
                    getVeganItemDataFunction(veganFoodURL, foodItemRecyclerView)
                }
                Log.d("TAG", "Function: Replace Fragment with Vegan com.lortnoc.foodcove.Data")
            }
        }
    }

    // Method for itemClick in RecyclerView
    override fun onClick(item: Meal, position: Int) {
        (activity as MainActivity).mealItemId = item.idMeal
        (activity as MainActivity).changeToFoodDescriptionFragment()
    }

    // ============== Functions Section ============== //

    private fun getBeefDescriptionFunction(
        descriptionURL: String,
        beef: Category,
        categoryDescription: TextView,
        categoryImage: ImageView
    ) {
        val req: RequestQueue = Volley.newRequestQueue(context)
        val que = JsonObjectRequest(Request.Method.GET, descriptionURL, null, { response ->

            Toast.makeText(context, "Data updated", Toast.LENGTH_SHORT).show()

            val categories: JSONArray = response.getJSONArray("categories")
            val beefSection: JSONObject = categories.getJSONObject(0)

            // Beef Description
            val getBeefDescription: String = beefSection.getString("strCategoryDescription")
            // Formats and remove brackets from Json String
            val formattedBeefStrCategoryDescription: String =
                getBeefDescription.replace("""[\[12\]]""".toRegex(), "").trim()
            beef.strCategoryDescription = formattedBeefStrCategoryDescription
            categoryDescription.text = beef.strCategoryDescription

            // Beef Image
            val getBeefImage: String = beefSection.getString("strCategoryThumb")
            beef.strCategoryThumb = getBeefImage
            Picasso.get().load(beef.strCategoryThumb).into(categoryImage)

        }, {
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
        })
        req.add(que)
        Log.d("TAG", "POST&GET API: Requests Beef Json API")
    }

    private fun getBeefItemDataFunction(
        beefFoodURL: String,
        FoodItemRecyclerView: RecyclerView
    ) {
        val mealBeefList = ArrayList<Meal>()
        val adapter = RecyclerViewAdapter(mealBeefList, this)

        val req: RequestQueue = Volley.newRequestQueue(context)
        val que = JsonObjectRequest(Request.Method.GET, beefFoodURL, null, { response ->

            val meals: JSONArray = response.getJSONArray("meals")

            for (i in 0 until meals.length()) {
                val jsonMeal = meals.getJSONObject(i)
                val jsonMealData = Meal(jsonMeal.getString("idMeal"), jsonMeal.getString("strMeal"), jsonMeal.getString("strMealThumb"))
                mealBeefList.add(jsonMealData)
             }
            FoodItemRecyclerView.adapter = adapter
        }, {
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
        })
        req.add(que)
    }

    private fun getChickenDescriptionFunction(
        descriptionURL: String,
        chicken: Category,
        categoryDescription: TextView,
        categoryImage: ImageView
    ) {
        val req: RequestQueue = Volley.newRequestQueue(context)
        val que = JsonObjectRequest(Request.Method.GET, descriptionURL, null, { response ->

            Toast.makeText(context, "Data updated", Toast.LENGTH_SHORT).show()

            val categories: JSONArray = response.getJSONArray("categories")
            val chickenSection: JSONObject = categories.getJSONObject(1)

            // Chicken Description
            val getChickenDescription: String =
                chickenSection.getString("strCategoryDescription")
            // Formats and remove brackets from Json String
            val char = "1"
            val formattedChickenStrCategoryDescription: String =
                getChickenDescription.replace("""[\[\]()$char]""".toRegex(), "").trim()
            val format1: String =
                formattedChickenStrCategoryDescription.replace("20", "2011").trim()
            val format2: String = format1.replace("9", "19").trim()
            chicken.strCategoryDescription = format2
            categoryDescription.text = chicken.strCategoryDescription

            // Chicken Image
            val getChickenImage: String = chickenSection.getString("strCategoryThumb")
            chicken.strCategoryThumb = getChickenImage
            Picasso.get().load(chicken.strCategoryThumb).into(categoryImage)
        }, {
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
        })
        req.add(que)
        Log.d("TAG", "POST&GET API: Requests Chicken Json API")
    }

    private fun getChickenItemDataFunction(
        chickenFoodURL: String,
        FoodItemRecyclerView: RecyclerView
    ) {
        val mealChickenList = ArrayList<Meal>()
        val adapter = RecyclerViewAdapter(mealChickenList, this)

        val req: RequestQueue = Volley.newRequestQueue(context)
        val que = JsonObjectRequest(Request.Method.GET, chickenFoodURL, null, { response ->

            val meals: JSONArray = response.getJSONArray("meals")

            for (i in 0 until meals.length()) {
                val jsonMeal = meals.getJSONObject(i)
                val jsonMealData = Meal(jsonMeal.getString("idMeal"), jsonMeal.getString("strMeal"), jsonMeal.getString("strMealThumb"))
                mealChickenList.add(jsonMealData)
            }
            FoodItemRecyclerView.adapter = adapter

            FoodItemRecyclerView.adapter = adapter
        }, {
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
        })
        req.add(que)
    }

    private fun getDessertDescriptionFunction(
        descriptionURL: String,
        dessert: Category,
        categoryDescription: TextView,
        categoryImage: ImageView
    ) {
        val req: RequestQueue = Volley.newRequestQueue(context)
        val que = JsonObjectRequest(Request.Method.GET, descriptionURL, null, { response ->

            Toast.makeText(context, "Data updated", Toast.LENGTH_SHORT).show()

            val categories: JSONArray = response.getJSONArray("categories")
            val dessertSection: JSONObject = categories.getJSONObject(2)

            // Dessert Description
            val getDessertDescription: String =
                dessertSection.getString("strCategoryDescription")
            // Formats and trims Json String
            val extPar =
                "The term dessert can apply to many confections, such as biscuits, cakes, cookies, custards, gelatins, ice creams, pastries, pies, puddings, and sweet soups, and tarts. Fruit is also commonly found in dessert courses because of its naturally occurring sweetness. Some cultures sweeten foods that are more commonly savory to create desserts."
            val formattedDessertStrCategoryDescription: String =
                getDessertDescription.replace(extPar, "").trim()
            dessert.strCategoryDescription = formattedDessertStrCategoryDescription
            categoryDescription.text = dessert.strCategoryDescription

            // Dessert Image
            val getDessertImage: String = dessertSection.getString("strCategoryThumb")
            dessert.strCategoryThumb = getDessertImage
            Picasso.get().load(dessert.strCategoryThumb).into(categoryImage)
        }, {
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
        })
        req.add(que)
        Log.d("TAG", "POST&GET API: Requests Dessert Json API")
    }

    private fun getDessertItemDataFunction(
        dessertFoodURL: String,
        FoodItemRecyclerView: RecyclerView
    ) {
        val mealDessertList = ArrayList<Meal>()
        val adapter = RecyclerViewAdapter(mealDessertList, this)

        val req: RequestQueue = Volley.newRequestQueue(context)
        val que = JsonObjectRequest(Request.Method.GET, dessertFoodURL, null, { response ->

            val meals: JSONArray = response.getJSONArray("meals")

            for (i in 0 until meals.length()) {
                val jsonMeal = meals.getJSONObject(i)
                val jsonMealData = Meal(jsonMeal.getString("idMeal"), jsonMeal.getString("strMeal"), jsonMeal.getString("strMealThumb"))
                mealDessertList.add(jsonMealData)
            }
            FoodItemRecyclerView.adapter = adapter
        }, {
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
        })
        req.add(que)
    }

    private fun getSeafoodDescriptionFunction(
        descriptionURL: String,
        seafood: Category,
        categoryDescription: TextView,
        categoryImage: ImageView
    ) {
        val req: RequestQueue = Volley.newRequestQueue(context)
        val que = JsonObjectRequest(Request.Method.GET, descriptionURL, null, { response ->

            Toast.makeText(context, "Data updated", Toast.LENGTH_SHORT).show()

            val categories: JSONArray = response.getJSONArray("categories")
            val seafoodSection: JSONObject = categories.getJSONObject(7)

            // Seafood Description
            val getSeafoodDescription: String =
                seafoodSection.getString("strCategoryDescription")
            // Formats and remove brackets from Json String
            val extPar =
                "Edible sea plants, such as some seaweeds and microalgae, are widely eaten as seafood around the world, especially in Asia (see the category of sea vegetables). In North America, although not generally in the United Kingdom, the term \"seafood\" is extended to fresh water organisms eaten by humans, so all edible aquatic life may be referred to as seafood. For the sake of completeness, this article includes all edible aquatic life."
            val formattedSeafoodStrCategoryDescription: String =
                getSeafoodDescription.replace(extPar, "").trim()
            seafood.strCategoryDescription = formattedSeafoodStrCategoryDescription
            categoryDescription.text = seafood.strCategoryDescription

            // Seafood Image
            val getSeafoodImage: String = seafoodSection.getString("strCategoryThumb")
            seafood.strCategoryThumb = getSeafoodImage
            Picasso.get().load(seafood.strCategoryThumb).into(categoryImage)
        }, {
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
        })
        req.add(que)
        Log.d("TAG", "POST&GET API: Requests Seafood Json API")
    }

    private fun getSeafoodItemDataFunction(
        seafoodFoodURL: String,
        FoodItemRecyclerView: RecyclerView
    ) {
        val mealSeafoodList = ArrayList<Meal>()
        val adapter = RecyclerViewAdapter(mealSeafoodList, this)

        val req: RequestQueue = Volley.newRequestQueue(context)
        val que = JsonObjectRequest(Request.Method.GET, seafoodFoodURL, null, { response ->

            val meals: JSONArray = response.getJSONArray("meals")

            for (i in 0 until meals.length()) {
                val jsonMeal = meals.getJSONObject(i)
                val jsonMealData = Meal(jsonMeal.getString("idMeal"), jsonMeal.getString("strMeal"), jsonMeal.getString("strMealThumb"))
                mealSeafoodList.add(jsonMealData)
            }
            FoodItemRecyclerView.adapter = adapter
        }, {
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
        })
        req.add(que)
    }

    private fun getPastaDescriptionFunction(
        descriptionURL: String,
        pasta: Category,
        categoryDescription: TextView,
        categoryImage: ImageView
    ) {
        val req: RequestQueue = Volley.newRequestQueue(context)
        val que = JsonObjectRequest(Request.Method.GET, descriptionURL, null, { response ->

            Toast.makeText(context, "Data updated", Toast.LENGTH_SHORT).show()

            val categories: JSONArray = response.getJSONArray("categories")
            val pastaSection: JSONObject = categories.getJSONObject(5)

            // Pasta Description
            val getPastaDescription: String =
                pastaSection.getString("strCategoryDescription")
            // Formats and remove brackets from Json String
            val extPar =
                "As an alternative for those wanting a different taste, or who need to avoid products containing gluten, some pastas can be made using rice flour in place of wheat.[3][4] Pastas may be divided into two broad categories, dried (pasta secca) and fresh (pasta fresca)."
            val formattedPastaStrCategoryDescription: String =
                getPastaDescription.replace(extPar, "").trim()
            pasta.strCategoryDescription = formattedPastaStrCategoryDescription
            categoryDescription.text = pasta.strCategoryDescription

            // Pasta Image
            val getPastaImage: String = pastaSection.getString("strCategoryThumb")
            pasta.strCategoryThumb = getPastaImage
            Picasso.get().load(pasta.strCategoryThumb).into(categoryImage)
        }, {
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
        })
        req.add(que)
        Log.d("TAG", "POST&GET API: Requests Pasta Json API")
    }

    private fun getPastaItemDataFunction(
        pastaFoodURL: String,
        FoodItemRecyclerView: RecyclerView
    ) {
        val mealPastaList = ArrayList<Meal>()
        val adapter = RecyclerViewAdapter(mealPastaList, this)

        val req: RequestQueue = Volley.newRequestQueue(context)
        val que = JsonObjectRequest(Request.Method.GET, pastaFoodURL, null, { response ->

            val meals: JSONArray = response.getJSONArray("meals")

            for (i in 0 until meals.length()) {
                val jsonMeal = meals.getJSONObject(i)
                val jsonMealData = Meal(jsonMeal.getString("idMeal"), jsonMeal.getString("strMeal"), jsonMeal.getString("strMealThumb"))
                mealPastaList.add(jsonMealData)
            }
            FoodItemRecyclerView.adapter = adapter
        }, {
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
        })
        req.add(que)
    }

    private fun getVeganDescriptionFunction(
        descriptionURL: String,
        vegan: Category,
        categoryDescription: TextView,
        categoryImage: ImageView
    ) {
        val req: RequestQueue = Volley.newRequestQueue(context)
        val que = JsonObjectRequest(Request.Method.GET, descriptionURL, null, { response ->

            Toast.makeText(context, "Data updated", Toast.LENGTH_SHORT).show()

            val categories: JSONArray = response.getJSONArray("categories")
            val veganSection: JSONObject = categories.getJSONObject(10)

            // Vegan Description
            val getVeganDescription: String =
                veganSection.getString("strCategoryDescription")
            // Formats and remove brackets from Json String
            val extPar =
                "[b] A follower of either the diet or the philosophy is known as a vegan (pronounced /ˈviːɡən/ VEE-gən). Distinctions are sometimes made between several categories of veganism. Dietary vegans (or strict vegetarians) refrain from consuming animal products, not only meat but also eggs, dairy products and other animal-derived substances.[c] The term ethical vegan is often applied to those who not only follow a vegan diet but extend the philosophy into other areas of their lives, and oppose the use of animals for any purpose.[d]"
            val formattedVeganStrCategoryDescription: String =
                getVeganDescription.replace(extPar, "").trim()
            vegan.strCategoryDescription = formattedVeganStrCategoryDescription
            categoryDescription.text = vegan.strCategoryDescription

            // Vegan Image
            val getVeganImage: String = veganSection.getString("strCategoryThumb")
            vegan.strCategoryThumb = getVeganImage
            Picasso.get().load(vegan.strCategoryThumb).into(categoryImage)
        }, {
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
        })
        req.add(que)
        Log.d("TAG", "POST&GET API: Requests Vegan Json API")
    }

    private fun getVeganItemDataFunction(
        veganFoodURL: String,
        FoodItemRecyclerView: RecyclerView
    ) {
        val mealVeganList = ArrayList<Meal>()
        val adapter = RecyclerViewAdapter(mealVeganList, this)

        val req: RequestQueue = Volley.newRequestQueue(context)
        val que = JsonObjectRequest(Request.Method.GET, veganFoodURL, null, { response ->

            val meals: JSONArray = response.getJSONArray("meals")

            for (i in 0 until meals.length()) {
                val jsonMeal = meals.getJSONObject(i)
                val jsonMealData = Meal(jsonMeal.getString("idMeal"), jsonMeal.getString("strMeal"), jsonMeal.getString("strMealThumb"))
                mealVeganList.add(jsonMealData)
            }
            FoodItemRecyclerView.adapter = adapter
        }, {
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
        })
        req.add(que)
    }
}
