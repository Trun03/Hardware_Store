package com.example.webshopcosmetics

import android.content.Intent
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
import androidx.compose.ui.Alignment

class AddBannerActivity : ComponentActivity() {
    private val apiService = ApiClient.apiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                AddBannerScreen(onAdd = { newBanner ->
                    addBanner(newBanner)
                })
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun AddBannerScreen(onAdd: (Banner) -> Unit) {
        var name by remember { mutableStateOf("") }
        var image by remember { mutableStateOf("") }
        var link by remember { mutableStateOf("") }
        var status by remember { mutableStateOf("0") } // Default to "Inactive"

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

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Status:")
                Spacer(modifier = Modifier.width(8.dp))
                Checkbox(
                    checked = (status == "1"),
                    onCheckedChange = { isChecked ->
                        status = if (isChecked) "1" else "0"
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (name.isBlank() || image.isBlank() || link.isBlank()) {
                        Toast.makeText(this@AddBannerActivity, "Please fill all fields correctly", Toast.LENGTH_SHORT).show()
                    } else {
                        val newBanner = Banner(
                            banner_id = 0L,
                            name = name,
                            image = image,
                            status = status, // Use String "1" or "0"
                            link = link,
                            option = 0
                        )
                        onAdd(newBanner)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Submit")
            }
        }
    }

    private fun addBanner(banner: Banner) {
        lifecycleScope.launch {
            try {
                val response = apiService.createBanner(banner)
                if (response.isSuccessful) {
                    Toast.makeText(this@AddBannerActivity, "Banner added successfully", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@AddBannerActivity, UpdateBannerActivity::class.java)
                    intent.putExtra("banner", banner)
                    startActivity(intent)
                    finish()
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                    Toast.makeText(this@AddBannerActivity, "Failed to add banner: $errorMessage", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@AddBannerActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
