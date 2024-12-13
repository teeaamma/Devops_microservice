package com.devops.model

interface AssetRepository {
    suspend fun allAssets(): List<Asset>
    suspend fun assetsByType(assetType: AssetType): List<Asset>
    suspend fun assetByFigi(figi: String): Asset?
    suspend fun addAsset(asset: Asset)
    suspend fun updateAssetPrice(figi: String, price: Float): Boolean
    suspend fun removeAsset(figi: String): Boolean
}