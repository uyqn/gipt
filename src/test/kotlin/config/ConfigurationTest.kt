package config

import io.github.cdimascio.dotenv.Dotenv
import no.uyqn.config.Configuration
import no.uyqn.config.EnvironmentalVariable
import no.uyqn.config.MissingEnvironmentalKeyException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito

class ConfigurationTest {
    private lateinit var dotenv: Dotenv
    private lateinit var configuration: Configuration

    @BeforeEach
    fun setUp() {
        dotenv = Mockito.mock(Dotenv::class.java)
        configuration = Configuration(dotenv)
    }

    @Test
    fun `should return environment variable when key is present`() {
        val key = EnvironmentalVariable.OPENAI_API_KEY
        val expectedValue = "my-secret-api-key"

        Mockito.`when`(dotenv[key]).thenReturn(expectedValue)

        val result = configuration.getEnvironmentVariable(key)

        assertEquals(expectedValue, result)
    }

    @Test
    fun `should throw MissingEnvironmentalKeyException when key is missing`() {
        val key = EnvironmentalVariable.OPENAI_API_KEY

        Mockito.`when`(dotenv[key]).thenReturn(null)

        val exception =
            assertThrows(MissingEnvironmentalKeyException::class.java) {
                configuration.getEnvironmentVariable(key)
            }

        assertTrue(exception.toString().contains(key))
    }
}
