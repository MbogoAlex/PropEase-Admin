package com.tms.propease_admin.model

import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponseBody(
    val statusCode: Int,
    val message: String,
    val data: CategoryData
)

@Serializable
data class CategoryData(
    val categories: List<CategoryItem>
)

@Serializable
data class CategoryItem(
    val id: Int,
    val name: String
)
