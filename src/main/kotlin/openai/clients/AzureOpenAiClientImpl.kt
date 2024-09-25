package no.uyqn.openai.clients

import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import no.uyqn.config.Configuration
import no.uyqn.config.EnvironmentalVariable
import no.uyqn.openai.OpenAiModel

class AzureOpenAiClientImpl(
    private val configuration: Configuration,
    model: String = OpenAiModel.GPT4O_MINI,
) : OpenAiClient {
    private val apiKey = configuration.getEnvironmentVariable(EnvironmentalVariable.OPENAI_API_KEY)
    private val resource = configuration.getEnvironmentVariable(EnvironmentalVariable.AZURE_OPENAI_RESOURCE)
    private val endpoint = "https://$resource.openai.azure.com/openai/deployments/$model"
    private val apiVersion = configuration.getEnvironmentVariable(EnvironmentalVariable.AZURE_OPENAI_API_VERSION)

    override suspend fun chat(request: ChatRequest): ChatResponse {
        val response =
            configuration.createHttpClient().use {
                it
                    .post("$endpoint/chat/completions?api-version=$apiVersion") {
                        contentType(ContentType.Application.Json)
                        header("api-key", apiKey)
                        setBody(request)
                    }
            }
        return response.body()
    }
}
