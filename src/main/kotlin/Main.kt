package no.uyqn

import io.github.cdimascio.dotenv.dotenv
import no.uyqn.config.Configuration

fun main() {
    val config = Configuration(dotenv = dotenv { directory = System.getProperty("user.dir") })
    println(config.getEnvironmentVariable(Configuration.OPENAI_API_KEY))
}
