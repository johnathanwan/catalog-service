package com.polarbookshop.catalogservice

import org.springframework.boot.*
import org.springframework.boot.autoconfigure.*
import org.springframework.boot.context.properties.*

@SpringBootApplication
@ConfigurationPropertiesScan
class CatalogServiceApplication

fun main(args: Array<String>) {
	runApplication<CatalogServiceApplication>(*args)
}
