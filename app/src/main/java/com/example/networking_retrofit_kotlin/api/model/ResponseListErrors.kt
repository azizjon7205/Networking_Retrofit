package com.example.networking_retrofit_kotlin.api.model

data class ResponseListErrors<T>(
    var status: String,
    var data: List<T>,
    var message: String
) {
}