package com.plcoding.photofindapp.presentation.photo_info

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.plcoding.photofindapp.ui.theme.DarkBlue
import com.ramcosta.composedestinations.annotation.Destination
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
@Destination
fun CompanyInfoScreen(
    symbol: String,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBlue)
            .padding(16.dp)
    ) {
        val (title, author, imgUrl, description, published) = symbol.split(":*:")
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val publishedFormatted = DateTimeFormatter.ofPattern("MMMM dd, yyyy | hh:mma").format(LocalDateTime.parse(published, dateFormatter))
        item {
            GlideImage(model = imgUrl, contentDescription = "", modifier = Modifier.fillMaxSize(), contentScale = ContentScale.FillBounds)
            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = author,
                fontStyle = FontStyle.Italic,
                fontSize = 14.sp,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = HtmlCompat.fromHtml(description, 0).toString(),
                fontSize = 12.sp,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = publishedFormatted,
                fontStyle = FontStyle.Italic,
                fontSize = 12.sp,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}