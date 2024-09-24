package no.uyqn.config

import io.github.cdimascio.dotenv.Dotenv

class Configuration(
    private val dotenv: Dotenv,
) {
    fun getEnvironmentVariable(key: String): String = dotenv[key] ?: throw MissingEnvironmentalKeyException(key)

    companion object {
        const val OPENAI_API_KEY = "OPENAI_API_KEY"
        const val AZURE_OPENAI_RESOURCE = "AZURE_OPENAI_RESOURCE"
    }
}
