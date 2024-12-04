package com.example.webshopcosmetics

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import coil.compose.rememberImagePainter
import com.example.webshopcosmetics.model.Banner
import com.example.webshopcosmetics.network.ApiClient
import com.example.webshopcosmetics.network.ApiService
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val apiService: ApiService by lazy { ApiClient.apiService }
    private var banners by mutableStateOf<List<Banner>>(emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(
                colorScheme = lightColorScheme(
                    primary = Color(0xFF1976D2),
                    secondary = Color(0xFF9E9E9E),
                    tertiary = Color(0xFF43A047)
                )
            ) {
                BannerScreen()
            }
        }
    }

    @Composable
    fun BannerScreen() {
        LaunchedEffect(Unit) {
            loadBanners()
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Thêm nút "Add" vào đầu màn hình
            Button(
                onClick = { navigateToAddBanner() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier
                    .padding(bottom = 16.dp) // Căn chỉnh khoảng cách dưới nút
            ) {
                Text("Add New Banner")
            }

            Text(
                text = "BANNERS LIST",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(banners) { banner ->
                    BannerItem(
                        banner = banner,
                        onUpdate = { updateBanner(banner) },
                        onDelete = { deleteBanner(banner.banner_id.toString()) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navigateToAddBanner() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text("Add New Banner")
            }
        }
    }

    private fun loadBanners() {
        lifecycleScope.launch {
            val response = apiService.getBanners()
            if (response.isSuccessful) {
                response.body()?.let { bannerResponse ->
                    banners = bannerResponse.banners
                } ?: run {
                    banners = emptyList()
                }
            } else {
                banners = emptyList()
            }
        }
    }

    private fun updateBanner(banner: Banner) {
        val intent = Intent(this, UpdateBannerActivity::class.java)
        intent.putExtra("banner", banner)
        startActivity(intent)
    }

    private fun deleteBanner(bannerId: String) {
        lifecycleScope.launch {
            val response = apiService.deleteBanner(bannerId)
            if (response.isSuccessful) {
                loadBanners()
            } else {
                // Handle error
            }
        }
    }

    private fun navigateToAddBanner() {
        val intent = Intent(this, AddBannerActivity::class.java)
        startActivity(intent)
    }

    @Composable
    fun BannerItem(banner: Banner, onUpdate: () -> Unit, onDelete: () -> Unit) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = rememberImagePainter(banner.image),
                    contentDescription = banner.name ?: "Banner Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Name: ${banner.name ?: "No Name"}", style = MaterialTheme.typography.titleMedium)
                Text(text = "Status: ${if (banner.status == "1") "Active" else "Inactive"}", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = onUpdate,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = MaterialTheme.colorScheme.onSecondary
                        )
                    ) {
                        Text("Update")
                    }
                    Button(
                        onClick = onDelete,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.tertiary,
                            contentColor = MaterialTheme.colorScheme.onTertiary
                        )
                    ) {
                        Text("Delete")
                    }
                }
            }
        }
    }
}
