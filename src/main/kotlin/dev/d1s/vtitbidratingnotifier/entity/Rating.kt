package dev.d1s.vtitbidratingnotifier.entity

data class Rating(
    val specialization: String,
    val costRecovery: Boolean,
    // не будем использовать здесь специальный тип данных.
    val date: String,
    val entries: Set<RatingEntry>
)