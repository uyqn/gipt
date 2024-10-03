package no.uyqn.args

sealed interface GiptArgument {
    val arg: String
    val info: String

    companion object {
        fun fromArg(arg: String): GiptArgument =
            GiptFlags.fromArg(arg) ?: GiptCommands.fromArg(arg) ?: throw IllegalArgumentException("Unknown argument: $arg")
    }
}
