package com.flagos.framework.home.mapper

import com.flagos.data.model.ResultsResponse
import com.flagos.data.utils.NORMAL_DATE
import com.flagos.data.utils.UI_DATE
import com.flagos.data.utils.getFormattedDate
import com.flagos.framework.home.mapper.MostPopularMoviesUiMapper.Companion.BLANK
import com.flagos.framework.home.mapper.MostPopularMoviesUiMapper.Companion.DOT
import junit.framework.TestCase
import org.junit.Test

class MostPopularMoviesUiMapperTest : TestCase() {

    private val mostPopularMoviesUiMapper = MostPopularMoviesUiMapper()

    private val resultResponseId = 0
    private val resultResponsePath = "Mock/Path"
    private val resultResponseTitle = "Mock Movie"
    private val resultResponseReleaseDate = "2021-01-01"
    private val resultResponseVoteAverage = 9.0

    @Test
    fun testMapPopularItemList() {
        val mostPopularItem = mostPopularMoviesUiMapper.toMostPopularItemList(getMostPopularResponseListMock())
        with(mostPopularItem[0]) {
            assertEquals(id, resultResponseId)
            assertEquals(posterPath, resultResponsePath)
            assertEquals(title, resultResponseTitle)
            assertEquals(releaseDate, getFormattedDate(resultResponseReleaseDate, NORMAL_DATE, UI_DATE))
            assertEquals(rating, resultResponseVoteAverage.toString().replace(DOT, BLANK).toInt())
        }
    }

    private fun getMostPopularResponseListMock(): List<ResultsResponse> {
        return listOf(
            ResultsResponse(
                adult = false,
                backdropPath = resultResponsePath,
                genreIds = listOf(1, 2, 3),
                id = resultResponseId,
                originalLanguage = "English",
                originalTitle = "Mock Original Movie",
                overview = "Mock Overview",
                popularity = 9.0,
                posterPath = resultResponsePath,
                releaseDate = resultResponseReleaseDate,
                title = resultResponseTitle,
                video = false,
                voteAverage = resultResponseVoteAverage,
                voteCount = 5000
            )
        )
    }
}
