package com.azhar.drinkrecipe.activities

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.azhar.drinkrecipe.adapter.CategoriesAdapter
import com.azhar.drinkrecipe.databinding.ActivityCategoriesBinding
import com.azhar.drinkrecipe.model.ModelCategories
import com.azhar.drinkrecipe.networking.ApiEndpoint
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class CategoriesActivity : AppCompatActivity() {

    private val binding: ActivityCategoriesBinding {
        ActivityCategoriesBinding.inflate(layoutInflater)
    }

    var modelCategories: MutableList<ModelCategories> = ArrayList()
    var categoriesAdapter: CategoriesAdapter? = null
    var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        progressDialog = ProgressDialog(this)
        progressDialog?.setTitle("Mohon Tunggu...")
        progressDialog?.setCancelable(false)
        progressDialog?.setMessage("Sedang menampilkan kategori")

        categoriesAdapter = CategoriesAdapter(modelCategories, this@CategoriesActivity)
        binding.rvCategories.layoutManager = LinearLayoutManager(this)
        binding.rvCategories.adapter = categoriesAdapter
        binding.rvCategories.setHasFixedSize(true)

        //get data categories
        getCategories()
    }

    private fun getCategories() {
            progressDialog?.show()
            AndroidNetworking.get(ApiEndpoint.BASEURL + ApiEndpoint.URL_CATEGORIES)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(object : JSONObjectRequestListener {
                        override fun onResponse(response: JSONObject) {
                            progressDialog?.dismiss()
                            try {
                                val jsonArray = response.getJSONArray("drinks")
                                for (i in 0 until jsonArray.length()) {
                                    val jsonObject = jsonArray.getJSONObject(i)
                                    val dataModel = ModelCategories()
                                    dataModel.strCategory = jsonObject.getString("strCategory")
                                    modelCategories.add(dataModel)
                                }
                                categoriesAdapter?.notifyDataSetChanged()
                            } catch (e: JSONException) {
                                Toast.makeText(this@CategoriesActivity,
                                        "Oops, gagal menampilkan kategori.", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onError(anError: ANError) {
                            progressDialog?.dismiss()
                            Toast.makeText(this@CategoriesActivity,
                                    "Oops! Sepertinya ada masalah dengan koneksi internet kamu.", Toast.LENGTH_SHORT).show()
                        }
                    })
        }

}