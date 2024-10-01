package no.uyqn.config

import io.github.cdimascio.dotenv.Dotenv
import io.github.cdimascio.dotenv.dotenv

class Configuration(
    private val dotenv: Dotenv = dotenv { directory = System.getProperty("user.dir") },
) {
    fun getEnvironmentVariable(key: String): String = dotenv[key] ?: throw MissingEnvironmentalKeyException(key)
}
