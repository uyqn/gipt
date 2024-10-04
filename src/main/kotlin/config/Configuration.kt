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

    init {
        logger.debug("Running application from $pwd")
    }

    fun getEnvironmentVariable(key: String): String = dotenv[key] ?: throw MissingEnvironmentalKeyException(key)

    fun repositoryUserConfig(key: String): String? {
        val config = repository.config
        val value = config.getString("user", null, key)
        if (value != null) {
            return value
        }

        println("Repository user.$key has not been set")
        print("Enter $key: ")
        readln().let { input ->
            if (input.isBlank()) {
                logger.warn("Continuing application without setting user.$key")
                return null
            }
            config.setString("user", null, key, input)
            config.save().let {
                logger.debug("git config --local user.{} \"{}\"", key, input)
            }
            return input
        }
    }
}
