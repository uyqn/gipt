package no.uyqn.args

import no.uyqn.config.Configuration
import no.uyqn.gipt.GiptCommand
import no.uyqn.gipt.GiptCommit

enum class GiptCommands(
    override val arg: String,
    val command: (Configuration) -> GiptCommand,
    val flags: Set<GiptFlags> = emptySet(),
    override val info: String = "",
) : GiptArgument {
    COMMIT(arg = "commit", command = { GiptCommit(it) }, flags = setOf(GiptFlags.PREVIEW)),
    ;

    companion object {
        private val map = entries.associateBy(GiptCommands::arg)

        fun fromArg(arg: String): GiptCommands? = map[arg]
    }
}
