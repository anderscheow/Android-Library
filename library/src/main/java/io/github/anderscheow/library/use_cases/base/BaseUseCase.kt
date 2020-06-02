package io.github.anderscheow.library.use_cases.base

import io.github.anderscheow.library.executor.PostExecutionThread
import io.github.anderscheow.library.executor.ThreadExecutor
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

abstract class BaseUseCase protected constructor(kodein: Kodein) {

    protected val threadExecutor by kodein.instance<ThreadExecutor>()
    protected val postExecutionThread by kodein.instance<PostExecutionThread>()

    private val disposables: CompositeDisposable = CompositeDisposable()

    fun dispose() {
        if (!disposables.isDisposed) {
            disposables.dispose()
        }
    }

    protected fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }
}
