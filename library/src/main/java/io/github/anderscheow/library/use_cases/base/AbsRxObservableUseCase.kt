package io.github.anderscheow.library.use_cases.base

import io.reactivex.Observable
import io.reactivex.observers.DisposableObserver
import org.kodein.di.Kodein

abstract class AbsRxObservableUseCase<T, Params> protected constructor(kodein: Kodein)
    : BaseUseCase(kodein) {

    internal abstract fun createObservable(params: Params): Observable<T>

    fun execute(observer: DisposableObserver<T>, params: Params) {
        addDisposable(this.createObservable(params)
                .subscribeOn(threadExecutor.getScheduler())
                .observeOn(postExecutionThread.getScheduler())
                .subscribeWith(observer))
    }

    internal fun execute(params: Params): Observable<T> {
        return createObservable(params)
    }
}
