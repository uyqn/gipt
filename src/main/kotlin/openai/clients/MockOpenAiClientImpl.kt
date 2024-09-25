package no.uyqn.openai.clients

import no.uyqn.openai.OpenAiModel
import no.uyqn.openai.clients.models.ChatRequest
import no.uyqn.openai.clients.models.ChatResponse
import no.uyqn.openai.clients.models.Choice
import no.uyqn.openai.clients.models.ContentFilterResults
import no.uyqn.openai.clients.models.FilterDetail
import no.uyqn.openai.clients.models.Message
import no.uyqn.openai.clients.models.Usage

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
        val response =
            ChatResponse(
                choices =
                    request.messages.map {
                        Choice(
                            contentFilterResults =
                                ContentFilterResults(
                                    FilterDetail(true, ""),
                                    FilterDetail(true, ""),
                                    FilterDetail(true, ""),
                                    FilterDetail(true, ""),
                                ),
                            finishReason = "stop",
                            index = 0,
                            message = Message(role = "assistant", content = mockMessage),
                        )
                    },
                created = 0,
                id = "mock-id",
                model = model,
                `object` = "chat.completion",
                promptFilterResults = emptyList(),
                systemFingerprint = "mock-fingerprint",
                usage =
                    Usage(
                        0,
                        0,
                        0,
                    ),
            )
        return response
    }
}
