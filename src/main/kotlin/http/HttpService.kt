package no.uyqn.http

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class HttpService {
    val logger: Logger = LoggerFactory.getLogger(this::class.java)

    fun createHttpClient(): HttpClient =
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        isLenient = true
                    },
                )
            }
            expectSuccess = true
        }

    suspend inline fun <reified T> post(
        url: String,
        body: T,
        block: HttpRequestBuilder.() -> Unit,
    ): HttpResponse {
        val client = createHttpClient()
        try {
            return client
                .post(url) {
                    contentType(ContentType.Application.Json)
                    setBody(body)
                    block()
                }.let {
                    val jsonFormatter = Json { prettyPrint = true }
                    val response =
                        jsonFormatter.encodeToString(
                            JsonObject.serializer(),
                            jsonFormatter.parseToJsonElement(it.bodyAsText()).jsonObject,
                        )
                    logger.debug("Azure OpenAI response: \n${response.prependIndent("|   ").trimMargin()}")
                    it
                }
        } catch (e: ClientRequestException) {
            throw e
        } finally {
            client.close()
        }
    }
}
