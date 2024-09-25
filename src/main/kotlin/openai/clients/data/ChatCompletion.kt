package no.uyqn.openai.clients.models

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
    var content: String,
) {
    init {
        val regex = Regex("```(?:plaintext\\n|\\n)([\\s\\S]*?)```")
        content = regex
            .find(content)
            ?.groupValues
            ?.get(1)
            ?.trim() ?: content
    }
}

@Serializable
data class ChatRequest(
    val messages: List<Message>,
)

@Serializable
data class Choice(
    @SerialName("content_filter_results") val contentFilterResults: ContentFilterResults,
    @SerialName("finish_reason") val finishReason: String,
    val index: Int,
    val message: Message,
)

@Serializable
data class ChatResponse(
    val choices: List<Choice>,
    val created: Long,
    val id: String,
    val model: String,
    val `object`: String,
    @SerialName("prompt_filter_results") val promptFilterResults: List<PromptFilterResult>,
    @SerialName("system_fingerprint") val systemFingerprint: String,
    val usage: Usage,
)

@Serializable
data class ContentFilterResults(
    val hate: FilterDetail,
    @SerialName("self_harm") val selfHarm: FilterDetail,
    val sexual: FilterDetail,
    val violence: FilterDetail,
)

@Serializable
data class FilterDetail(
    val filtered: Boolean,
    val severity: String,
)

@Serializable
data class PromptFilterResult(
    @SerialName("prompt_index") val promptIndex: Int,
    @SerialName("content_filter_results") val contentFilterResults: ContentFilterResults,
)

@Serializable
data class Usage(
    @SerialName("completion_tokens") val completionTokens: Int,
    @SerialName("prompt_tokens") val promptTokens: Int,
    @SerialName("total_tokens") val totalTokens: Int,
)
