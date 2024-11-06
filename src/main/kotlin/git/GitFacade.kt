package no.uyqn.git

import org.eclipse.jgit.api.Git
import org.eclipse.jgit.api.errors.NoHeadException
import org.eclipse.jgit.diff.DiffFormatter
import org.eclipse.jgit.dircache.DirCacheIterator
import org.eclipse.jgit.revwalk.RevCommit
import org.eclipse.jgit.treewalk.CanonicalTreeParser
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat

/**
 * A wrapper class for the JGit library.
 */
class GitFacade(
    private val git: Git,
) {
    internal val diff: String
        get() {
            val outputStream = ByteArrayOutputStream()
            val diffFormatter = DiffFormatter(outputStream)
            diffFormatter.setRepository(git.repository)

            try {
                git
                    .diff()
                    .setCached(true)
                    .setOutputStream(outputStream)
                    .call()
                    .forEach(diffFormatter::format)
            } catch (e: NoHeadException) {
                val dirCacheIterator = DirCacheIterator(git.repository.readDirCache())
                val workingTreeIterator = CanonicalTreeParser()

                git
                    .diff()
                    .setOldTree(dirCacheIterator) // Index (staged files)
                    .setNewTree(workingTreeIterator) // Working directory
                    .call()
                    .forEach(diffFormatter::format)
            } finally {
                diffFormatter.close()
            }
            return outputStream.toString("UTF-8")
        }

    val branch = git.repository.branch

    fun commit(message: String): RevCommit = git.commit().setMessage(message).call()

    fun committedMessage(commit: RevCommit): String =
        """
        |    commit ${commit.name}
        |    Author: ${commit.authorIdent.name} <${commit.authorIdent.emailAddress}>
        |    Date: ${SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy Z").format(commit.commitTime * 1000L)}
        
        ${commit.fullMessage.prependIndent("|       ")}
        """.trimMargin()

    override fun toString(): String = "$git"
}
