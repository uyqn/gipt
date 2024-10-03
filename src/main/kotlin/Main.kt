package no.uyqn

import kotlinx.coroutines.runBlocking
import no.uyqn.args.GiptArgument
import no.uyqn.args.GiptCommands
import no.uyqn.args.GiptFlags
import no.uyqn.config.Configuration
import org.slf4j.Logger
import org.slf4j.LoggerFactory

val prompt =
    """
    Generate a commit message following the Conventional Commits format based on the provided git diff --cached output
    """.trimIndent()
val logger: Logger = LoggerFactory.getLogger("Main")

fun main(args: Array<String>) =
    runBlocking {
        val arguments =
            args
                .drop(1)
                .map(GiptArgument::fromArg)
        val commands = arguments.filterIsInstance<GiptCommands>()
        if (commands.isEmpty()) {
            logger.error("No command provided")
            return@runBlocking
        }
        val config = Configuration(pwd = args[0])
        val flags =
            arguments
                .filterIsInstance<GiptFlags>()
                .toSet()

        commands.forEach {
            it
                .command(config)
                .execute(flags = flags.intersect(it.flags))
        }
    }
