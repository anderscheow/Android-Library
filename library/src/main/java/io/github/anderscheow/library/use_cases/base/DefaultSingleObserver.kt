package io.github.anderscheow.library.use_cases.base

import io.reactivex.observers.DisposableSingleObserver

open class DefaultSingleObserver<T> : DisposableSingleObserver<T>() {

    override fun onSuccess(value: T) {}

    override fun onError(error: Throwable) {
        error.printStackTrace()
    }
}
