package no.uyqn.gipt

import io.ktor.client.plugins.ClientRequestException
import no.uyqn.args.GiptFlags
import no.uyqn.config.Configuration
import no.uyqn.git.GitUtils
import no.uyqn.openai.clients.OpenAiClient
import no.uyqn.openai.clients.data.ChatRequest
import no.uyqn.openai.clients.data.Message
import no.uyqn.openai.clients.data.MessageRole
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class GiptCommit(
    override val configuration: Configuration,
) : GiptCommand {
    private val logger: Logger = LoggerFactory.getLogger(GiptCommit::class.java)
    private val prompt =
        """
        Generate a commit message following the Conventional Commits format based on the provided git diff --cached output
        """.trimIndent()

    override suspend fun execute(flags: Set<GiptFlags>) {
        val diff = configuration.git.diff

        if (diff.isEmpty()) {
            logger.info("No changes found in staging area, add changes to staging area with 'git add' before running this command")
            return
        }

        val client = OpenAiClient.create(configuration, debug = flags.contains(GiptFlags.DEBUG))
        val chatRequest =
            ChatRequest(
                messages =
                    listOf(
                        Message(role = MessageRole.SYSTEM, content = prompt),
                        Message(role = MessageRole.USER, content = diff),
                    ),
            )

        try {
            val response = client.chat(chatRequest)
            response.choices.map { it.message.content }.forEach {
                logger.info("OpenAIs generated response: \n$it")
                val commitMessage = GitUtils.extractGitCommitMessage(it)
                configuration.git.commit(commitMessage)
            }
        } catch (e: ClientRequestException) {
            println("Response: ${e.response}")
            println("Message: ${e.message}")
            println("Cause: ${e.cause}")
            println("StackTrace: ${e.stackTrace}")
            throw e
        }
    }
}
