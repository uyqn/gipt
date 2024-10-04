package no.uyqn

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.Logger
import kotlinx.coroutines.runBlocking
import no.uyqn.args.GiptArgument
import no.uyqn.args.GiptCommands
import no.uyqn.args.GiptFlags
import no.uyqn.config.Configuration
import org.slf4j.LoggerFactory

val logger = LoggerFactory.getLogger("Main") as Logger

fun main(args: Array<String>) =
    runBlocking {
        val packageLogger = LoggerFactory.getLogger("no.uyqn") as Logger

        val arguments =
            args
                .drop(1)
                .map(GiptArgument::fromArg)

        val flags =
            arguments
                .filterIsInstance<GiptFlags>()
                .toSet()

        if (flags.contains(GiptFlags.VERBOSE) || flags.contains(GiptFlags.DEBUG)) {
            logger.level = Level.DEBUG
            packageLogger.level = Level.DEBUG
        }

        val commands = arguments.filterIsInstance<GiptCommands>()
        if (commands.isEmpty()) {
            logger.error("No command provided")
            return@runBlocking
        }

        val config = Configuration(pwd = args[0])

        commands.forEach {
            it
                .command(config)
                .execute(flags = flags.intersect(it.flags))
        }
    }
