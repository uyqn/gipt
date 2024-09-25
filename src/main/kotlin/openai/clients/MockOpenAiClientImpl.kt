package no.uyqn.openai.clients

import no.uyqn.openai.OpenAiModel
import no.uyqn.openai.clients.data.ChatRequest
import no.uyqn.openai.clients.data.ChatResponse
import no.uyqn.openai.clients.data.Choice
import no.uyqn.openai.clients.data.Message
import no.uyqn.openai.clients.data.MessageRole
import no.uyqn.openai.clients.data.Usage

class MockOpenAiClientImpl(
    private val model: String = OpenAiModel.GPT4O_MINI,
) : OpenAiClient {
    private val mockMessage: String =
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

    override suspend fun chat(request: ChatRequest): ChatResponse {
        val message = Message(role = MessageRole.ASSISTANT, content = mockMessage)
        return createMockChatResponse(
            choices = listOf(Choice(message = message)),
            model = model,
        )
    }

    companion object {
        fun createMockChatResponse(
            choices: List<Choice>,
            model: String,
            usage: Usage = Usage(0, 0, 0),
        ) = ChatResponse(
            choices = choices,
            created = 1677858242,
            id = "mock-id",
            model = model,
            `object` = "chat.completion",
            usage = usage,
        )
    }
}
