package no.uyqn.openai.clients

import io.ktor.client.call.body
import io.ktor.client.request.header
import no.uyqn.config.Configuration
import no.uyqn.config.EnvironmentalVariable
import no.uyqn.http.HttpService
import no.uyqn.openai.OpenAiModel
import no.uyqn.openai.clients.data.ChatRequest
import no.uyqn.openai.clients.data.ChatResponse

class AzureOpenAiClientImpl(
    configuration: Configuration,
    model: String = OpenAiModel.GPT4O_MINI,
) : OpenAiClient {
    private val apiKey = configuration.getEnvironmentVariable(EnvironmentalVariable.OPENAI_API_KEY)
    private val resource = configuration.getEnvironmentVariable(EnvironmentalVariable.AZURE_OPENAI_RESOURCE)
    private val endpoint = "https://$resource.openai.azure.com/openai/deployments/$model"
    private val apiVersion = configuration.getEnvironmentVariable(EnvironmentalVariable.AZURE_OPENAI_API_VERSION)
    private val httpService = HttpService()

    override suspend fun chat(request: ChatRequest): ChatResponse {
        val response =
            httpService.post(url = "$endpoint/chat/completions?api-version=$apiVersion", body = request) {
                header("api-key", apiKey)
            }
        return response.body()
    }
}
