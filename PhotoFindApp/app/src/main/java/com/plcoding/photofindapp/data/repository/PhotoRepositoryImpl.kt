package com.plcoding.photofindapp.data.repository

import com.plcoding.photofindapp.data.remote.PhotoApi
import com.plcoding.photofindapp.domain.model.TaggedPhoto
import com.plcoding.photofindapp.domain.repository.PhotoRepository
import com.plcoding.photofindapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotoRepositoryImpl @Inject constructor(
    private val api: PhotoApi,
): PhotoRepository {


    override suspend fun getCompanyListings(
        tags: String
    ): Flow<Resource<TaggedPhoto>> {
        return flow {
            emit(Resource.Loading(true))

            try {
                emit(Resource.Success(api.getListings(tags)))
            } catch(e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
            }
        }
    }
}