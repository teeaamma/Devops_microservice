package com.devops.model

import com.devops.db.AssetDAO
import com.devops.db.AssetTable
import com.devops.db.daoToModel
import com.devops.db.suspendTransaction
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.update

class AssetRepositoryImpl : AssetRepository {
    override suspend fun allAssets(): List<Asset> = suspendTransaction {
        AssetDAO.all().map(::daoToModel)
    }

    override suspend fun assetsByType(assetType: AssetType): List<Asset> = suspendTransaction {
        AssetDAO
            .find { (AssetTable.type eq assetType.toString()) }
            .map(::daoToModel)
    }

    override suspend fun assetByFigi(figi: String): Asset? = suspendTransaction {
        AssetDAO
            .find { (AssetTable.figi eq figi) }
            .limit(1)
            .map(::daoToModel)
            .firstOrNull()
    }

    override suspend fun addAsset(asset: Asset): Unit = suspendTransaction {
        AssetDAO.new {
            figi = asset.figi
            name = asset.name
            type = asset.type.toString()
            price = asset.price
        }
    }

    override suspend fun updateAssetPrice(figi: String, price: Float): Boolean = suspendTransaction {
        val updated = AssetTable.update({ AssetTable.figi eq figi }) {
            it[AssetTable.price] = price
        }
        updated == 1
    }

    override suspend fun removeAsset(figi: String): Boolean = suspendTransaction {
        val rowsDeleted = AssetTable.deleteWhere {
            AssetTable.figi eq figi
        }
        rowsDeleted == 1
    }
}