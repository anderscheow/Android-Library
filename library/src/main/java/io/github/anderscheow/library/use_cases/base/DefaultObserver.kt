package io.github.anderscheow.library.use_cases.base

import io.reactivex.observers.DisposableObserver

open class DefaultObserver<T> : DisposableObserver<T>() {

    override fun onComplete() {
    }

    override fun onNext(value: T) {
    }

    override fun onError(error: Throwable) {
        error.printStackTrace()
    }
}
