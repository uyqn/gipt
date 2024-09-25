package openai.clients

import no.uyqn.openai.clients.Message
import no.uyqn.openai.clients.MessageRole
import org.junit.jupiter.api.Test

class MessageTest {
    @Test
    fun `Should only get the commit message`() {
        val content =
            """
            There might be some text before the desired result.
            ```plaintext
            feat(client): implement OpenAI client structure with Azure support

            - Refactored code to integrate OpenAiClient interface and its implementations for Azure and standard OpenAI API.
            - Introduced OpenAiClient, AzureOpenAiClientImpl, and OpenAiClientImpl classes for managing API calls.
            - Created ChatRequest, ChatResponse, Message, and Choice data classes for handling API request and response formats.
            - Updated main function to utilize the new OpenAiClient structure.
            ```
            or there might be some text after the desired result.
            """.trimIndent()

        val message = Message(role = MessageRole.ASSISTANT, content)

        val expected =
            """
            feat(client): implement OpenAI client structure with Azure support

            - Refactored code to integrate OpenAiClient interface and its implementations for Azure and standard OpenAI API.
            - Introduced OpenAiClient, AzureOpenAiClientImpl, and OpenAiClientImpl classes for managing API calls.
            - Created ChatRequest, ChatResponse, Message, and Choice data classes for handling API request and response formats.
            - Updated main function to utilize the new OpenAiClient structure.
            """.trimIndent()

        assert(message.content == expected)
    }
}
