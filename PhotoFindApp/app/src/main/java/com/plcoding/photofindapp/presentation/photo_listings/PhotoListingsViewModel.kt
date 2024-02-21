package com.plcoding.photofindapp.presentation.photo_listings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.photofindapp.domain.repository.PhotoRepository
import com.plcoding.photofindapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoListingsViewModel @Inject constructor(
    private val repository: PhotoRepository
): ViewModel() {

    private val _uiStateFlow = MutableStateFlow(PhotoListingsState())
    val uiStateFlow: StateFlow<PhotoListingsState> = _uiStateFlow

    private var searchJob: Job? = null

    init {
        getCompanyListings()
    }

    fun onEvent(event: PhotoListingsEvent) {
        when(event) {
            is PhotoListingsEvent.OnSearchQueryChange -> {
                _uiStateFlow.value = _uiStateFlow.value.copy(searchQuery = event.query)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    getCompanyListings()
                }
            }
        }
    }

    private fun getCompanyListings(
        query: String = _uiStateFlow.value.searchQuery.lowercase(),
    ) {
        viewModelScope.launch {
            repository
                .getCompanyListings(query)
                .collect { result ->
                    when(result) {
                        is Resource.Success -> {
                            result.data?.let { listings ->
                                _uiStateFlow.value = _uiStateFlow.value.copy(
                                    taggedPhotoItems = listings.items,
                                    isLoading = false
                                )
                            }
                        }
                        is Resource.Error -> Unit
                        is Resource.Loading -> {
                            _uiStateFlow.value = _uiStateFlow.value.copy(
                                isLoading = true
                            )
                        }
                    }
                }
        }
    }
}