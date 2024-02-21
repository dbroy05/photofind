package com.plcoding.photofindapp.presentation.photo_listings

import com.plcoding.photofindapp.domain.model.Item

data class PhotoListingsState(
    val taggedPhotoItems: List<Item> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val searchQuery: String = ""
)
