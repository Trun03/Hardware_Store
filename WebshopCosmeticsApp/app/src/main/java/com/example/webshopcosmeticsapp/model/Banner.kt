package com.example.webshopcosmetics.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class Banner(
    val banner_id: Long,      // Assuming ID is numeric
    val name: String?,        // Nullable name
    val image: String,        // Image URL, required
    val status: String,      // Representing bit(1) as Boolean
    val link: String?,        // Nullable link
    val option: Int           // Assuming option is a number
) : Parcelable

data class BannerResponse(
    val success: Boolean,      // Status of the API call
    val banners: List<Banner>  // List of banners
) : Serializable
