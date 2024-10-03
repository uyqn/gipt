package no.uyqn.git

object GitUtils {
    fun extractGitCommitMessage(content: String): String {
        val pattern = Regex("```(?:plaintext\\n|markdown\\n|\\n)([\\s\\S]*?)```")
        return pattern.find(content)?.let {
            // Check which group matched and extract the relevant value
            val matchedGroup = it.groups[1]?.value ?: it.groups[2]?.value
            matchedGroup?.replace("**", "")?.trim() ?: content
        } ?: content
    }
}
