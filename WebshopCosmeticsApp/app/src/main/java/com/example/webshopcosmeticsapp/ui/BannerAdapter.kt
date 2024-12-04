package com.example.webshopcosmetics.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.webshopcosmetics.model.Banner
import com.example.webshopcosmetics.utils.ImageLoader

@Composable
fun BannerAdapter(banner: Banner) {
    Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Text(text = banner.name ?: "No Name")
        banner.image?.let {
            ImageLoader(it) // Sử dụng ImageLoader để tải ảnh
        }
    }
}
