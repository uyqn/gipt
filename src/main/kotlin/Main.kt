package no.uyqn

import no.uyqn.git.GitFacade
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import java.io.File

fun main(args: Array<String>) {
    val repository = FileRepositoryBuilder()
        .findGitDir(File(args[0]))
        .build()
    val git = GitFacade(Git(repository))

    println(git.getDiff())
}
