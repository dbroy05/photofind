package com.plcoding.photofindapp.presentation.photo_listings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.plcoding.photofindapp.presentation.destinations.CompanyInfoScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
@Destination(start = true)
fun PhotoListingsScreen(
    navigator: DestinationsNavigator,
    viewModel: PhotoListingsViewModel = hiltViewModel()
) {
    //val state = viewModel.state
    val uiState by viewModel.uiStateFlow.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        OutlinedTextField(
            value = uiState.searchQuery,
            onValueChange = {
                viewModel.onEvent(
                    PhotoListingsEvent.OnSearchQueryChange(it)
                )
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            placeholder = {
                Text(text = "Search photos...")
            },
            maxLines = 1,
            singleLine = true
        )
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator()
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 128.dp)
                    //columns = GridCells.Fixed(3)
                ) {
                    items(uiState.taggedPhotoItems.size) { photoItem ->
                        GlideImage(
                            model = uiState.taggedPhotoItems[photoItem].media.m,
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    val delim = ":*:"
                                    val selectItem = uiState.taggedPhotoItems[photoItem]
                                    val selectPhotoMeta =
                                        "${selectItem.title}$delim${selectItem.author}$delim${selectItem.media.m}$delim${selectItem.description}$delim${selectItem.published}"
                                    navigator.navigate(
                                        CompanyInfoScreenDestination(selectPhotoMeta)
                                    )
                                }
                        )
                    }
                }
            }

        }
    }
}