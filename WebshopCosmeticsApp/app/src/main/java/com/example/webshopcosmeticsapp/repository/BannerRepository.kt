package com.example.webshopcosmetics.repository

import com.example.webshopcosmetics.model.Banner
import com.example.webshopcosmetics.network.ApiService

class BannerRepository(private val apiService: ApiService) {
    suspend fun getBanners(): List<Banner> {
        val response = apiService.getBanners()
        return if (response.isSuccessful) {
            // Lấy danh sách banner từ body của response
            response.body()?.banners ?: emptyList() // Trả về danh sách từ body nếu thành công, nếu null thì trả về danh sách rỗng
        } else {
            emptyList() // Nếu request thất bại, trả về danh sách rỗng
        }
    }
}
