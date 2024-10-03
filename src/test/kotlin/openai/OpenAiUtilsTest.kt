package openai

import no.uyqn.openai.OpenAiUtils
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class OpenAiUtilsTest {
    @Test
    fun `should extract all three blocks of code from content`() {
        // Given
        val content =
            """
            There might be some text before
            ```
            There is a commit message inside here
            ```
            And there might be some texts after
            There might be some text before
            ```
            Here are some code
            ```
            And there might be some texts after
            There might be some text before
            ```
            Here are some more code
            ```
            And there might be some texts after
            """.trimIndent()

        // When
        val codeBlocks = OpenAiUtils.extractCodeBlocks(content)

        // Then
        Assertions.assertEquals(3, codeBlocks.size)
        Assertions.assertEquals("There is a commit message inside here", codeBlocks[0])
        Assertions.assertEquals("Here are some code", codeBlocks[1])
        Assertions.assertEquals("Here are some more code", codeBlocks[2])
    }
}
