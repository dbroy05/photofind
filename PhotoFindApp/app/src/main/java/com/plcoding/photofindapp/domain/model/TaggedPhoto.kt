package com.plcoding.photofindapp.domain.model

data class TaggedPhoto(
    val description: String = "",
    val generator: String = "",
    val items: List<Item> = emptyList(),
    val link: String = "",
    val modified: String = "",
    val title: String = ""
)