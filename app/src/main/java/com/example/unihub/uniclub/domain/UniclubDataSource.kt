package com.example.unihub.uniclub.domain

import ProductModel
import com.example.unihub.core.domain.util.NetworkError
import com.example.unihub.core.domain.util.Result

interface UniclubDataSource {
    suspend fun login(email: String, password: String) : Result<ApiResponseDTO<String>, NetworkError>

    suspend fun getProducts(): Result<ApiResponseDTO<List<ProductModel>>, NetworkError>

    suspend fun getCategories(): Result<ApiResponseDTO<List<CategoryModel>>, NetworkError>

    suspend fun getBrands(): Result<ApiResponseDTO<List<BrandModel>>, NetworkError>
}