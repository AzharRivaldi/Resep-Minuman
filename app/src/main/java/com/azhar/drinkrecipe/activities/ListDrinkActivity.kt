package com.azhar.drinkrecipe.activities

import android.app.ProgressDialog
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.azhar.drinkrecipe.R
import com.azhar.drinkrecipe.adapter.DrinkAdapter
import com.azhar.drinkrecipe.databinding.ActivityListDrinkBinding
import com.azhar.drinkrecipe.model.ModelCategories
import com.azhar.drinkrecipe.model.ModelDrink
import com.azhar.drinkrecipe.networking.ApiEndpoint
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class ListDrinkActivity : AppCompatActivity() {

    private val binding: ActivityListDrinkBinding {
        ActivityListDrinkBinding.inflate(layoutInflater)
    }

    companion object {
        const val LIST_DRINK = "CATEGORIES"
    }

    var modelDrink: MutableList<ModelDrink> = ArrayList()
    var modelCategories: ModelCategories? = null
    var drinkAdapter: DrinkAdapter? = null
    var progressDialog: ProgressDialog? = null
    var strCategory: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        progressDialog = ProgressDialog(this)
        progressDialog?.setTitle("Mohon Tunggu...")
        progressDialog?.setCancelable(false)
        progressDialog?.setMessage("Sedang menampilkan minuman")

        setSupportActionBar(binding.toolbar)
        assert(supportActionBar != null)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //get data intent
        modelCategories = intent.getSerializableExtra(LIST_DRINK) as ModelCategories
        if (modelCategories != null) {
            strCategory = modelCategories?.strCategory

            binding.tvCategories.text = strCategory

            drinkAdapter = DrinkAdapter(modelDrink, this@ListDrinkActivity)
            binding.rvListDrink.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
            binding.rvListDrink.adapter = drinkAdapter
            binding.rvListDrink.setHasFixedSize(true)

            //get data drink
            getListDrink(strCategory)
        }
    }

    private fun getListDrink(strCategory: String?) {
        progressDialog?.show()
        AndroidNetworking.get(ApiEndpoint.BASEURL + ApiEndpoint.URL_FILTER)
                .addPathParameter("strCategory", strCategory)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject) {
                        progressDialog?.dismiss()
                        try {
                            val jsonArray = response.getJSONArray("drinks")
                            for (i in 0 until jsonArray.length()) {
                                val jsonObject = jsonArray.getJSONObject(i)
                                val dataModel = ModelDrink()
                                dataModel.idDrink = jsonObject.getString("idDrink")
                                dataModel.strDrink = jsonObject.getString("strDrink")
                                dataModel.strDrinkThumb = jsonObject.getString("strDrinkThumb")
                                modelDrink.add(dataModel)
                            }
                            drinkAdapter?.notifyDataSetChanged()
                        } catch (e: JSONException) {
                            Toast.makeText(this@ListDrinkActivity,
                                    "Oops, gagal menampilkan minuman.", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onError(anError: ANError) {
                        progressDialog?.dismiss()
                        Toast.makeText(this@ListDrinkActivity,
                                "Oops! Sepertinya ada masalah dengan koneksi internet kamu.", Toast.LENGTH_SHORT).show()
                    }
                })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}