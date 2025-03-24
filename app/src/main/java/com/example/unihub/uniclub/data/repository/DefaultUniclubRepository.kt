package com.example.unihub.uniclub.data.repository

import ProductModel
import com.example.unihub.core.domain.util.NetworkError
import com.example.unihub.core.domain.util.Result
import com.example.unihub.core.domain.util.map
import com.example.unihub.uniclub.domain.BrandModel
import com.example.unihub.uniclub.domain.CategoryModel
import com.example.unihub.uniclub.domain.UniclubDataSource
import com.example.unihub.uniclub.domain.UniclubRepository
import timber.log.Timber

class DefaultUniclubRepository(private val dataSource: UniclubDataSource) : UniclubRepository {
    override suspend fun getProducts(): Result<List<ProductModel>, NetworkError> {
        return dataSource.getProducts().map { apiResponse ->
            Timber.d("getBrand: ${apiResponse.data}")
            apiResponse.data?.map { productDto ->
                ProductModel(
                        id = productDto.id,
                        name = productDto.name,
                        link = productDto.link,
                        price = productDto.price,
                        categories = productDto.categories
                )
            }
                    ?: emptyList()
        }
    }

    override suspend fun getCategories(): Result<List<CategoryModel>, NetworkError> {
        return dataSource.getCategories().map { apiResponse ->
            apiResponse.data?.map {
                CategoryModel(
                        id = it.id,
                        name = it.name,
                )
            }
                    ?: emptyList()
        }
    }

    override suspend fun getBrands(): Result<List<BrandModel>, NetworkError> {
        return dataSource.getBrands().map { apiResponse ->
            Timber.d("getBrand: ${apiResponse.data}")
            apiResponse.data?.map {
                BrandModel(
                        id = it.id,
                        name = it.name,
                )
            }
                    ?: emptyList()
        }
    }

    override suspend fun login(email: String, password: String): Result<String, NetworkError> {
        return dataSource.login(email, password).map { apiResponse ->
            Timber.d("login: ${apiResponse.data}")
            apiResponse.data ?: ""
        }
    }
}
