package com.example.networking_retrofit_kotlin.api.model

data class ResponseObjectErrors<T>(
    var status: String,
    var data: T,
    var message: String
) {
}