package com.example.networking_retrofit_kotlin.api

import com.example.networking_retrofit_kotlin.model.Employee
import com.example.networking_retrofit_kotlin.api.model.ResponseListErrors
import com.example.networking_retrofit_kotlin.api.model.ResponseObjectErrors
import retrofit2.Call
import retrofit2.http.*

@JvmSuppressWildcards
interface ApiService {

    @GET("employees")
    fun getEmployees(): Call<ResponseListErrors<Employee>>

    @GET("employee/{id}")
    fun getEmployee(@Path("id") id: Int): Call<ResponseObjectErrors<Employee>>

    @POST("create")
    fun createEmployee(@Body employee: Employee): Call<ResponseObjectErrors<Employee>>

    @POST("create")
    @FormUrlEncoded
    fun createEmployee(@Field("employee_name") employee: String): Call<ResponseObjectErrors<Employee>>

    @DELETE("delete/{id}")
    fun deleteEmployee(@Path("id") id: Int): Call<ResponseObjectErrors<String>>

    @PUT("update/{id}")
    fun updateEmployee(@Path("id") id: Int, @Body employee: Employee): Call<ResponseObjectErrors<Employee>>
}