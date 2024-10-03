package no.uyqn.openai.clients

import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import kotlinx.serialization.Serializable
import no.uyqn.config.Configuration
import no.uyqn.config.EnvironmentalVariable
import no.uyqn.http.HttpService
import no.uyqn.openai.OpenAiModel
import no.uyqn.openai.clients.data.ChatRequest
import no.uyqn.openai.clients.data.ChatResponse
import no.uyqn.openai.clients.data.Message

class OpenAiClientImpl(
    configuration: Configuration,
    private val model: String = OpenAiModel.GPT4O_MINI,
) : OpenAiClient {
    private val apiKey = configuration.getEnvironmentVariable(EnvironmentalVariable.OPENAI_API_KEY)
    private val apiVersion = "v1"
    private val endpoint = "https://api.openai.com/$apiVersion/"
    private val httpService = HttpService()

    @Serializable
    private data class OpenAiChatRequest(
        val model: String = OpenAiModel.GPT4O_MINI,
        val messages: List<Message>,
    )

    override suspend fun chat(request: ChatRequest): ChatResponse {
        val response =
            httpService.post(url = "$endpoint/chat/completions", body = OpenAiChatRequest(model = model, messages = request.messages)) {
                bearerAuth(apiKey)
            }
        return response.body()
    }
}
