package no.uyqn.config

class MissingEnvironmentalKeyException(
    private val key: String,
) : Exception(key) {
    override fun toString(): String = "Missing environmental key $key"
}
