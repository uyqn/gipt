package no.uyqn.git

import org.eclipse.jgit.api.Git
import org.eclipse.jgit.api.errors.NoHeadException
import org.eclipse.jgit.api.errors.RefNotFoundException
import org.eclipse.jgit.diff.DiffFormatter
import org.eclipse.jgit.dircache.DirCacheIterator
import org.eclipse.jgit.lib.ObjectId
import org.eclipse.jgit.revwalk.RevCommit
import org.eclipse.jgit.revwalk.RevWalk
import org.eclipse.jgit.treewalk.CanonicalTreeParser
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat

/**
 * A wrapper class for the JGit library.
 */
class GitFacade(
    private val git: Git,
) {
    private val logger: Logger = LoggerFactory.getLogger(GitFacade::class.java)

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

    val branch: String = git.repository.branch
    private val baseBranch: String
        get() =
            git
                .branchList()
                .call()
                .map { it.name }
                .map { it.removePrefix("refs/heads/") }
                .firstOrNull { setOf("main", "master").contains(it) } ?: throw RefNotFoundException("Unable to locate base branch!")

    internal val log: String
        get() {
            val repository = git.repository

            val mainBranch = baseBranch
            val currentBranch = repository.branch
            logger.info("Comparing commits from $mainBranch to $currentBranch")

            val mainBranchId: ObjectId = repository.resolve("$mainBranch^{commit}")
            val currentBranchId: ObjectId = repository.resolve("$currentBranch^{commit}")

            val revWalk = RevWalk(repository)
            val mainCommit = revWalk.parseCommit(mainBranchId)
            val branchCommit = revWalk.parseCommit(currentBranchId)

            revWalk.markUninteresting(mainCommit)
            revWalk.markStart(branchCommit)

            val commits = revWalk.joinToString("\n\n") { committedMessage(it) }

            revWalk.dispose()

            return commits
        }

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
