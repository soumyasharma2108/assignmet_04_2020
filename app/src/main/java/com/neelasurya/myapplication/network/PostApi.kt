package com.neelasurya.myapplication.network

import com.neelasurya.myapplication.model.UserData
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * The interface which provides methods to get result of webservices
 */
interface PostApi {
    /**
     * Get the list of the pots from the API
     */
    @GET("/api")
    fun getPosts(@Query("results") result:Int ): Observable<UserData>
}