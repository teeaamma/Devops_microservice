package com.devops.plugins

import com.devops.model.Asset
import com.devops.model.AssetRepository
import com.devops.model.AssetType
import io.ktor.http.*
import io.ktor.serialization.JsonConvertException
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureSerialization(repository: AssetRepository) {
    install(ContentNegotiation) {
        json()
    }
    routing {
        route("/assets") {
            get {
                val assets = repository.allAssets()
                call.respond(assets)
            }

            get("/byFigi/{figi}") {
                val figi = call.parameters["figi"]
                if (figi == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }
                val asset = repository.assetByFigi(figi)
                if (asset == null) {
                    call.respond(HttpStatusCode.NotFound)
                    return@get
                }
                call.respond(asset)
            }

            get("/byType/{type}") {
                val typeAsText = call.parameters["type"]
                if (typeAsText == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }
                try {
                    val type = AssetType.valueOf(typeAsText)
                    val assets = repository.assetsByType(type)


                    if (assets.isEmpty()) {
                        call.respond(HttpStatusCode.NotFound)
                        return@get
                    }
                    call.respond(assets)
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest)
                }
            }

            post {
                try {
                    val asset = call.receive<Asset>()
                    repository.addAsset(asset)
                    call.respond(HttpStatusCode.NoContent)
                } catch (ex: IllegalStateException) {
                    call.respond(HttpStatusCode.BadRequest)
                } catch (ex: JsonConvertException) {
                    call.respond(HttpStatusCode.BadRequest)
                }
            }

            put("/updatePrice/{figi}/{price}") {
                val figi = call.parameters["figi"]
                val price = call.parameters["price"]?.toFloatOrNull()
                if (figi == null || price == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@put
                }
                if (repository.updateAssetPrice(figi, price)) {
                    call.respond(HttpStatusCode.NoContent)
                } else {
                    call.respond(HttpStatusCode.NotFound)
                }
            }


            delete("/{figi}") {
                val figi = call.parameters["figi"]
                if (figi == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@delete
                }
                if (repository.removeAsset(figi)) {
                    call.respond(HttpStatusCode.NoContent)
                } else {
                    call.respond(HttpStatusCode.NotFound)
                }
            }
        }
    }
}
