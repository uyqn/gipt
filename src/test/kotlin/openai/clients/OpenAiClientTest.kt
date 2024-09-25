package openai.clients

import no.uyqn.config.Configuration
import no.uyqn.config.EnvironmentalVariable
import no.uyqn.openai.clients.AzureOpenAiClientImpl
import no.uyqn.openai.clients.MockOpenAiClientImpl
import no.uyqn.openai.clients.OpenAiClient
import no.uyqn.openai.clients.OpenAiClientImpl
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

class OpenAiClientTest {
    @Test
    fun `should return MockOpenAiClientImpl instance`() {
        // Given
        val configuration: Configuration = mock()

        // When
        val client = OpenAiClient.create(configuration, devMode = true)

        // Then
        assertTrue(client is MockOpenAiClientImpl)
    }

    @Test
    fun `should return OpenAiClientImpl instance`() {
        // Given
        val configuration: Configuration = mock()
        Mockito
            .`when`(configuration.getEnvironmentVariable(EnvironmentalVariable.OPENAI_API_KEY))
            .thenReturn("sk-somerandomopenaiapikey123")

        // When
        val client = OpenAiClient.create(configuration, devMode = false)

        // Then
        assertTrue(client is OpenAiClientImpl)
    }

    @Test
    fun `should return AzureOpenAiClientImpl instance`() {
        // Given
        val configuration: Configuration = mock()
        Mockito
            .`when`(configuration.getEnvironmentVariable(EnvironmentalVariable.OPENAI_API_KEY))
            .thenReturn("somerandomazureopenaiapikey123")

        // When
        val client = OpenAiClient.create(configuration, devMode = false)

        // Then
        assertTrue(client is AzureOpenAiClientImpl)
    }
}
