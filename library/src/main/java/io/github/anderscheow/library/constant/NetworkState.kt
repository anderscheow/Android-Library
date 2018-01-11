package io.github.anderscheow.library.constant

@Suppress("DataClassPrivateConstructor")
data class NetworkState private constructor(
        val status: Status,
        val message: String? = null) {

    enum class Status {
        RUNNING,
        SUCCESS,
        FAILED
    }

    companion object {

        var LOADED = NetworkState(Status.SUCCESS)

        var LOADING = NetworkState(Status.RUNNING)

        fun error(msg: String?) = NetworkState(Status.FAILED, msg)
    }
}
