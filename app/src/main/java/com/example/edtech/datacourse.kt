package com.example.edtech

import com.google.firebase.database.PropertyName


data class datacour(
    @PropertyName("image") var image: String? = null,  // Correct annotation for Firestore
    var price: String? = null,
    var name: String? = null,
    var id: String? = null
)