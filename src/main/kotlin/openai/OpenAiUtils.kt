package no.uyqn.openai

object OpenAiUtils {
    fun extractCodeBlocks(text: String): List<String> {
        val pattern = Regex("```(?:\\w+\\n|\\n)([\\s\\S]*?)```")
        return pattern
            .findAll(text)
            .map { it.groups[1]?.value?.trim() ?: "" }
            .toList()
    }

    fun extractCodeBlock(
        text: String,
        index: Int = 0,
    ): String = extractCodeBlocks(text)[index]
}
