package no.uyqn.args

enum class GiptFlags(
    override val arg: String,
    override val info: String = "",
) : GiptArgument {
    DEBUG(
        arg = "--debug",
        info =
            """
            |   Run in debug mode.
            |   Running in debug mode and attach application to a remote jvm debugger on localhost:5005.
            |   Prevents the program from performing http requests to openai.
            """.trimMargin(),
    ),

    PREVIEW(
        arg = "--preview",
        info =
            """
            |   Preview the commit message.
            |   Prints the generated commit message to the console without committing.
            """.trimMargin(),
    ),

    VERBOSE(
        arg = "--verbose",
        info =
            """
            |   Run in verbose mode.
            |   Set logger level to INFO and print additional information to the console.
            """.trimMargin(),
    ),
    ;

    companion object {
        private val map = entries.associateBy(GiptFlags::arg)

        fun fromArg(arg: String): GiptFlags? = map[arg]
    }
}
