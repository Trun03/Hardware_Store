package com.example.webshopcosmetics.utils

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import coil.compose.rememberImagePainter

@Composable
fun ImageLoader(url: String) {
    val painter: Painter = rememberImagePainter(url)
    Image(painter = painter, contentDescription = null)
}
