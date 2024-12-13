package com.devops.db

import com.devops.model.Asset
import com.devops.model.AssetType
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object AssetTable : IntIdTable("asset") {
    val figi = varchar("figi", 50)
    val name = varchar("name", 50)
    val type = varchar("type", 50)
    val price = float("price")
}

class AssetDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<AssetDAO>(AssetTable)

    var figi by AssetTable.figi
    var name by AssetTable.name
    var type by AssetTable.type
    var price by AssetTable.price
}

suspend fun <T> suspendTransaction(block: Transaction.() -> T): T =
    newSuspendedTransaction(Dispatchers.IO, statement = block)

fun daoToModel(dao: AssetDAO) = Asset(
    dao.figi,
    dao.name,
    AssetType.valueOf(dao.type),
    dao.price
)