package no.uyqn.openai.clients.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

object MessageRole {
    const val USER = "user"
    const val ASSISTANT = "assistant"
    const val SYSTEM = "system"
}

@Serializable
data class Message(
    val role: String,
    val content: String,
)

@Serializable
data class ChatRequest(
    val messages: List<Message>,
)

@Serializable
data class Choice(
    val message: Message,
)

@Serializable
data class ChatResponse(
    val choices: List<Choice>,
    val created: Long,
    val id: String,
    val model: String,
    val `object`: String,
    val usage: Usage,
)

@Serializable
data class Usage(
    @SerialName("completion_tokens") val completionTokens: Int,
    @SerialName("prompt_tokens") val promptTokens: Int,
    @SerialName("total_tokens") val totalTokens: Int,
)
