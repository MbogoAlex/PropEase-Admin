package com.tms.propease_admin.model

import kotlinx.serialization.Serializable

@Serializable
data class PropertiesResponseBody(
    val statusCode: Int,
    val message: String,
    val data: PropertiesData
)

@Serializable
data class FilteredPropertiesResponseBody(
    val statusCode: Int,
    val message: String,
    val data: FilteredPropertiesData
)

@Serializable
data class FilteredPropertiesData(
    val properties: List<PropertyDetails>
)
@Serializable
data class PropertiesData(
    val property: List<PropertyDetails>
)
@Serializable
data class PropertyDetails(
    val user: PropertyOwner,
    val propertyId: Int,
    val title: String,
    val description: String,
    val category: String,
    val rooms: Int,
    val price: Double,
    val approved: Boolean,
    val paid: Boolean,
    val postedDate: String,
    val deletionTime: String?,
    val features: List<String>,
    val location: PropertyLocation,
    val paymentDetails: PaymentDetails,
    val images: List<PropertyImage>
)

@Serializable
data class PropertyOwner(
    val userId: Int,
    val email: String,
    val phoneNumber: String,
    val profilePic: String?,
    val approved: Boolean,
    val fname: String,
    val lname: String
)

@Serializable
data class PropertyLocation(
    val address: String,
    val county: String,
    val latitude: Double,
    val longitude: Double
)

@Serializable
data class PaymentDetails(
    val id: Int,
    val partnerReferenceID: String?,
    val transactionID: String?,
    val msisdn: String?,
    val partnerTransactionID: String?,
    val payerTransactionID: String?,
    val receiptNumber: String?,
    val transactionAmount: Double?,
    val transactionStatus: String,
    val paymentComplete: Boolean,
    val createdAt: Long,
    val updatedAt: Long
)

@Serializable
data class PropertyImage(
    val id: Int,
    val name: String,
    val approved: Boolean
)

@Serializable
data class PropertyResponseBody(
    val statusCode: Int,
    val message: String,
    val data: PropertyData
)

@Serializable
data class PropertyData(
    val property: PropertyDetails
)

@Serializable
data class PropertyVerificationRequestBody(
    val approvedImagesId: List<Int>
)

@Serializable
data class PropertyVerificationResponseBody(
    val statusCode: Int,
    val message: String
)

