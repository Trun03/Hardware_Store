package com.example.webshopcosmetics

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.webshopcosmetics.model.Banner
import com.example.webshopcosmetics.network.ApiClient
import kotlinx.coroutines.launch
import androidx.compose.material3.ExperimentalMaterial3Api

class UpdateBannerActivity : ComponentActivity() {

    private val apiService = ApiClient.apiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val banner = intent.getParcelableExtra<Banner>("banner")
        if (banner == null) {
            Toast.makeText(this, "No banner data", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        setContent {
            MaterialTheme {
                UpdateBannerScreen(banner = banner, onUpdate = { updatedBanner ->
                    updateBanner(updatedBanner)
                })
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun UpdateBannerScreen(banner: Banner, onUpdate: (Banner) -> Unit) {
        var name by remember { mutableStateOf(banner.name ?: "") }
        var image by remember { mutableStateOf(banner.image) }
        var link by remember { mutableStateOf(banner.link ?: "") }
        var status by remember { mutableStateOf(banner.status.toBoolean()) }  // Chuyển từ String sang Boolean

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = image,
                onValueChange = { image = it },
                label = { Text("Image URL") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = link,
                onValueChange = { link = it },
                label = { Text("Link") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Đảm bảo status là String khi gán lại
            OutlinedTextField(
                value = status.toString(),  // Chuyển thành String để nhập vào
                onValueChange = { status = it == "1" },  // Chuyển từ "1" hoặc "0" thành Boolean
                label = { Text("Status (1 for active, 0 for inactive)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val updatedBanner = banner.copy(
                        name = name,
                        image = image,
                        link = link,
                        status = if (status) "1" else "0"  // Chuyển status lại thành String ("1" hoặc "0")
                    )
                    onUpdate(updatedBanner)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Submit")
            }
        }
    }

    private fun updateBanner(banner: Banner) {
        lifecycleScope.launch {
            try {
                // Chuyển banner_id từ Long thành String
                val response = apiService.updateBanner(banner.banner_id.toString(), banner)
                if (response.isSuccessful) {
                    Toast.makeText(this@UpdateBannerActivity, "Banner updated successfully", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@UpdateBannerActivity, "Failed to update banner: ${response.errorBody()?.string()}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@UpdateBannerActivity, "An error occurred: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
