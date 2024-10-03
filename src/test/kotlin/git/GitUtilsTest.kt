package git

import no.uyqn.git.GitUtils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class GitUtilsTest {
    @Test
    fun `should extract commit message from content`() {
        // Given
        val content =
            """
            There might be some text before
            ```
            There is a commit message inside here
            ```
            And there might be some texts after
            """.trimIndent()

        // When
        val commitMessage = GitUtils.extractGitCommitMessage(content)

        // Then
        assertEquals("There is a commit message inside here", commitMessage)
    }

    @Test
    fun `should extract commit message from content with plaintext`() {
        // Given
        val content =
            """
            There might be some text before
            ```plaintext
            There is a commit message inside here
            ```
            And there might be some texts after
            """.trimIndent()

        // When
        val commitMessage = GitUtils.extractGitCommitMessage(content)

        // Then
        assertEquals("There is a commit message inside here", commitMessage)
    }

    @Test
    fun `should extract commit message from content with markdown`() {
        // Given
        val content =
            """
            There might be some text before
            ```markdown
            There is a commit message inside here
            ```
            And there might be some texts after
            """.trimIndent()

        // When
        val commitMessage = GitUtils.extractGitCommitMessage(content)

        // Then
        assertEquals("There is a commit message inside here", commitMessage)
    }

    @Test
    fun `should extract commit message from content with git`() {
        // Given
        val content =
            """
            There might be some text before
            ```git
            There is a commit message inside here
            ```
            And there might be some texts after
            """.trimIndent()

        // When
        val commitMessage = GitUtils.extractGitCommitMessage(content)

        // Then
        assertEquals("There is a commit message inside here", commitMessage)
    }
}
