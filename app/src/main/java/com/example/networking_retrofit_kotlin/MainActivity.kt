package com.example.networking_retrofit_kotlin

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.networking_retrofit_kotlin.adapter.EmployeeAdapter
import com.example.networking_retrofit_kotlin.api.ApiClient
import com.example.networking_retrofit_kotlin.api.ApiService
import com.example.networking_retrofit_kotlin.api.model.ResponseListErrors
import com.example.networking_retrofit_kotlin.api.model.ResponseObjectErrors
import com.example.networking_retrofit_kotlin.model.Employee
import com.example.networking_retrofit_kotlin.utils.Alerts
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var service: ApiService
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var adapter: EmployeeAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var fm_loading: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }

    private fun initViews(){
        service = ApiClient.createService(ApiService::class.java)
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_container)
        fm_loading = findViewById(R.id.fm_loading)

        recyclerView = findViewById(R.id.rv_main)
        recyclerView.layoutManager = GridLayoutManager(this, 1)
        getEmployees()

        swipeRefreshLayout.setOnRefreshListener {
            getEmployees()
            swipeRefreshLayout.isRefreshing = false
        }

        val b_floating = findViewById<FloatingActionButton>(R.id.b_floating)
        b_floating.setOnClickListener {
            Alerts.createrDialog(this, null) {
                createEmployee(it)
            }
        }
    }

    private fun refreshAdapter(items: ArrayList<Employee>){
        adapter = EmployeeAdapter(this, items, {
            deleteEmployee(it)
        }, { employee, position ->
            updateEmployee(employee, position)
        })
        recyclerView.adapter = adapter

    }

    fun getEmployees(){
        fm_loading.visibility = View.VISIBLE
        service.getEmployees().enqueue(object : Callback<ResponseListErrors<Employee>>{
            override fun onResponse(
                call: Call<ResponseListErrors<Employee>>,
                response: Response<ResponseListErrors<Employee>>,
            ) {
                Log.d("RRR", "Read -> ${response.isSuccessful}")
                fm_loading.visibility = View.INVISIBLE
                refreshAdapter(response.body()!!.data as ArrayList<Employee>)
                response.body()
            }

            override fun onFailure(call: Call<ResponseListErrors<Employee>>, t: Throwable) {
                fm_loading.visibility = View.INVISIBLE
                Log.d("RRR", t.localizedMessage!!)
            }

        })
    }

    fun createEmployee(employee: Employee){
        fm_loading.visibility = View.VISIBLE
        service.createEmployee(employee).enqueue(object: Callback<ResponseObjectErrors<Employee>>{
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<ResponseObjectErrors<Employee>>,
                response: Response<ResponseObjectErrors<Employee>>,
            ) {
                fm_loading.visibility = View.INVISIBLE
                adapter.items.add(response.body()!!.data)
                adapter.notifyDataSetChanged()
                Log.d("RRR", "Create -> ${response.body()!!.data}")
            }

            override fun onFailure(call: Call<ResponseObjectErrors<Employee>>, t: Throwable) {
                fm_loading.visibility = View.INVISIBLE
                Log.d("RRR", t.localizedMessage!!)
            }

        })
    }

    fun deleteEmployee(employee: Employee){
        fm_loading.visibility = View.VISIBLE
        service.deleteEmployee(employee.id).enqueue(object: Callback<ResponseObjectErrors<String>>{
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<ResponseObjectErrors<String>>,
                response: Response<ResponseObjectErrors<String>>,
            ) {
                fm_loading.visibility = View.INVISIBLE
                adapter.items.remove(employee)
                adapter.notifyDataSetChanged()
                Log.d("RRR", "Delete -> ${response.isSuccessful}")
            }

            override fun onFailure(call: Call<ResponseObjectErrors<String>>, t: Throwable) {
                fm_loading.visibility = View.INVISIBLE
                Log.d("RRR", t.localizedMessage!!)
            }

        })
    }

    fun updateEmployee(employee: Employee, position: Int){
        fm_loading.visibility = View.VISIBLE
        service.updateEmployee(employee.id, employee).enqueue(object: Callback<ResponseObjectErrors<Employee>>{
            override fun onResponse(
                call: Call<ResponseObjectErrors<Employee>>,
                response: Response<ResponseObjectErrors<Employee>>,
            ) {
                fm_loading.visibility = View.INVISIBLE
                adapter.items[position] = employee
                adapter.notifyItemChanged(position)
                Log.d("RRR", "Update -> ${response.body()!!.data}")
            }

            override fun onFailure(call: Call<ResponseObjectErrors<Employee>>, t: Throwable) {
                fm_loading.visibility = View.INVISIBLE
                Log.d("RRR", t.localizedMessage!!)
            }

        })
    }

}