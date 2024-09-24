package no.uyqn

import io.github.cdimascio.dotenv.dotenv
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import no.uyqn.config.Configuration
import no.uyqn.git.GitFacade
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import java.io.File

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

fun main(args: Array<String>) =
    runBlocking {
        val config =
            Configuration(
                dotenv =
                    dotenv {
                        directory = System.getProperty("user.dir")
                    },
            )
        val client =
            HttpClient(CIO) {
                install(ContentNegotiation) {
                    json(
                        Json {
                            ignoreUnknownKeys = true
                            isLenient = true
                        },
                    )
                }
            }

        val endpoint = "https://${config.getEnvironmentVariable(
            Configuration.AZURE_OPENAI_RESOURCE,
        )}.openai.azure.com/openai/deployments/gpt-4o-mini/chat/completions?api-version=2024-02-01"

        val repository = FileRepositoryBuilder().findGitDir(File(args[0])).build()
        val git = GitFacade(Git(repository))

        val prompt =
            """
            Provide a commit message following the conventional commit format based on the following git diff --cached output:
            """.trimIndent()
        val diff = git.getDiff()

        if (diff.isEmpty()) {
            return@runBlocking
        }

        val message = "$prompt\nDiff: $diff"
        val chatRequest = ChatRequest(messages = listOf(Message(role = "user", content = message)))

        try {
            val response =
                client
                    .post(endpoint) {
                        header("api-key", config.getEnvironmentVariable(Configuration.OPENAI_API_KEY))
                        contentType(ContentType.Application.Json)
                        setBody(chatRequest)
                    }

            if (!response.status.isSuccess()) {
                println(response.body<String>())
                return@runBlocking
            }

            val content: ChatResponse = response.body()

            content.choices.forEach {
                println(it.message.content)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            client.close()
        }
    }
