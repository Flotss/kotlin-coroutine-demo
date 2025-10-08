package com.isep.coroutineDemo.data

import com.google.gson.annotations.SerializedName

data class UserResponse(
    val results: List<User>
)

data class User(
    val name: Name,
    val email: String,
    val phone: String,
    val picture: Picture,
    val location: Location
)

data class Name(
    val title: String,
    val first: String,
    val last: String
) {
    fun fullName() = "$title $first $last"
}

data class Picture(
    val large: String,
    val medium: String,
    val thumbnail: String
)

data class Location(
    val city: String,
    val country: String
)

