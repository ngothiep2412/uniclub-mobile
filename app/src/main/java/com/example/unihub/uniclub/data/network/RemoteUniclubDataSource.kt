package com.example.unihub.uniclub.data.network

import ProductModel
import com.example.unihub.BuildConfig
import com.example.unihub.core.data.networking.constructUrl
import com.example.unihub.core.data.networking.safeCall
import com.example.unihub.core.domain.util.NetworkError
import com.example.unihub.core.domain.util.Result
import com.example.unihub.uniclub.domain.ApiResponseDTO
import com.example.unihub.uniclub.domain.BrandModel
import com.example.unihub.uniclub.domain.CategoryModel
import com.example.unihub.uniclub.domain.LoginRequest
import com.example.unihub.uniclub.domain.UniclubDataSource
import com.example.unihub.uniclub.util.AppPreferencesDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.headers
import io.ktor.util.toMap
import kotlinx.coroutines.flow.first
import timber.log.Timber

class RemoteUniclubDataSource(
    private val httpClient: HttpClient,
    private val appPreferencesDataSource: AppPreferencesDataSource,
) : UniclubDataSource {

    override suspend fun login(
        email: String,
        password: String
    ): Result<ApiResponseDTO<String>, NetworkError> {
        return safeCall<ApiResponseDTO<String>> {
            Timber.d("network ${BuildConfig.BASE_URL}")
            httpClient.post(
                urlString = constructUrl("/authen")
            ) {
                setBody(LoginRequest(email, password))
            }
        }
    }


    override suspend fun getProducts(): Result<ApiResponseDTO<List<ProductModel>>, NetworkError> {
        // Lấy token từ AppPreferencesDataSource
        val token = appPreferencesDataSource.getAuthToken()
            .first() // Đảm bảo bạn thu thập giá trị đầu tiên từ Flow

        // Kiểm tra token
        if (token.isNullOrEmpty()) {
            Timber.e("Token is null or empty")
            return Result.Error(NetworkError.UNAUTHORIZED)
        }

        return safeCall<ApiResponseDTO<List<ProductModel>>> {
            Timber.d("token $token")
            httpClient.get(urlString = constructUrl("/product/1")) {
                header("Authorization", "Bearer $token")
            }
        }
    }

    override suspend fun getCategories(): Result<ApiResponseDTO<List<CategoryModel>>, NetworkError> {
        return safeCall<ApiResponseDTO<List<CategoryModel>>> {
            Timber.d("network ${BuildConfig.BASE_URL}")
            httpClient.get(
                urlString = constructUrl("/category")
            )
        }
    }

    override suspend fun getBrands(): Result<ApiResponseDTO<List<BrandModel>>, NetworkError> {
        return safeCall<ApiResponseDTO<List<BrandModel>>> {
            Timber.d("network ${BuildConfig.BASE_URL}")
            httpClient.get(
                urlString = constructUrl("/brand")
            )
        }
    }
}