package no.uyqn.gipt

import no.uyqn.args.GiptFlags
import no.uyqn.config.Configuration
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class GiptPullRequest(
    override val configuration: Configuration,
) : GiptCommand {
    private val logger: Logger = LoggerFactory.getLogger(GiptPullRequest::class.java)

    private val prompt =
        """
        We are currently doing some work on the following branch: ${configuration.git.branch}.
        Generate a pull request title that follows the Conventional Commits guidelines and a pull request description based on the provided git commits.
        """.trimIndent()

    override suspend fun execute(flags: Set<GiptFlags>) {
        val log = configuration.git.log
        prompt(prompt, log) {
            logger.debug("OpenAIs generated response: \n${it.prependIndent("|   ").trimMargin()}")
            println(it)
        }
    }
}
