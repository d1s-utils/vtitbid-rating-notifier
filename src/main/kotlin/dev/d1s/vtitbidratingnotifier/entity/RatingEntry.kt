package dev.d1s.vtitbidratingnotifier.entity

data class RatingEntry(
    val position: Int,
    val applicantName: String,
    val registrationNumber: String,
    val certificateType: String,
    val averageScore: String
)
