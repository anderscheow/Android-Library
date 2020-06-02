package com.easipos.template.use_cases

import io.reactivex.CompletableEmitter
import io.reactivex.MaybeEmitter
import io.reactivex.ObservableEmitter
import io.reactivex.SingleEmitter

//region ObservableEmitter
inline fun <T> ObservableEmitter<T>.isNotDisposed(method: () -> Unit) {
    if (!this.isDisposed) {
        method.invoke()
    }
}

fun <T> ObservableEmitter<T>.next(value: T?) {
    this.isNotDisposed {
        value?.let {
            this.onNext(value)
        }
    }
}

fun <T> ObservableEmitter<T>.complete() {
    this.isNotDisposed {
        this.onComplete()
    }
}

fun <T> ObservableEmitter<T>.error(error: Throwable) {
    this.isNotDisposed {
        this.onError(error)
    }
}
//endregion

//region SingleEmitter
inline fun <T> SingleEmitter<T>.isNotDisposed(method: () -> Unit) {
    if (!this.isDisposed) {
        method.invoke()
    }
}

fun <T> SingleEmitter<T>.success(value: T?) {
    this.isNotDisposed {
        value?.let {
            this.onSuccess(value)
        }
    }
}

fun <T> SingleEmitter<T>.error(error: Throwable) {
    this.isNotDisposed {
        this.onError(error)
    }
}
//endregion

//region MaybeEmitter
inline fun <T> MaybeEmitter<T>.isNotDisposed(method: () -> Unit) {
    if (!this.isDisposed) {
        method.invoke()
    }
}

fun <T> MaybeEmitter<T>.success(value: T?) {
    this.isNotDisposed {
        value?.let {
            this.onSuccess(value)
        }
    }
}

fun <T> MaybeEmitter<T>.error(error: Throwable) {
    this.isNotDisposed {
        this.onError(error)
    }
}
//endregion

//region CompletableEmitter
inline fun CompletableEmitter.isNotDisposed(method: () -> Unit) {
    if (!this.isDisposed) {
        method.invoke()
    }
}

fun CompletableEmitter.complete() {
    this.isNotDisposed {
        this.onComplete()
    }
}

fun CompletableEmitter.error(error: Throwable) {
    this.isNotDisposed {
        this.onError(error)
    }
}
//endregion