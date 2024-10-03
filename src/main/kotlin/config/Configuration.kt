package no.uyqn.config

import io.github.cdimascio.dotenv.Dotenv
import io.github.cdimascio.dotenv.dotenv
import no.uyqn.git.GitFacade
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import java.io.File

class Configuration(
    pwd: String,
    private val dotenv: Dotenv = dotenv { directory = System.getProperty("user.dir") },
) {
    private val repository: Repository = FileRepositoryBuilder().findGitDir(File(pwd)).build()
    val git = GitFacade(git = Git(repository))

    fun getEnvironmentVariable(key: String): String = dotenv[key] ?: throw MissingEnvironmentalKeyException(key)
}
