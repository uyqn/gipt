package no.uyqn

import io.github.cdimascio.dotenv.dotenv
import kotlinx.coroutines.runBlocking
import no.uyqn.config.Configuration
import no.uyqn.git.GitFacade
import no.uyqn.openai.clients.OpenAiClient
import no.uyqn.openai.clients.models.ChatRequest
import no.uyqn.openai.clients.models.Message
import no.uyqn.openai.clients.models.MessageRole
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import java.io.File

val prompt =
    """
    Generate a commit message following the conventional commit format based on the following git diff --cached output:
    """.trimIndent()
val additionalPrompt =
    """
    Only provide the commit message in its entirety in the style of
    <type>[optional scope]: <description>

    [optional body]

    [optional footer(s)]
    """.trimIndent()

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
        val chatRequest = ChatRequest(messages = listOf(Message(role = MessageRole.USER, content = "$prompt\n$diff\n$additionalPrompt")))
        val response = client.chat(chatRequest)

        response.choices.map { it.message.content }.forEach { println(it) }
    }
