package no.uyqn.gipt

import no.uyqn.args.GiptFlags
import no.uyqn.config.Configuration

class GiptPullRequest(
    override val configuration: Configuration,
) : GiptCommand {
    override suspend fun execute(flags: Set<GiptFlags>) {
    }
}
