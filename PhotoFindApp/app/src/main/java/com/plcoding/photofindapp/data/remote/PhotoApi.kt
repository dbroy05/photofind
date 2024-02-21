package com.plcoding.photofindapp.data.remote

import com.plcoding.photofindapp.domain.model.TaggedPhoto
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotoApi {

    @GET("services/feeds/photos_public.gne?format=json")
    suspend fun getListings(
        @Query("tags") tags: String,
        @Query("nojsoncallback") nojsoncallback:Int = 1
    ): TaggedPhoto


    companion object {
        const val BASE_URL = "https://api.flickr.com/"
    }
}