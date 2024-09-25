package no.uyqn.openai.clients

import no.uyqn.config.Configuration
import no.uyqn.config.EnvironmentalVariable
import no.uyqn.openai.OpenAiModel
import no.uyqn.openai.clients.models.ChatRequest
import no.uyqn.openai.clients.models.ChatResponse

interface OpenAiClient {
    suspend fun chat(request: ChatRequest): ChatResponse

    companion object {
        fun create(
            configuration: Configuration,
            model: String = OpenAiModel.GPT4O_MINI,
            devMode: Boolean = false,
        ): OpenAiClient {
            if (devMode) {
                return MockOpenAiClientImpl(model)
            }
            if (isAzureOpenAiApiKey(configuration)) {
                return AzureOpenAiClientImpl(configuration, model)
            }
            return OpenAiClientImpl(configuration, model)
        }

        private fun isAzureOpenAiApiKey(configuration: Configuration): Boolean =
            !configuration.getEnvironmentVariable(EnvironmentalVariable.OPENAI_API_KEY).startsWith("sk-")
    }
}
