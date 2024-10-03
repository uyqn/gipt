package no.uyqn.gipt

import no.uyqn.args.GiptFlags
import no.uyqn.config.Configuration

sealed interface GiptCommand {
    val configuration: Configuration

    suspend fun execute(flags: Set<GiptFlags>)
}
