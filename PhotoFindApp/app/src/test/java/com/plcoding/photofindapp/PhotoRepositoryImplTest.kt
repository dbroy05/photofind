package com.plcoding.photofindapp

import com.plcoding.photofindapp.data.remote.PhotoApi
import com.plcoding.photofindapp.data.repository.PhotoRepositoryImpl
import com.plcoding.photofindapp.domain.model.Item
import com.plcoding.photofindapp.domain.model.Media
import com.plcoding.photofindapp.domain.model.TaggedPhoto
import com.plcoding.photofindapp.util.Resource
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class PhotoRepositoryImplTest {

    @Mock
    lateinit var photoApi: PhotoApi

    @Before
    fun createRepository() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun testGetPhotos_emptyList() = runTest {
        Mockito.`when`(photoApi.getListings("")).thenReturn(TaggedPhoto())
        val photoRepo = PhotoRepositoryImpl(photoApi)
        photoRepo.getCompanyListings("").collect {
            if (it is Resource.Success) {
                assertEquals(true, it.data?.items?.isEmpty())
            }
        }

    }

    @Test
    fun testGetPhotos_resultList() = runTest {
        Mockito.`when`(photoApi.getListings("")).thenReturn(
            TaggedPhoto(
                description = "This was taken on a beautiful place",
                title = "Nice photo",
                items = listOf(
                    Item(
                        "Edgar Elam Poe",
                        "",
                        "",
                        "What a beautiful photo",
                        "",
                        Media("https://flickr.com"),
                        "",
                        "porcupine",
                        "Most beautiful pic"
                    )
                )
            )
        )
        val photoRepo = PhotoRepositoryImpl(photoApi)
        photoRepo.getCompanyListings("").collect {
            if (it is Resource.Success) {
                assertEquals(false, it.data?.items?.isEmpty())
                assertEquals("This was taken on a beautiful place", it.data?.description)
                assertEquals("Nice photo", it.data?.title)
                it.data?.items?.let { item ->
                    assertEquals("Edgar Elam Poe", item[0].author)
                    assertEquals("What a beautiful photo", item[0].description)
                    assertEquals("Most beautiful pic", item[0].title)
                    assertEquals("porcupine", item[0].tags)
                }

            }
        }

    }
}