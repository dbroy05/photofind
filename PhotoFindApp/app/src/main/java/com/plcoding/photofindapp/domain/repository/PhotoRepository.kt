package com.plcoding.photofindapp.domain.repository

import com.plcoding.photofindapp.domain.model.TaggedPhoto
import com.plcoding.photofindapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface PhotoRepository {

    suspend fun getCompanyListings(
        tags: String
    ): Flow<Resource<TaggedPhoto>>
}