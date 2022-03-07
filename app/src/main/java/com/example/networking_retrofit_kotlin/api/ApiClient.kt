package com.example.networking_retrofit_kotlin.api

import com.example.networking_retrofit_kotlin.model.Employee
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    val IS_TESTER = false
    val SERVER_DEVELOPMENT = "https://6221f0e6666291106a17fe42.mockapi.io/"
    val SERVER_PRODUCTION = "https://dummy.restapiexample.com/api/v1/"

    fun server() = if (IS_TESTER) SERVER_DEVELOPMENT else SERVER_PRODUCTION

    val retrofit = Retrofit.Builder()
        .baseUrl(server())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val employeeService = retrofit.create(Employee::class.java)

    fun <T> createService(service: Class<T>): T{
        return retrofit.create(service)
    }
}