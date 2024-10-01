package no.uyqn

import io.github.cdimascio.dotenv.dotenv
import io.ktor.client.plugins.ClientRequestException
import kotlinx.coroutines.runBlocking
import no.uyqn.config.Configuration
import no.uyqn.git.GitFacade
import no.uyqn.git.GitUtils
import no.uyqn.openai.clients.OpenAiClient
import no.uyqn.openai.clients.data.ChatRequest
import no.uyqn.openai.clients.data.Message
import no.uyqn.openai.clients.data.MessageRole
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import java.io.File

val prompt =
    """
    Generate a commit message following the Conventional Commits format based on the provided git diff --cached output
    """.trimIndent()
/*val additionalPrompt =
    """
    Only provide the commit message in its entirety following the Conventional Commits format:
    <type>[optional scope]: <description>

    [optional body]

    [optional footer(s)]
    """.trimIndent()*/

fun main(args: Array<String>) =
    runBlocking {
        val config = Configuration(dotenv = dotenv { directory = System.getProperty("user.dir") })
        val repository = FileRepositoryBuilder().findGitDir(File(args[0])).build()
        val git = GitFacade(Git(repository))

        val diff = git.getDiff()

        if (diff.isEmpty()) {
            return@runBlocking
        }

        val client = OpenAiClient.create(config, devMode = args.contains("--dev"))
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
                println(it)
                println(GitUtils.extractGitCommitMessage(it))
            }
        } catch (e: ClientRequestException) {
            println("Response: ${e.response}")
            println("Message: ${e.message}")
        }
    }
