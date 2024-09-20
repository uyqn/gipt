package no.uyqn.git

import org.eclipse.jgit.api.Git
import org.eclipse.jgit.api.errors.NoHeadException
import org.eclipse.jgit.diff.DiffFormatter
import org.eclipse.jgit.dircache.DirCacheIterator
import org.eclipse.jgit.treewalk.CanonicalTreeParser
import java.io.ByteArrayOutputStream

/**
 * A wrapper class for the JGit library.
 */
class GitFacade(private val git: Git) {
    private val repository = git.repository

    /**
     * git diff --staged
     */
    fun getDiff(): String {
        val outputStream = ByteArrayOutputStream()
        val diffFormatter = DiffFormatter(outputStream)
        diffFormatter.setRepository(repository)

        try {
            git.diff()
                .setCached(true)
                .setOutputStream(outputStream)
                .call()
                .forEach(diffFormatter::format)
        } catch (e: NoHeadException) {
            val dirCacheIterator = DirCacheIterator(repository.readDirCache())
            val workingTreeIterator = CanonicalTreeParser()

            git.diff()
                .setOldTree(dirCacheIterator) // Index (staged files)
                .setNewTree(workingTreeIterator) // Working directory
                .call()
                .forEach(diffFormatter::format)
        } finally {
            diffFormatter.close()
        }
        return outputStream.toString("UTF-8")
    }
}
