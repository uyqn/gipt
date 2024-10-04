package no.uyqn.config

import io.github.cdimascio.dotenv.Dotenv
import io.github.cdimascio.dotenv.dotenv
import no.uyqn.git.GitFacade
import no.uyqn.logger
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import java.io.File

class Configuration(
    pwd: String,
    private val dotenv: Dotenv =
        dotenv {
            directory = System.getProperty("user.dir")
            ignoreIfMissing = true
        },
) {
    private val repository: Repository =
        try {
            FileRepositoryBuilder().findGitDir(File(pwd)).build()
        } catch (e: IllegalArgumentException) {
            logger.error("$pwd is not within a Git repository")
            throw e
        }
    val git = GitFacade(git = Git(repository))
    private val username: String = repository.config.getString("user", null, "name")
    private val email: String = repository.config.getString("user", null, "email")

    init {
        logger.debug("Running application from $pwd")
        logger.debug("username: $username")
        logger.debug("email: $email")
    }

    fun getEnvironmentVariable(key: String): String = dotenv[key] ?: throw MissingEnvironmentalKeyException(key)
}
