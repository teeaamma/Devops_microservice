package com.devops

import com.devops.model.AssetRepositoryImpl
import com.devops.plugins.configureDatabases
import com.devops.plugins.configureRouting
import com.devops.plugins.configureSerialization
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.cio.EngineMain.main(args)
}

fun Application.module() {
    val repository = AssetRepositoryImpl()

    configureSerialization(repository)
    configureDatabases(environment.config)
    configureRouting()
}
