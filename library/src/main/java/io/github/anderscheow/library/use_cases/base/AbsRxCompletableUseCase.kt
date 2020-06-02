package io.github.anderscheow.library.use_cases.base

import io.reactivex.Completable
import org.kodein.di.Kodein

abstract class AbsRxCompletableUseCase<Params> protected constructor(kodein: Kodein)
    : BaseUseCase(kodein) {

    abstract fun createCompletable(params: Params): Completable

    fun execute(observer: DefaultCompletableObserver, params: Params) {
        addDisposable(this.createCompletable(params)
                .subscribeOn(threadExecutor.getScheduler())
                .observeOn(postExecutionThread.getScheduler())
                .subscribeWith(observer))
    }

    internal fun execute(params: Params): Completable {
        return createCompletable(params)
    }
}
