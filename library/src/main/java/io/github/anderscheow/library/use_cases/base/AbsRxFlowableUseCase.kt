package io.github.anderscheow.library.use_cases.base

import io.reactivex.Flowable
import io.reactivex.subscribers.DisposableSubscriber
import org.kodein.di.Kodein

abstract class AbsRxFlowableUseCase<T, Params> protected constructor(kodein: Kodein)
    : BaseUseCase(kodein) {

    internal abstract fun createFlowable(params: Params): Flowable<T>

    fun execute(observer: DisposableSubscriber<T>, params: Params) {
        addDisposable(this.createFlowable(params)
                .subscribeOn(threadExecutor.getScheduler())
                .observeOn(postExecutionThread.getScheduler())
                .subscribeWith(observer))
    }

    internal fun execute(params: Params): Flowable<T> {
        return createFlowable(params)
    }
}

