package com.plcoding.photofindapp.presentation.photo_listings

sealed class PhotoListingsEvent {
    data class OnSearchQueryChange(val query: String): PhotoListingsEvent()
}
