package git

import no.uyqn.git.GitFacade
import org.eclipse.jgit.api.Git
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.nio.file.Path
import kotlin.test.assertTrue

class GitFacadeTest {
    private lateinit var repoDir: File
    private lateinit var git: Git
    private lateinit var gitFacade: GitFacade

    @BeforeEach
    fun setUp(
        @TempDir tempDir: Path,
    ) {
        repoDir = tempDir.toFile()
        git = Git.init().setDirectory(repoDir).call()
        gitFacade = GitFacade(git)
    }

    @Test
    fun `getDiff should return empty if nothing is committed for staging`() {
        val diff = gitFacade.diff
        assertTrue(diff.isEmpty(), "Expected empty diff, but got:\n$diff")
    }

    @Test
    fun `getDiff should return something if something is committed for staging`() {
        val file = File(repoDir, "file.txt")
        file.writeText("Hello, World!")
        git.add().addFilepattern("file.txt").call()

        val diff = gitFacade.diff
        assertTrue(diff.isNotEmpty())
    }
}
