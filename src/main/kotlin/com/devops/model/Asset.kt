package com.devops.model

import kotlinx.serialization.Serializable

@Serializable
data class Asset(
    val figi: String,
    val name: String,
    val type: AssetType,
    var price: Float
)
