package com.example.webshopcosmetics.network

import com.example.webshopcosmetics.model.Banner
import com.example.webshopcosmetics.model.BannerResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("get_banners.php")
    suspend fun getBanners(): Response<BannerResponse>

    @POST("create_banner.php")
    suspend fun createBanner(@Body banner: Banner): Response<BannerResponse>

    @PUT("update_banner.php/{banner_id}")
    suspend fun updateBanner(
        @Path("banner_id") bannerId: String,
        @Body banner: Banner
    ): Response<Void>

    @DELETE("delete_banner.php")
    suspend fun deleteBanner(@Query("banner_id") bannerId: String): Response<Map<String, Any>>
}
