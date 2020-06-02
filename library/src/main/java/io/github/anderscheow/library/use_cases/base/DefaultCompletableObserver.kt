package io.github.anderscheow.library.use_cases.base

import io.reactivex.observers.DisposableCompletableObserver

open class DefaultCompletableObserver : DisposableCompletableObserver() {

    override fun onComplete() {}

    override fun onError(error: Throwable) {
        error.printStackTrace()
    }
}
