package com.ntg.movieapiappcompose.data.remote

import com.ntg.movieapiappcompose.data.model.Movie
import com.ntg.movieapiappcompose.data.model.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("/3/movie/upcoming?language=en-US")
    suspend fun getUpcomingList(
        @Query("page") page: Int,
    ):Response<ResponseBody<List<Movie>?>>
}