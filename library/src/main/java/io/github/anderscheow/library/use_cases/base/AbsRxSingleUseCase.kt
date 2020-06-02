package io.github.anderscheow.library.use_cases.base

import io.reactivex.Single
import io.reactivex.observers.DisposableSingleObserver
import org.kodein.di.Kodein

abstract class AbsRxSingleUseCase<T, Params> protected constructor(kodein: Kodein)
    : BaseUseCase(kodein) {

    abstract fun createSingle(params: Params): Single<T>

    fun execute(observer: DisposableSingleObserver<T>, params: Params) {
        addDisposable(this.createSingle(params)
            .subscribeOn(threadExecutor.getScheduler())
            .observeOn(postExecutionThread.getScheduler())
            .subscribeWith(observer))
    }

    internal fun execute(params: Params): Single<T> {
        return createSingle(params)
    }
}
