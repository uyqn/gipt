package no.uyqn.git

object GitUtils {
    fun extractGitCommitMessage(content: String): String {
        val pattern1 = Regex("```(?:plaintext\\n|\\n)([\\s\\S]*?)```")
        val pattern2 = Regex("(?s)---(.*?)---")
        val combinedPattern =
            Regex(
                """$pattern1|$pattern2""",
            )

        return combinedPattern.find(content)?.let {
            // Check which group matched and extract the relevant value
            val matchedGroup = it.groups[1]?.value ?: it.groups[2]?.value
            matchedGroup?.replace("**", "")?.trim() ?: content
        } ?: content
    }
}
