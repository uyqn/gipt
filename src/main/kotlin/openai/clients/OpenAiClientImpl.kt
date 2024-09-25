package no.uyqn.openai.clients

import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.Serializable
import no.uyqn.config.Configuration
import no.uyqn.config.EnvironmentalVariable
import no.uyqn.openai.OpenAiModel
import no.uyqn.openai.clients.data.ChatRequest
import no.uyqn.openai.clients.data.ChatResponse
import no.uyqn.openai.clients.data.Message

class OpenAiClientImpl(
    private val configuration: Configuration,
    private val model: String = OpenAiModel.GPT4O_MINI,
) : OpenAiClient {
    private val apiKey = configuration.getEnvironmentVariable(EnvironmentalVariable.OPENAI_API_KEY)
    private val apiVersion = "v1"
    private val endpoint = "https://api.openai.com/$apiVersion/"

    @Serializable
    private data class OpenAiChatRequest(
        val model: String = OpenAiModel.GPT4O_MINI,
        val messages: List<Message>,
    )

    override suspend fun chat(request: ChatRequest): ChatResponse {
        val response =
            configuration.createHttpClient().use {
                it
                    .post("$endpoint/chat/completions") {
                        contentType(ContentType.Application.Json)
                        bearerAuth(apiKey)
                        setBody(OpenAiChatRequest(model, messages = request.messages))
                    }
            }
        return response.body()
    }
}
