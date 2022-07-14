package dev.d1s.vtitbidratingnotifier.service

import dev.d1s.vtitbidratingnotifier.entity.Rating

interface RatingService {

    fun requestAndParseRating(): Rating
}