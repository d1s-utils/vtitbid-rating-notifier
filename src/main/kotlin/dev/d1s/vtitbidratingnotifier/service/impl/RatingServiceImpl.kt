package dev.d1s.vtitbidratingnotifier.service.impl

import dev.d1s.vtitbidratingnotifier.constant.COST_RECOVERY_SPAN_VALUE
import dev.d1s.vtitbidratingnotifier.constant.SPECIALIZATION_SPAN_VALUE
import dev.d1s.vtitbidratingnotifier.constant.VTITBID_RATING_LOCATION
import dev.d1s.vtitbidratingnotifier.entity.Rating
import dev.d1s.vtitbidratingnotifier.entity.RatingEntry
import dev.d1s.vtitbidratingnotifier.properties.VtitbidRatingNotifierConfigurationProperties
import dev.d1s.vtitbidratingnotifier.service.RatingService
import org.jsoup.Jsoup
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RatingServiceImpl : RatingService {

    @set:Autowired
    lateinit var properties: VtitbidRatingNotifierConfigurationProperties

    override fun requestAndParseRating(): Rating? {
        val document = Jsoup.connect(VTITBID_RATING_LOCATION).get()
        val elements = document.body().children()

        elements.forEachIndexed { i, e ->
            if (e.tagName() == "p") {
                val spans = e.getElementsByTag("span")

                val costRecovery = properties.costRecovery

                val specialization = spans.getOrNull(1)?.text()


                if (spans.firstOrNull()?.text() == SPECIALIZATION_SPAN_VALUE
                    && specialization?.contains(properties.specialization) == true
                    && costRecovery == (elements[i + 1]
                        .getElementsByTag("span")
                        .firstOrNull()
                        ?.getElementsByTag("span")
                        ?.firstOrNull()
                        ?.text() == COST_RECOVERY_SPAN_VALUE)
                ) {
                    return Rating(
                        specialization,
                        costRecovery,
                        elements[
                                i + if (costRecovery) {
                                    3
                                } else {
                                    2
                                }
                        ].getElementsByTag("span").let {
                            "${it[0].text()} ${it[1].text()} "
                        },
                        buildSet {
                            elements[
                                    i + if (costRecovery) {
                                        5
                                    } else {
                                        4
                                    }
                            ].getElementsByTag("tbody")
                                .first()!!
                                .getElementsByTag("tr")
                                .drop(1)
                                .forEachIndexed { i, row ->
                                    val columns = row.getElementsByTag("td")
                                        .drop(1)
                                        .map {
                                            it.getElementsByTag("p")
                                                .first()!!
                                                .getElementsByTag("span")
                                                .first()
                                                ?.text()
                                        }

                                    if (columns.first() != null) {
                                        add(
                                            RatingEntry(
                                                i + 1,
                                                columns[0]!!,
                                                columns[1]!!,
                                                columns[2]!!,
                                                columns[3]!!
                                            )
                                        )
                                    }
                                }
                        }
                    )
                }
            }
        }

        return null
    }
}