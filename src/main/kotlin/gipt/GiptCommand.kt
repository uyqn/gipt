package no.uyqn.gipt

import io.ktor.client.plugins.ClientRequestException
import no.uyqn.args.GiptFlags
import no.uyqn.config.Configuration
import no.uyqn.logger
import no.uyqn.openai.clients.OpenAiClient
import no.uyqn.openai.clients.data.ChatRequest
import no.uyqn.openai.clients.data.Message
import no.uyqn.openai.clients.data.MessageRole

sealed interface GiptCommand {
    val configuration: Configuration

    suspend fun execute(flags: Set<GiptFlags>)

    suspend fun prompt(
        messages: List<Message>,
        debug: Boolean,
        action: (String) -> Unit,
    ) {
        val client = OpenAiClient.create(configuration, debug = debug)
        val chatRequest = ChatRequest(messages = messages)
        try {
            val response = client.chat(chatRequest)
            response.choices.map { it.message.content }.forEach(action)
        } catch (e: ClientRequestException) {
            logger.error("Response: ${e.response}")
            logger.error("Message: ${e.message}")
            logger.error("Cause: ${e.cause}")
            logger.error("StackTrace: ${e.stackTrace}")
            throw e
        }
    }

    suspend fun prompt(
        prompt: String,
        message: String,
        debug: Boolean = false,
        action: (String) -> Unit,
    ) = prompt(
        messages =
            listOf(
                Message(role = MessageRole.SYSTEM, content = prompt),
                Message(role = MessageRole.USER, content = message),
            ),
        debug = debug,
        action = action,
    )
}
