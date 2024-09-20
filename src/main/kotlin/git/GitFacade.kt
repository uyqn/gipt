package no.uyqn.git

import org.eclipse.jgit.api.Git
import org.eclipse.jgit.lib.Repository
import java.io.ByteArrayOutputStream

/**
 * A wrapper class for the JGit library.
 */
class GitFacade(private val git: Git) {
    private val repository: Repository = git.repository

    /**
     * git diff --staged
     */
    fun getDiff(): String {
        val outputStream = ByteArrayOutputStream()
        git.diff()
            .setCached(true)
            .setOutputStream(outputStream)
            .call()
        return outputStream.toString("UTF-8")
    }
}
