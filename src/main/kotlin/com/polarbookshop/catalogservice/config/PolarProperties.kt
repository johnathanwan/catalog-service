package com.polarbookshop.catalogservice.config

import org.springframework.boot.context.properties.*

@ConfigurationProperties(prefix = "polar")
data class PolarProperties(
    /** A message to welcome users. */
    val greeting: String
)
//hot reload of config properties from git is not able to update the polar.greeting property